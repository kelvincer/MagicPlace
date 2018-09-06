package com.example.proyectomaster.start.activities.list;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.start.activities.list.entitites.FavouritePlaceModel;
import com.example.proyectomaster.start.activities.list.events.ListFavouriteEvent;
import com.example.proyectomaster.start.activities.list.ui.ListFavouriteView;

import org.greenrobot.eventbus.Subscribe;

public class ListFavouritePresenterImpl implements ListFavouritePresenter {

    EventBus eventBus;
    ListFavouriteView listFavouriteView;
    ListFavouriteInteractor listFavouriteInteractor;

    public ListFavouritePresenterImpl(EventBus eventBus, ListFavouriteView listFavouriteView, ListFavouriteInteractor listFavouriteInteractor) {
        this.eventBus = eventBus;
        this.listFavouriteView = listFavouriteView;
        this.listFavouriteInteractor = listFavouriteInteractor;
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
    public void getFavourites(String category) {
        listFavouriteInteractor.getFavouritesByCategory(category);
    }

    @Override
    public void deleteFavouritePlace(FavouritePlaceModel model) {
        listFavouriteInteractor.deleteFavouritePlace(model);
    }

    @Subscribe
    @Override
    public void onEventMainThread(ListFavouriteEvent event) {

        switch (event.getType()) {

            case ListFavouriteEvent.ON_SUCCESS:
                listFavouriteView.setOptions(event.getOptions());
                break;
            case ListFavouriteEvent.ON_DELETE_SUCCESS:
                listFavouriteView.showMessage(event.getMessage());
                break;
            case ListFavouriteEvent.ON_ERROR:
                listFavouriteView.showMessage(event.getMessage());
                break;
        }
    }
}
