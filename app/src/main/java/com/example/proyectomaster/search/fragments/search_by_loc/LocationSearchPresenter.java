package com.example.proyectomaster.search.fragments.search_by_loc;

import com.example.proyectomaster.search.fragments.search_by_loc.events.SearchEvent;

public interface LocationSearchPresenter {

    void onCreate();

    void onDestroy();

    void getPlaces(String query);

    void onEventMainThread(SearchEvent event);
}
