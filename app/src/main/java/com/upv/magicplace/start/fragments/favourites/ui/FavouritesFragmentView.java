package com.upv.magicplace.start.fragments.favourites.ui;

import com.upv.magicplace.start.entities.CategoryModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface FavouritesFragmentView {

    void showMessage(String message);

    void setOptions(FirestoreRecyclerOptions<CategoryModel> options);
}
