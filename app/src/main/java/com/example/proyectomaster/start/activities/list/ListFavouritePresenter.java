package com.example.proyectomaster.start.activities.list;

import com.example.proyectomaster.start.activities.list.entitites.FavouritePlaceModel;
import com.example.proyectomaster.start.activities.list.events.ListFavouriteEvent;

public interface ListFavouritePresenter {

    void onCreate();

    void onDestroy();

    void getFavourites(String category);

    void deleteFavouritePlace(FavouritePlaceModel model);

    void onEventMainThread(ListFavouriteEvent event);
}
