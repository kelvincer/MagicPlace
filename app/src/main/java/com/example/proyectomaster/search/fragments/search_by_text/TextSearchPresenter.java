package com.example.proyectomaster.search.fragments.search_by_text;

import com.example.proyectomaster.search.fragments.search_by_text.events.SearchEvent;

public interface TextSearchPresenter {

    void onCreate();

    void onDestroy();

    void getPlaces(String query);

    void onEventMainThread(SearchEvent event);
}
