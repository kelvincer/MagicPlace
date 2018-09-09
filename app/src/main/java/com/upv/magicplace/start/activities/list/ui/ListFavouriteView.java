package com.upv.magicplace.start.activities.list.ui;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.upv.magicplace.start.activities.list.entitites.FavouritePlaceModel;

public interface ListFavouriteView {

    void showMessage(String message);

    void setOptions(FirestoreRecyclerOptions<FavouritePlaceModel> options);
}
