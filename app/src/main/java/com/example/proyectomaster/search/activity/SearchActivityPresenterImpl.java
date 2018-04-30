package com.example.proyectomaster.search.activity;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.activity.events.SearchActivityEvent;
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

    @Subscribe
    @Override
    public void onEventMainThread(SearchActivityEvent event) {
    }
}
