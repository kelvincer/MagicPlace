package com.upv.magicplace.start.activities.list;

import com.upv.magicplace.start.activities.list.entitites.FavouritePlaceModel;

public interface ListFavouriteInteractor {

    void getFavouritesByCategory(String category);

    void deleteFavouritePlace(FavouritePlaceModel model);
}
