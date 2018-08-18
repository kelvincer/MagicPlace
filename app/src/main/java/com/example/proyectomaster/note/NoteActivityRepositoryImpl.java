package com.example.proyectomaster.note;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.note.events.NoteEvent;
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

public class NoteActivityRepositoryImpl implements NoteActivityRepository {

    private static String TAG = NoteActivityRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;

    public NoteActivityRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void uploadPhotoAndNote(final byte[] data, final String message, final String placeId) {

        final String uuid = UUID.randomUUID().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(ConstantsHelper.FIREBASE_STORAGE);
        final StorageReference imageRef = storageRef.child(String.format("%s_%s", "Place", uuid)).child(uuid + ".jpg");

        UploadTask uploadTask = imageRef.putBytes(data);
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
                        Map<String, Object> datos = new HashMap<>();
                        datos.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        datos.put("date", Helper.getCurrentDate());
                        datos.put("comment", message);
                        datos.put("url", uri.toString());
                        datos.put("timestamp", System.currentTimeMillis());

                        final String uuid = UUID.randomUUID().toString();
                        FirebaseFirestore.getInstance()
                                .collection("comentarios")
                                .document(placeId)
                                .collection("messages")
                                .document("message_" + uuid)
                                .set(datos).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                post(NoteEvent.ON_SUCCESS);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                post(NoteEvent.ON_ERROR);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void uploadOnlyNote(String message, String placeId) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        datos.put("date", Helper.getCurrentDate());
        datos.put("comment", message);
        datos.put("url", null);
        datos.put("timestamp", System.currentTimeMillis());

        Log.d(TAG, "datos only note: " + datos.toString());

        final String uuid = UUID.randomUUID().toString();
        FirebaseFirestore.getInstance()
                .collection("comentarios")
                .document(placeId)
                .collection("messages")
                .document("message_" + uuid)
                .set(datos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                post(NoteEvent.ON_SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                post(NoteEvent.ON_ERROR, e.getMessage());
            }
        });
    }

    private void post(int type) {
        post(type, "");
    }

    private void post(int type, String message) {
        NoteEvent event = new NoteEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }
}
