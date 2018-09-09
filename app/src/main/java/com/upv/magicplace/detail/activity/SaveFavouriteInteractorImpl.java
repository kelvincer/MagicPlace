package com.upv.magicplace.detail.activity;

import com.upv.magicplace.detail.entities.Result;

public class SaveFavouriteInteractorImpl implements SaveFavouriteInteractor {

    SaveFavouriteRepository saveFavouriteRepository;

    public SaveFavouriteInteractorImpl(SaveFavouriteRepository saveFavouriteRepository) {
        this.saveFavouriteRepository = saveFavouriteRepository;
    }

    @Override
    public void saveFavourite(Result result) {
        saveFavouriteRepository.saveFavourite(result);
    }

    @Override
    public void checkIfFavourite(Result result) {
        saveFavouriteRepository.checkIfFavourite(result);
    }
}
