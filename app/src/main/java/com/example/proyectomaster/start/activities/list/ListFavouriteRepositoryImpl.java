package com.example.proyectomaster.start.activities.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.start.activities.list.entitites.FavouritePlaceModel;
import com.example.proyectomaster.start.activities.list.events.ListFavouriteEvent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class ListFavouriteRepositoryImpl implements ListFavouriteRepository {

    private static final String TAG = ListFavouriteRepositoryImpl.class.getSimpleName();
    EventBus eventBus;

    public ListFavouriteRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getFavouritesByCategory(String category) {

        final Query query = FirebaseFirestore.getInstance()
                .collection("favourites")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("types")
                .document(category)
                .collection("places");

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    FirestoreRecyclerOptions<FavouritePlaceModel> options = new FirestoreRecyclerOptions
                            .Builder<FavouritePlaceModel>().setQuery(query, FavouritePlaceModel.class).build();
                    postEvent(ListFavouriteEvent.ON_SUCCESS, options);
                } else {
                    //postEvent(FavouritesFragEvent.ON_NO_CATEGORIES, "No hay categorias");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ListFavouriteEvent.ON_ERROR, "error");
            }
        });
    }

    @Override
    public void deleteFavouritePlace(final FavouritePlaceModel model) {

        FirebaseFirestore.getInstance()
                .collection("favourites")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("types")
                .document(model.getCategory())
                .collection("places")
                .document("place_" + model.getPlaceId())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                postEvent(ListFavouriteEvent.ON_DELETE_SUCCESS, "Eliminado satisfactoriamente");
                FirebaseFirestore.getInstance()
                        .collection("favourites")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("types")
                        .document(model.getCategory())
                        .collection("places")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                if (queryDocumentSnapshots != null) {
                                    if (queryDocumentSnapshots.size() == 0) {
                                        FirebaseFirestore.getInstance()
                                                .collection("favourites")
                                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .collection("types")
                                                .document(model.getCategory())
                                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DELETE CATEGORY SUCCESSFUL");
                                            }
                                        });
                                    }
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                postEvent(ListFavouriteEvent.ON_ERROR, "Error al eliminar");
            }
        });
    }

    private void postEvent(int type, FirestoreRecyclerOptions<FavouritePlaceModel> options) {
        ListFavouriteEvent event = new ListFavouriteEvent();
        event.setOptions(options);
        event.setType(type);
        eventBus.post(event);
    }

    private void postEvent(int type, String message) {
        ListFavouriteEvent event = new ListFavouriteEvent();
        event.setMessage(message);
        event.setType(type);
        eventBus.post(event);
    }
}
