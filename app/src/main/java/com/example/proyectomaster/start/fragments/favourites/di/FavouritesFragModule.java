package com.example.proyectomaster.start.fragments.favourites.di;

import com.example.proyectomaster.PerFragment;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentInteractor;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentInteractorImpl;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentPresenter;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentPresenterImpl;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentRepository;
import com.example.proyectomaster.start.fragments.favourites.FavouritesFragmentRepositoryImpl;
import com.example.proyectomaster.start.fragments.favourites.ui.FavouritesFragmentView;

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
