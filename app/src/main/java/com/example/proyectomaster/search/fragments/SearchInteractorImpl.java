package com.example.proyectomaster.search.fragments;

public class SearchInteractorImpl implements SearchInteractor {

    SearchRepository searchRepository;

    public SearchInteractorImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public void execute(String query) {
        searchRepository.getPlaces( query);
    }
}
