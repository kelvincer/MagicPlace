package com.upv.magicplace.detail.fragments.notes.events;

import com.upv.magicplace.detail.fragments.notes.entities.Comment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteFragEvent {

    private int type;
    private String message;
    FirestoreRecyclerOptions<Comment> opciones;
    public static final int GET_COMMENTS_SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int NO_COMMENTS = 2;

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

    public FirestoreRecyclerOptions<Comment> getOpciones() {
        return opciones;
    }

    public void setOpciones() {
        setOpciones();
    }

    public void setOpciones(FirestoreRecyclerOptions<Comment> opciones) {
        this.opciones = opciones;
    }
}
