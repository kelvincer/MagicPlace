package com.upv.magicplace.start.fragments.favourites;

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
