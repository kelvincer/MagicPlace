package com.example.proyectomaster.detail.fragments.highlight.events;

import com.example.proyectomaster.detail.entities.StoragePhoto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HighlightEvent {

    private int type;
    private String message;
    FirestoreRecyclerOptions<StoragePhoto> opciones;
    public static final int GET_PHOTOS_SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int ON_SUCCESS_UPLOAD_PHOTO = 2;
    public static final int NO_PHOTOS = 3;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FirestoreRecyclerOptions<StoragePhoto> getOpciones() {
        return opciones;
    }

    public void setOpciones(FirestoreRecyclerOptions<StoragePhoto> opciones) {
        this.opciones = opciones;
    }
}
