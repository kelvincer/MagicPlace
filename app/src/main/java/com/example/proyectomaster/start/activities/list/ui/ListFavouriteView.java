package com.example.proyectomaster.start.activities.list.ui;

import com.example.proyectomaster.start.activities.list.entitites.FavouritePlaceModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface ListFavouriteView {

    void showMessage(String message);

    void setOptions(FirestoreRecyclerOptions<FavouritePlaceModel> options);
}
