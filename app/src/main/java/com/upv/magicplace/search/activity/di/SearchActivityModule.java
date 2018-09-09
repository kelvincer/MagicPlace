package com.upv.magicplace.search.activity.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.lib.ImageLoader;
import com.upv.magicplace.search.activity.adapters.RecyclerViewResultAdapter;
import com.upv.magicplace.search.activity.api.PlacesApiService;
import com.upv.magicplace.search.entities.Result;
import com.upv.magicplace.search.activity.SearchActivityInteractor;
import com.upv.magicplace.search.activity.SearchActivityInteractorImpl;
import com.upv.magicplace.search.activity.SearchActivityPresenter;
import com.upv.magicplace.search.activity.SearchActivityPresenterImpl;
import com.upv.magicplace.search.activity.SearchActivityRepository;
import com.upv.magicplace.search.activity.SearchActivityRepositoryImpl;
import com.upv.magicplace.search.activity.adapters.OnItemClickListener;
import com.upv.magicplace.search.activity.ui.SearchActivityView;
import com.upv.magicplace.search.activity.adapters.OnItemClickListener;
import com.upv.magicplace.search.activity.adapters.RecyclerViewResultAdapter;
import com.upv.magicplace.search.activity.api.PlacesApiService;
import com.upv.magicplace.search.activity.ui.SearchActivityView;
import com.upv.magicplace.search.entities.Result;

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

    @PerActivity
    @Provides
    SearchActivityView provideSearchActivityView() {
        return searchActivityView;
    }

    @PerActivity
    @Provides
    SearchActivityPresenter provideSearchActivityPresenter(EventBus eventBus, SearchActivityView searchActivityView, SearchActivityInteractor searchActivityInteractor) {

        return new SearchActivityPresenterImpl(eventBus, searchActivityView, searchActivityInteractor);
    }

    @PerActivity
    @Provides
    SearchActivityInteractor provideSearchActivityInteractor(SearchActivityRepository searchActivityRepository) {
        return new SearchActivityInteractorImpl(searchActivityRepository);

    }

    @PerActivity
    @Provides
    SearchActivityRepository provideSearchActivityRepository(EventBus eventBus, PlacesApiService service) {
        return new SearchActivityRepositoryImpl(eventBus, service);
    }

    @PerActivity
    @Provides
    RecyclerViewResultAdapter providePlacesApiAdapter(List<Result> data, OnItemClickListener onItemClickListener, ImageLoader imageLoader) {
        return new RecyclerViewResultAdapter(data, onItemClickListener, imageLoader);
    }

    @PerActivity
    @Provides
    List<Result> provideData() {
        return new ArrayList<>();
    }

    @PerActivity
    @Provides
    OnItemClickListener provideOnItemClickListener() {
        return this.onItemClickListener;
    }

    @PerActivity
    @Provides
    public PlacesApiService providePlacesApiService(Retrofit retrofit) {
        return retrofit.create(PlacesApiService.class);
    }
}
