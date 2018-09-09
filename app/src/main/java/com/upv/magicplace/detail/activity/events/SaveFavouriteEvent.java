package com.upv.magicplace.detail.activity.events;

public class SaveFavouriteEvent {

    public static final int ON_SUCCESS = 1;
    public static final int ON_ERROR = 2;
    public static final int IS_FAVOURITE = 3;
    private int type;
    private String message;
    private boolean isFavourite;

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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
