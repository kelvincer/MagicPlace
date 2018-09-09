package com.upv.magicplace.search.activity;

import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.search.activity.events.SearchEvent;
import com.upv.magicplace.search.activity.ui.SearchActivityView;

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
    public void getNextResults(String query) {
        searchActivityInteractor.execute(query);
    }

    @Override
    public void newSearch(String query) {
        searchActivityView.clearData();
        searchActivityView.hideInfoText();
        searchActivityView.hideKeyboard();
        searchActivityView.showProgressBar();
        searchActivityInteractor.execute(query);
    }

    @Subscribe
    @Override
    public void onEventMainThread(SearchEvent event) {
        switch (event.getType()) {

            case SearchEvent.SUCCESS_EVENT:
                searchActivityView.hideProgressBar();
                searchActivityView.clearData(); // because asynchronous process
                searchActivityView.hideInfoText(); // because asynchronous processs
                searchActivityView.updatePlaces(event.getData());
                break;
            case SearchEvent.ERROR:
                searchActivityView.hideProgressBar();
                searchActivityView.showMessage(event.getMessage());
                break;
            default:
                throw new IllegalArgumentException("Invalid event type");
        }
    }
}
