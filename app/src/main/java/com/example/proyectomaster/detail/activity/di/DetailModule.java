package com.example.proyectomaster.detail.activity.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.detail.activity.DetailActivityInteractor;
import com.example.proyectomaster.detail.activity.DetailActivityInteractorImpl;
import com.example.proyectomaster.detail.activity.DetailActivityPresenter;
import com.example.proyectomaster.detail.activity.DetailActivityPresenterImpl;
import com.example.proyectomaster.detail.activity.DetailActivityRepository;
import com.example.proyectomaster.detail.activity.DetailActivityRepositoryImpl;
import com.example.proyectomaster.detail.activity.FirebasePhotoInteractor;
import com.example.proyectomaster.detail.activity.FirebasePhotoInteractorImpl;
import com.example.proyectomaster.detail.activity.FirebasePhotoRepository;
import com.example.proyectomaster.detail.activity.FirebasePhotoRepositoryImpl;
import com.example.proyectomaster.detail.activity.api.DetailPlaceApiService;
import com.example.proyectomaster.detail.activity.ui.DetailActivityView;
import com.example.proyectomaster.lib.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailModule {

    private DetailActivityView detailActivityView;

    public DetailModule(DetailActivityView detailActivityView) {
        this.detailActivityView = detailActivityView;
    }

    @PerActivity
    @Provides
    public DetailActivityView provideDetailActivityView() {
        return detailActivityView;
    }

    @PerActivity
    @Provides
    public DetailActivityPresenter provideDetailActivityPresenterImpl(EventBus eventBus, DetailActivityView detailActivityView,
                                                                      DetailActivityInteractor detailActivityInteractor,
                                                                      FirebasePhotoInteractor firebasePhotoInteractor) {

        return new DetailActivityPresenterImpl(eventBus, detailActivityView, detailActivityInteractor, firebasePhotoInteractor);
    }

    @PerActivity
    @Provides
    public DetailActivityInteractor provideDetailActivityInteractorImpl(DetailActivityRepository detailActivityRepository) {
        return new DetailActivityInteractorImpl(detailActivityRepository);
    }

    @PerActivity
    @Provides
    public FirebasePhotoInteractor provideFirebasePhotoInteractorImpl(FirebasePhotoRepository firebasePhotoRepository) {
        return new FirebasePhotoInteractorImpl(firebasePhotoRepository);
    }

    @PerActivity
    @Provides
    public DetailActivityRepository provideDetailActivityRepositoryImpl(EventBus eventBus, DetailPlaceApiService detailPlaceApiService) {
        return new DetailActivityRepositoryImpl(eventBus, detailPlaceApiService);
    }

    @PerActivity
    @Provides
    public FirebasePhotoRepository provideFirebasePhotoRepositoryImpl(EventBus eventBus) {
        return new FirebasePhotoRepositoryImpl(eventBus);
    }
}
