package com.example.proyectomaster.start.fragments.favourites.ui;

import com.example.proyectomaster.start.entities.CategoryModel;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentRepositoryImpl;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface FavouritesFragmentView {

    void showMessage(String message);

    void setOptions(FirestoreRecyclerOptions<CategoryModel> options);
}
