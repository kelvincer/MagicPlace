package com.example.proyectomaster.search.fragments.search_by_text;

public class SearchGoogleInteractorImpl implements SearchGoogleInteractor {

    TextSearchRepository textSearchRepository;

    public SearchGoogleInteractorImpl(TextSearchRepository textSearchRepository) {
        this.textSearchRepository = textSearchRepository;

    }

    @Override
    public void execute(String query) {
        textSearchRepository.getPlaces( query);
    }
}
