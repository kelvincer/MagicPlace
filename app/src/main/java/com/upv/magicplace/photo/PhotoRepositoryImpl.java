package com.upv.magicplace.photo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.upv.magicplace.ConstantsHelper;
import com.upv.magicplace.detail.activity.events.FirebasePhotoEvent;
import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.photo.events.PhotoEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PhotoRepositoryImpl implements PhotoRepository {

    private static final String TAG = PhotoRepositoryImpl.class.getSimpleName();
    EventBus eventBus;

    public PhotoRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void deleteFile(final FavoritePhotoModel model, final String placeId) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(ConstantsHelper.FIREBASE_STORAGE);
        final StorageReference imageRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(model.getName() + ".jpg");
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("photos")
                        .document("places")
                        .collection(placeId)
                        .document("foto_" + model.getName())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        post(PhotoEvent.ON_SUCCESS, "Foto eliminada");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        post(PhotoEvent.ON_ERROR, e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                post(PhotoEvent.ON_ERROR, e.getLocalizedMessage());
            }
        });
    }

    private void post(int type) {
        post(type, "");
    }

    private void post(int type, String message) {
        PhotoEvent event = new PhotoEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }
}
