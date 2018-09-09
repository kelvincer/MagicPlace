package com.upv.magicplace.detail.fragments.highlight;

import android.support.annotation.NonNull;
import android.util.Log;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.upv.magicplace.detail.fragments.highlight.events.HighlightEvent;
import com.upv.magicplace.lib.EventBus;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.upv.magicplace.detail.fragments.highlight.events.HighlightEvent;


public class HighlightRepositoryImpl implements HighlightRepository {

    private static final String TAG = HighlightRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;

    public HighlightRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPhotos(String placeId) {

        final Query query = FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("photos")
                .document("places")
                .collection(placeId)
                .limit(20);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    FirestoreRecyclerOptions<FavoritePhotoModel> options = new FirestoreRecyclerOptions
                            .Builder<FavoritePhotoModel>().setQuery(query, FavoritePhotoModel.class).build();
                    post(HighlightEvent.GET_PHOTOS_SUCCESS, options);
                    Log.d(TAG, "on success");
                } else {
                    post(HighlightEvent.NO_PHOTOS, "NO HAY PHOTOS");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                post(HighlightEvent.ERROR, e.getMessage());
            }
        });
    }

    private void post(int type) {
        post(type, "");
    }

    private void post(int type, String message) {
        HighlightEvent event = new HighlightEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }

    private void post(int type, FirestoreRecyclerOptions<FavoritePhotoModel> options) {
        HighlightEvent event = new HighlightEvent();
        event.setOpciones(options);
        event.setType(type);
        eventBus.post(event);
    }
}
