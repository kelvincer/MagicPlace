package com.example.proyectomaster.search.fragments;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.fragments.events.SearchEvent;
import com.example.proyectomaster.search.fragments.ui.SearchBytTextView;

import org.greenrobot.eventbus.Subscribe;

public class SearchPresenterImpl implements SearchPresenter {

    private EventBus eventBus;
    private SearchBytTextView searchBytTextView;
    private SearchInteractor searchInteractor;

    public SearchPresenterImpl(EventBus eventBus, SearchBytTextView searchBytTextView, SearchInteractor searchInteractor) {

        this.eventBus = eventBus;
        this.searchBytTextView = searchBytTextView;
        this.searchInteractor = searchInteractor;
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
        searchInteractor.execute(query);
    }

    @Subscribe
    @Override
    public void onEventMainThread(SearchEvent event) {
        searchBytTextView.setData(event.getData());
    }
}
