package com.example.proyectomaster.search.activity;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.activity.events.SearchEvent;
import com.example.proyectomaster.search.activity.ui.SearchActivityView;

import org.greenrobot.eventbus.Subscribe;

public class SearchActivityPresenterImpl implements SearchActivityPresenter {

    private EventBus eventBus;
    private SearchActivityView searchActivityView;
    private SearchActivityInteractor searchActivityInteractor;

    public SearchActivityPresenterImpl(EventBus eventBus, SearchActivityView searchActivityView, SearchActivityInteractor searchActivityInteractor) {

        this.eventBus = eventBus;
        this.searchActivityView = searchActivityView;
        this.searchActivityInteractor = searchActivityInteractor;
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
    public void getResults(String query) {
        searchActivityInteractor.execute(query);
    }

    @Subscribe
    @Override
    public void onEventMainThread(SearchEvent event) {
        switch (event.getType()) {

            case SearchEvent.GET_EVENT:
                searchActivityView.hideProgressBar();
                searchActivityView.updatePlaces(event.getData());
                break;
            case SearchEvent.ERROR:
                searchActivityView.hideProgressBar();
                searchActivityView.showMessage(event.getMessage());
                break;
        }
    }
}
