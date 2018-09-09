package com.upv.magicplace.search.activity;

import com.upv.magicplace.search.activity.events.SearchEvent;
import com.upv.magicplace.search.activity.events.SearchEvent;

public interface SearchActivityPresenter {

    void onCreate();

    void onDestroy();

    void getNextResults(String query);

    void newSearch(String query);

    void onEventMainThread(SearchEvent event);
}
