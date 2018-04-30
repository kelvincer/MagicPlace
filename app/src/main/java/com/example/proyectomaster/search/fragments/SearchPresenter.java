package com.example.proyectomaster.search.fragments;

import com.example.proyectomaster.search.fragments.events.SearchEvent;

public interface SearchPresenter {

    void onCreate();

    void onDestroy();

    void getPlaces(String query);

    void onEventMainThread(SearchEvent event);
}
