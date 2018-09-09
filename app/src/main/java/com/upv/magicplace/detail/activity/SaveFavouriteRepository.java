package com.upv.magicplace.detail.activity;

import com.upv.magicplace.detail.entities.Result;

public interface SaveFavouriteRepository {

    void saveFavourite(Result resulte);

    void checkIfFavourite(Result result);
}
