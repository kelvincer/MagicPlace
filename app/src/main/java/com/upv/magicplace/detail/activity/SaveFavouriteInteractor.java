package com.upv.magicplace.detail.activity;

import com.upv.magicplace.detail.entities.Result;

public interface SaveFavouriteInteractor {

    void saveFavourite(Result result);

    void checkIfFavourite(Result result);
}
