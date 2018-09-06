package com.example.proyectomaster.start.fragments.favourites.events;

import com.example.proyectomaster.start.entities.CategoryModel;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentRepositoryImpl;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FavouritesFragEvent {

    public static final int ON_SUCCESS = 1;
    public static final int ON_ERROR = 2;
    public static final int ON_NO_CATEGORIES = 3;

    int type;
    String message;
    FirestoreRecyclerOptions<CategoryModel> options;

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

    public FirestoreRecyclerOptions<CategoryModel> getOptions() {
        return options;
    }

    public void setOptions(FirestoreRecyclerOptions<CategoryModel> options) {
        this.options = options;
    }
}
