package com.example.proyectomaster.start.fragments.favourites;

import android.support.annotation.NonNull;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.start.entities.CategoryModel;
import com.example.proyectomaster.start.fragments.favourites.events.FavouritesFragEvent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FavouritesFragmentRepositoryImpl implements FavouritesFragmentRepository {

    private static final String TAG = FavouritesFragmentRepositoryImpl.class.getSimpleName();
    EventBus eventBus;

    public FavouritesFragmentRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getCategories() {
        final Query query = FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("favourites");

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    FirestoreRecyclerOptions<CategoryModel> options = new FirestoreRecyclerOptions
                            .Builder<CategoryModel>().setQuery(query, CategoryModel.class).build();
                    postEvent(FavouritesFragEvent.ON_SUCCESS, options);
                } else {
                    postEvent(FavouritesFragEvent.ON_NO_CATEGORIES, "No hay categorias");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                postEvent(FavouritesFragEvent.ON_ERROR, "error");
            }
        });

        /*FirebaseFirestore.getInstance().collection("favourites")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("types")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    Log.d(TAG, list.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });*/
    }

    private void postEvent(int type, String message) {
        FavouritesFragEvent event = new FavouritesFragEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }

    private void postEvent(int type, FirestoreRecyclerOptions<CategoryModel> options) {
        FavouritesFragEvent event = new FavouritesFragEvent();
        event.setOptions(options);
        event.setType(type);
        eventBus.post(event);
    }
}
