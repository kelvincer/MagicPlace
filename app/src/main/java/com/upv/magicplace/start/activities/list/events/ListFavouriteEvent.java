package com.upv.magicplace.start.activities.list.events;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.upv.magicplace.start.activities.list.entitites.FavouritePlaceModel;

public class ListFavouriteEvent {

    public static final int ON_SUCCESS = 1;
    public static final int ON_ERROR = 2;
    public static final int ON_DELETE_SUCCESS = 3;
    FirestoreRecyclerOptions<FavouritePlaceModel> options;

    int type;
    String message;

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

    public FirestoreRecyclerOptions<FavouritePlaceModel> getOptions() {
        return options;
    }

    public void setOptions(FirestoreRecyclerOptions<FavouritePlaceModel> options) {
        this.options = options;
    }
}
