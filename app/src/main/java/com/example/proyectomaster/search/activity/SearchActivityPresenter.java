package com.example.proyectomaster.search.activity;

import com.example.proyectomaster.search.activity.events.SearchEvent;

public interface SearchActivityPresenter {

    void onCreate();

    void onDestroy();

    void getResults(String query);

    void onEventMainThread(SearchEvent event);
}
