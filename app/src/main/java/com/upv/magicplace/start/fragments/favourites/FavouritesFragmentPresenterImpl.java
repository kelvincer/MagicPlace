package com.upv.magicplace.start.fragments.favourites;

import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.start.fragments.favourites.events.FavouritesFragEvent;
import com.upv.magicplace.start.fragments.favourites.ui.FavouritesFragmentView;

import org.greenrobot.eventbus.Subscribe;

public class FavouritesFragmentPresenterImpl implements FavouritesFragmentPresenter {

    EventBus eventBus;
    FavouritesFragmentInteractor favouritesFragmentInteractor;
    FavouritesFragmentView favouritesFragmentView;

    public FavouritesFragmentPresenterImpl(EventBus eventBus, FavouritesFragmentInteractor favouritesFragmentInteractor,
                                           FavouritesFragmentView favouritesFragmentView) {
        this.eventBus = eventBus;
        this.favouritesFragmentInteractor = favouritesFragmentInteractor;
        this.favouritesFragmentView = favouritesFragmentView;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void getCategories() {
        favouritesFragmentInteractor.getCategories();
    }

    @Subscribe
    @Override
    public void onEventMainThread(FavouritesFragEvent favouritesFragEvent) {
        switch (favouritesFragEvent.getType()) {

            case FavouritesFragEvent.ON_SUCCESS:
                favouritesFragmentView.setOptions(favouritesFragEvent.getOptions());
                break;
            case FavouritesFragEvent.ON_NO_CATEGORIES:
                favouritesFragmentView.showMessage(favouritesFragEvent.getMessage());
                break;
            case FavouritesFragEvent.ON_ERROR:
                favouritesFragmentView.showMessage(favouritesFragEvent.getMessage());
                break;
        }
    }
}
