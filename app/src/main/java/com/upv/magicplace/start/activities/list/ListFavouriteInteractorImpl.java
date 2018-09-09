package com.upv.magicplace.start.activities.list;

import com.upv.magicplace.start.activities.list.entitites.FavouritePlaceModel;

public class ListFavouriteInteractorImpl implements ListFavouriteInteractor {

    ListFavouriteRepository listFavouriteRepository;


    public ListFavouriteInteractorImpl(ListFavouriteRepository listFavouriteRepository) {
        this.listFavouriteRepository = listFavouriteRepository;
    }

    @Override
    public void getFavouritesByCategory(String category) {
        listFavouriteRepository.getFavouritesByCategory(category);
    }

    @Override
    public void deleteFavouritePlace(FavouritePlaceModel placeId) {
        listFavouriteRepository.deleteFavouritePlace(placeId);
    }
}
