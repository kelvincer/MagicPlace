package com.example.proyectomaster.start.fragments.favourites;

public class FavouritesFragmentInteractorImpl implements FavouritesFragmentInteractor {

    FavouritesFragmentRepository favouritesFragmentRepository;

    public FavouritesFragmentInteractorImpl(FavouritesFragmentRepository favouritesFragmentRepository) {
        this.favouritesFragmentRepository = favouritesFragmentRepository;
    }

    @Override
    public void getCategories() {
        favouritesFragmentRepository.getCategories();
    }
}
