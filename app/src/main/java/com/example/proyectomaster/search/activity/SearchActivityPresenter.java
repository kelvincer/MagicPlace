package com.example.proyectomaster.search.activity;

import com.example.proyectomaster.search.activity.events.SearchActivityEvent;

public interface SearchActivityPresenter {

    void onCreate();
    void onDestroy();

    void onEventMainThread(SearchActivityEvent event);
}
