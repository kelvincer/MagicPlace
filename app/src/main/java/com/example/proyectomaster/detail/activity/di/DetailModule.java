package com.example.proyectomaster.detail.activity.di;

import android.support.v4.app.FragmentManager;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.detail.activity.DetailActivityInteractor;
import com.example.proyectomaster.detail.activity.DetailActivityInteractorImpl;
import com.example.proyectomaster.detail.activity.DetailActivityPresenter;
import com.example.proyectomaster.detail.activity.DetailActivityPresenterImpl;
import com.example.proyectomaster.detail.activity.DetailActivityRepository;
import com.example.proyectomaster.detail.activity.DetailActivityRepositoryImpl;
import com.example.proyectomaster.detail.activity.adapters.PagerAdapter;
import com.example.proyectomaster.detail.activity.api.DetailPlaceApiService;
import com.example.proyectomaster.detail.activity.ui.DetailActivity;
import com.example.proyectomaster.detail.activity.ui.DetailActivityView;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.di.LibsModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = {LibsModule.class, DetailApiModule.class})
public class DetailModule {

    private DetailActivityView detailActivityView;
    private DetailActivity detailActivity;
    private Integer numTabs;

    public DetailModule(DetailActivityView detailActivityView,
                        DetailActivity detailActivity,
                        Integer numTabs) {
        this.detailActivityView = detailActivityView;
        this.detailActivity = detailActivity;
        this.numTabs = numTabs;
    }

    @ActivityScope
    @Provides
    public DetailActivityView provideDetailActivityView() {
        return detailActivityView;
    }

    @ActivityScope
    @Provides
    public DetailActivityPresenter provideDetailActivityPresenterImpl(EventBus eventBus, DetailActivityView detailActivityView, DetailActivityInteractor detailActivityInteractor) {

        return new DetailActivityPresenterImpl(eventBus, detailActivityView, detailActivityInteractor);
    }

    @ActivityScope
    @Provides
    public DetailActivityInteractor provideDetailActivityInteractorImpl(DetailActivityRepository detailActivityRepository) {
        return new DetailActivityInteractorImpl(detailActivityRepository);
    }

    @ActivityScope
    @Provides
    public DetailActivityRepository provideDetailActivityRepositoryImpl(EventBus eventBus, DetailPlaceApiService detailPlaceApiService) {
        return new DetailActivityRepositoryImpl(eventBus, detailPlaceApiService);
    }
}
