package com.example.proyectomaster.detail.activity.ui;

import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.entities.FavoritePhotoModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface DetailActivityView {

    void showProgressaBar();

    void hideProgressBar();

    void showMessage(String message);

    void removeFavouriteOption();

    void setResult(Result result);

    void seFavourite(boolean isFavourite);

    void loadFavoritesPhotos();
}
