package com.upv.magicplace.detail.fragments.highlight.events;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HighlightEvent {

    private int type;
    private String message;
    FirestoreRecyclerOptions<FavoritePhotoModel> opciones;
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

    public FirestoreRecyclerOptions<FavoritePhotoModel> getOpciones() {
        return opciones;
    }

    public void setOpciones(FirestoreRecyclerOptions<FavoritePhotoModel> opciones) {
        this.opciones = opciones;
    }
}
