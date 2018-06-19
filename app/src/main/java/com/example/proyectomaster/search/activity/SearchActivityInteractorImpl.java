package com.example.proyectomaster.search.activity;

public class SearchActivityInteractorImpl implements SearchActivityInteractor {

    SearchActivityRepository searchActivityRepository;

    public SearchActivityInteractorImpl(SearchActivityRepository searchActivityRepository){
        this.searchActivityRepository = searchActivityRepository;
    }

    @Override
    public void execute(String query) {
        searchActivityRepository.getPlaces(query);
    }
}
