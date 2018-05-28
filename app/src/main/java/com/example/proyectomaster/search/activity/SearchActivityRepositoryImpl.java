package com.example.proyectomaster.search.activity;

import com.example.proyectomaster.lib.EventBus;

public class SearchActivityRepositoryImpl implements SearchActivityRepository {

    private static final String TAG = SearchActivityRepositoryImpl.class.getSimpleName();
    EventBus eventBus;

    public SearchActivityRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPlaces() {
    }
}
