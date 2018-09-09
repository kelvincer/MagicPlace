package com.upv.magicplace.start.activities.list;

import com.upv.magicplace.start.activities.list.entitites.FavouritePlaceModel;
import com.upv.magicplace.start.activities.list.events.ListFavouriteEvent;

public interface ListFavouritePresenter {

    void onCreate();

    void onDestroy();

    void getFavourites(String category);

    void deleteFavouritePlace(FavouritePlaceModel model);

    void onEventMainThread(ListFavouriteEvent event);
}
