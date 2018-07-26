package com.example.proyectomaster.search.activity.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.ImageLoader;
import com.example.proyectomaster.search.activity.adapters.RecyclerViewResultAdapter;
import com.example.proyectomaster.search.activity.api.PlacesApiService;
import com.example.proyectomaster.search.entities.Result;
import com.example.proyectomaster.search.activity.SearchActivityInteractor;
import com.example.proyectomaster.search.activity.SearchActivityInteractorImpl;
import com.example.proyectomaster.search.activity.SearchActivityPresenter;
import com.example.proyectomaster.search.activity.SearchActivityPresenterImpl;
import com.example.proyectomaster.search.activity.SearchActivityRepository;
import com.example.proyectomaster.search.activity.SearchActivityRepositoryImpl;
import com.example.proyectomaster.search.activity.adapters.OnItemClickListener;
import com.example.proyectomaster.search.activity.ui.SearchActivityView;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

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
    SearchActivityRepository provideSearchActivityRepository(EventBus eventBus, PlacesApiService service) {
        return new SearchActivityRepositoryImpl(eventBus, service);
    }

    @ActivityScope
    @Provides
    RecyclerViewResultAdapter providePlacesApiAdapter(List<Result> data, OnItemClickListener onItemClickListener, ImageLoader imageLoader) {
        return new RecyclerViewResultAdapter(data, onItemClickListener, imageLoader);
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

    @ActivityScope
    @Provides
    public PlacesApiService providePlacesApiService(Retrofit retrofit) {
        return retrofit.create(PlacesApiService.class);
    }
}
