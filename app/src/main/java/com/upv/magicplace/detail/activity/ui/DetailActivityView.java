package com.upv.magicplace.detail.activity.ui;

import com.upv.magicplace.detail.entities.Result;

public interface DetailActivityView {

    void showProgressaBar();

    void hideProgressBar();

    void showMessage(String message);

    void removeFavouriteOption();

    void setResult(Result result);

    void seFavourite(boolean isFavourite);

    void loadFavoritesPhotos();
}
