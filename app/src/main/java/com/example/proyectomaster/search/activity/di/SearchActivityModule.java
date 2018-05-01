package com.example.proyectomaster.search.activity.di;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.model_place_api.Result;
import com.example.proyectomaster.search.activity.SearchActivityInteractor;
import com.example.proyectomaster.search.activity.SearchActivityInteractorImpl;
import com.example.proyectomaster.search.activity.SearchActivityPresenter;
import com.example.proyectomaster.search.activity.SearchActivityPresenterImpl;
import com.example.proyectomaster.search.activity.SearchActivityRepository;
import com.example.proyectomaster.search.activity.SearchActivityRepositoryImpl;
import com.example.proyectomaster.search.activity.adapters.PlacesApiAdapter;
import com.example.proyectomaster.search.activity.ui.SearchActivityView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    SearchActivityView searchActivityView;

    public SearchActivityModule(SearchActivityView searchActivityView) {
        this.searchActivityView = searchActivityView;

    }

    @Singleton
    @Provides
    SearchActivityView provideSearchActivityView() {
        return searchActivityView;
    }

    @Singleton
    @Provides
    SearchActivityPresenter provideSearchActivityPresenter(EventBus eventBus, SearchActivityView searchActivityView, SearchActivityInteractor searchActivityInteractor) {

        return new SearchActivityPresenterImpl(eventBus, searchActivityView, searchActivityInteractor);
    }

    @Singleton
    @Provides
    SearchActivityInteractor provideSearchActivityInteractor(SearchActivityRepository searchActivityRepository) {
        return new SearchActivityInteractorImpl(searchActivityRepository);

    }

    @Singleton
    @Provides
    SearchActivityRepository provideSearchActivityRepository(EventBus eventBus) {
        return new SearchActivityRepositoryImpl(eventBus);
    }

    @Singleton
    @Provides
    PlacesApiAdapter providePlacesApiAdapter(List<Result> data) {
        return new PlacesApiAdapter(data);
    }

    @Singleton
    @Provides
    List<Result> provideData() {
        return new ArrayList<Result>();
    }
}
