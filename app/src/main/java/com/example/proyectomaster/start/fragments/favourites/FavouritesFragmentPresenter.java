package com.example.proyectomaster.start.fragments.favourites;

import com.example.proyectomaster.start.fragments.favourites.events.FavouritesFragEvent;

public interface FavouritesFragmentPresenter {

    void onCreate();

    void onDestroy();

    void getCategories();

    void onEventMainThread(FavouritesFragEvent favouritesFragEvent);
}
