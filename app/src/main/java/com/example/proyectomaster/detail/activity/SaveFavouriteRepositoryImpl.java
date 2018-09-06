package com.example.proyectomaster.detail.activity;

import android.support.annotation.NonNull;

import com.example.proyectomaster.detail.activity.events.SaveFavouriteEvent;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.lib.EventBus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class SaveFavouriteRepositoryImpl implements SaveFavouriteRepository {

    EventBus eventBus;

    public SaveFavouriteRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void saveFavourite(final Result result) {

        final String type = result.getTypes().get(0) != null ? result.getTypes().get(0) : "unknown";
        final Map<String, Object> datos = new HashMap<>();
        datos.put("placeId", result.getPlaceId());
        datos.put("placeName", result.getName());
        datos.put("address", result.getFormattedAddress());
        datos.put("category", type);

        Map<String, Object> datosType = new HashMap<>();
        datosType.put("type_name", type);

        FirebaseFirestore.getInstance()
                .collection("favourites")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("types")
                .document(type).set(datosType).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseFirestore.getInstance()
                        .collection("favourites")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("types")
                        .document(type)
                        .collection("places")
                        .document("place_" + result.getPlaceId())
                        .set(datos).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        postEvent(SaveFavouriteEvent.ON_SUCCESS, "success save favourite");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        postEvent(SaveFavouriteEvent.ON_ERROR, "failure save favourite");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                postEvent(SaveFavouriteEvent.ON_ERROR, "failure save favourite");
            }
        });
    }

    @Override
    public void checkIfFavourite(Result result) {
        final String type = result.getTypes().get(0) != null ? result.getTypes().get(0) : "unknown";
        FirebaseFirestore.getInstance()
                .collection("favourites")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("types")
                .document(type)
                .collection("places")
                .document("place_" + result.getPlaceId())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if (documentSnapshot != null) {
                            if (documentSnapshot.exists()) {
                                postEvent(SaveFavouriteEvent.IS_FAVOURITE, true);
                            } else {
                                postEvent(SaveFavouriteEvent.IS_FAVOURITE, false);
                            }
                        }
                    }
                });
    }

    private void postEvent(int type, String message) {
        SaveFavouriteEvent event = new SaveFavouriteEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }

    private void postEvent(int type, boolean exists) {
        SaveFavouriteEvent event = new SaveFavouriteEvent();
        event.setType(type);
        event.setFavourite(exists);
        eventBus.post(event);
    }
}
