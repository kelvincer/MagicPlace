package com.example.proyectomaster.search.fragments.search_by_loc;

import android.util.Log;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.fragments.search_by_loc.events.SearchEvent;
import com.example.proyectomaster.search.fragments.search_by_loc.ui.LocationSearchView;

import org.greenrobot.eventbus.Subscribe;

public class LocationSearchPresenterImpl implements LocationSearchPresenter {

    private EventBus eventBus;
    private LocationSearchView locationSearchView;
    private LocationSearchInteractor locationSearchInteractor;

    public LocationSearchPresenterImpl(EventBus eventBus, LocationSearchView locationSearchView, LocationSearchInteractor locationSearchInteractor) {
        this.eventBus = eventBus;
        this.locationSearchView = locationSearchView;
        this.locationSearchInteractor = locationSearchInteractor;
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
    public void getPlaces(String query) {
        locationSearchInteractor.execute(query);
    }

    @Subscribe
    @Override
    public void onEventMainThread(SearchEvent event) {

        Log.d("TAG", "" + event.getType());
        switch (event.getType()) {
            case SearchEvent.GET_EVENT:
                locationSearchView.setData(event.getData());
                break;
            case SearchEvent.ERROR:
                locationSearchView.showErrorMessage(event.getMessage());
                break;
        }
    }
}
