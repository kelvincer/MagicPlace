package com.example.proyectomaster.search.activity.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.entities.Result;
import com.example.proyectomaster.search.activity.SearchActivityInteractor;
import com.example.proyectomaster.search.activity.SearchActivityInteractorImpl;
import com.example.proyectomaster.search.activity.SearchActivityPresenter;
import com.example.proyectomaster.search.activity.SearchActivityPresenterImpl;
import com.example.proyectomaster.search.activity.SearchActivityRepository;
import com.example.proyectomaster.search.activity.SearchActivityRepositoryImpl;
import com.example.proyectomaster.search.activity.adapters.OnItemClickListener;
import com.example.proyectomaster.search.activity.adapters.PlacesApiAdapter;
import com.example.proyectomaster.search.activity.ui.SearchActivityView;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    SearchActivityView searchActivityView;
    OnItemClickListener onItemClickListener;

    public SearchActivityModule(SearchActivityView searchActivityView, OnItemClickListener onItemClickListener) {
        this.searchActivityView = searchActivityView;
        this.onItemClickListener = onItemClickListener;
    }

    @ActivityScope
    @Provides
    SearchActivityView provideSearchActivityView() {
        return searchActivityView;
    }

    @ActivityScope
    @Provides
    SearchActivityPresenter provideSearchActivityPresenter(EventBus eventBus, SearchActivityView searchActivityView, SearchActivityInteractor searchActivityInteractor) {

        return new SearchActivityPresenterImpl(eventBus, searchActivityView, searchActivityInteractor);
    }

    @ActivityScope
    @Provides
    SearchActivityInteractor provideSearchActivityInteractor(SearchActivityRepository searchActivityRepository) {
        return new SearchActivityInteractorImpl(searchActivityRepository);

    }

    @ActivityScope
    @Provides
    SearchActivityRepository provideSearchActivityRepository(EventBus eventBus) {
        return new SearchActivityRepositoryImpl(eventBus);
    }

    @ActivityScope
    @Provides
    PlacesApiAdapter providePlacesApiAdapter(List<Result> data, OnItemClickListener onItemClickListener) {
        return new PlacesApiAdapter(data, onItemClickListener);
    }

    @ActivityScope
    @Provides
    List<Result> provideData() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    OnItemClickListener provideOnItemClickListener() {
        return this.onItemClickListener;
    }
}
