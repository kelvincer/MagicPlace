package com.example.proyectomaster.search.fragments.search_by_text;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.fragments.search_by_text.events.SearchEvent;
import com.example.proyectomaster.search.fragments.search_by_text.ui.SearchBytTextView;

import org.greenrobot.eventbus.Subscribe;

public class TextSearchPresenterImpl implements TextSearchPresenter {

    private EventBus eventBus;
    private SearchBytTextView searchBytTextView;
    private SearchGoogleInteractor searchGoogleInteractor;

    public TextSearchPresenterImpl(EventBus eventBus, SearchBytTextView searchBytTextView, SearchGoogleInteractor searchGoogleInteractor) {

        this.eventBus = eventBus;
        this.searchBytTextView = searchBytTextView;
        this.searchGoogleInteractor = searchGoogleInteractor;
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
        searchGoogleInteractor.execute(query);
    }

    @Subscribe
    @Override
    public void onEventMainThread(SearchEvent event) {

        switch (event.getType()) {
            case SearchEvent.GET_EVENT:
                searchBytTextView.setData(event.getData());
                break;
            case SearchEvent.ERROR:
                searchBytTextView.showErrorMessage(event.getMessage());
                break;
        }
    }
}
