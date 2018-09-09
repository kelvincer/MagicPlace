package com.upv.magicplace.start.fragments.favourites;

import com.upv.magicplace.start.fragments.favourites.events.FavouritesFragEvent;

public interface FavouritesFragmentPresenter {

    void onCreate();

    void onDestroy();

    void getCategories();

    void onEventMainThread(FavouritesFragEvent favouritesFragEvent);
}
