package com.example.proyectomaster.search.fragments.search_by_loc.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.fragments.search_by_loc.LocationSearchInteractor;
import com.example.proyectomaster.search.fragments.search_by_loc.LocationSearchInteractorImpl;
import com.example.proyectomaster.search.fragments.search_by_loc.LocationSearchPresenter;
import com.example.proyectomaster.search.fragments.search_by_loc.LocationSearchPresenterImpl;
import com.example.proyectomaster.search.fragments.search_by_loc.LocationSearchRepository;
import com.example.proyectomaster.search.fragments.search_by_loc.LocationSearchRepositoryImpl;
import com.example.proyectomaster.search.fragments.search_by_loc.api.GooglePlaceLocationApiService;
import com.example.proyectomaster.search.fragments.search_by_loc.ui.LocationSearchView;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationSearchModule {

    LocationSearchView locationSearchView;

    public LocationSearchModule(LocationSearchView locationSearchView) {
        this.locationSearchView = locationSearchView;
    }

    @Provides
    @ActivityScope
    public LocationSearchView provideLocationSearchView() {
        return locationSearchView;
    }

    @Provides
    @ActivityScope
    public LocationSearchPresenter provideLocationSearchPresenterImpl(EventBus eventBus, LocationSearchView locationSearchView, LocationSearchInteractor locationSearchInteractor) {

        return new LocationSearchPresenterImpl(eventBus, locationSearchView, locationSearchInteractor);
    }

    @Provides
    @ActivityScope
    public LocationSearchInteractor provideLocationSearchInteractorImpl(LocationSearchRepository repository) {
        return new LocationSearchInteractorImpl(repository);
    }

    @Provides
    @ActivityScope
    public LocationSearchRepository LocationSearchRepositoryImpl(EventBus eventBus, GooglePlaceLocationApiService service) {
        return new LocationSearchRepositoryImpl(eventBus, service);
    }
}
