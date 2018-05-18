package com.example.proyectomaster.search.fragments.search_by_loc;

public class LocationSearchInteractorImpl implements LocationSearchInteractor {

    LocationSearchRepository repository;

    public LocationSearchInteractorImpl(LocationSearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String location) {
        repository.getPlaces(location);
    }
}
