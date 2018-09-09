package com.upv.magicplace.note.events;

public class NoteEvent {

    private int type;
    private String message;
    public static final int ON_SUCCESS = 1;
    public static final int ON_ERROR = 2;

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
}
