package com.example.proyectomaster.detail.activity;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.detail.activity.events.FirebasePhotoEvent;
import com.example.proyectomaster.lib.EventBus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebasePhotoRepositoryImpl implements FirebasePhotoRepository {

    private static final String TAG = FirebasePhotoRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;

    public FirebasePhotoRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void uploadPhoto(final byte[] data, final String placeId) {

        final String uuid = UUID.randomUUID().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(ConstantsHelper.FIREBASE_STORAGE);
        final StorageReference imageRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uuid + ".jpg");

        UploadTask uploadTask = imageRef.putBytes(data);

        /*imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, uri.toString());
                Map<String, Object> datos = new HashMap<>();
                datos.put("url", uri.toString());
                FirebaseFirestore.getInstance()
                        .collection("images")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection(placeId)
                        .document("fotos")
                        .set(datos).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "UPLOAD DATA TO FIRESTORE SUCCESS");
                        post(HighlightEvent.ON_SUCCESS_UPLOAD_PHOTO);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "UPLOAD DATA TO FIRESTORE FAILURE");
                    }
                });
            }
        });*/
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "UPLOAD PHOTO FAILURE");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "UPLOAD PHOTO SUCCESS");
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, uri.toString());
                        Map<String, Object> datos = new HashMap<>();
                        datos.put("url", uri.toString());
                        datos.put("name", uuid);
                        FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .collection("photos")
                                .document("places")
                                .collection(placeId)
                                .document("foto_" + uuid)
                                .set(datos).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "UPLOAD DATA TO FIRESTORE SUCCESS");
                                post(FirebasePhotoEvent.ON_SUCCESS_UPLOAD_PHOTO, "Success upload");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                                post(FirebasePhotoEvent.ERROR, e.getMessage());
                                Log.d(TAG, "UPLOAD DATA TO FIRESTORE FAILURE");
                            }
                        });
                    }
                });
            }
        });
    }

    private void post(int type, String message) {
        FirebasePhotoEvent event = new FirebasePhotoEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }
}
