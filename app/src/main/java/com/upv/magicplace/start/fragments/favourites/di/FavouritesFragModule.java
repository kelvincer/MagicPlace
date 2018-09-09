package com.upv.magicplace.start.fragments.favourites.di;

import com.upv.magicplace.PerFragment;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentInteractor;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentInteractorImpl;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentPresenter;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentPresenterImpl;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentRepository;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentRepositoryImpl;
import com.upv.magicplace.start.fragments.favourites.ui.FavouritesFragmentView;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentRepositoryImpl;
import com.upv.magicplace.start.fragments.favourites.ui.FavouritesFragmentView;

import dagger.Module;
import dagger.Provides;

@Module
public class FavouritesFragModule {

    FavouritesFragmentView favouritesFragmentView;

    public FavouritesFragModule(FavouritesFragmentView favouritesFragmentView) {
        this.favouritesFragmentView = favouritesFragmentView;
    }

    @PerFragment
    @Provides
    public FavouritesFragmentView getFavouritesFragmentView() {
        return favouritesFragmentView;
    }

    @PerFragment
    @Provides
    public FavouritesFragmentPresenter provideFavouritesFragmentPresenterImpl(EventBus eventBus, FavouritesFragmentInteractor favouritesFragmentInteractor,
                                                                              FavouritesFragmentView favouritesFragmentView) {
        return new FavouritesFragmentPresenterImpl(eventBus, favouritesFragmentInteractor,
                favouritesFragmentView);
    }

    @PerFragment
    @Provides
    public FavouritesFragmentInteractor provideFavouritesFragmentInteractorImpl(FavouritesFragmentRepository favouritesFragmentRepository) {
        return new FavouritesFragmentInteractorImpl(favouritesFragmentRepository);
    }

    @PerFragment
    @Provides
    public FavouritesFragmentRepository provideFavouritesFragmentRepositoryImpl(EventBus eventBus) {
        return new FavouritesFragmentRepositoryImpl(eventBus);
    }
}
