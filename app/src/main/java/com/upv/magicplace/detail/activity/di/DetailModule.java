package com.upv.magicplace.detail.activity.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.detail.activity.DetailActivityInteractor;
import com.upv.magicplace.detail.activity.DetailActivityInteractorImpl;
import com.upv.magicplace.detail.activity.DetailActivityPresenter;
import com.upv.magicplace.detail.activity.DetailActivityPresenterImpl;
import com.upv.magicplace.detail.activity.DetailActivityRepository;
import com.upv.magicplace.detail.activity.DetailActivityRepositoryImpl;
import com.upv.magicplace.detail.activity.FirebasePhotoInteractor;
import com.upv.magicplace.detail.activity.FirebasePhotoInteractorImpl;
import com.upv.magicplace.detail.activity.FirebasePhotoRepository;
import com.upv.magicplace.detail.activity.FirebasePhotoRepositoryImpl;
import com.upv.magicplace.detail.activity.SaveFavouriteInteractor;
import com.upv.magicplace.detail.activity.SaveFavouriteInteractorImpl;
import com.upv.magicplace.detail.activity.SaveFavouriteRepository;
import com.upv.magicplace.detail.activity.SaveFavouriteRepositoryImpl;
import com.upv.magicplace.detail.activity.api.DetailPlaceApiService;
import com.upv.magicplace.detail.activity.ui.DetailActivityView;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.detail.activity.FirebasePhotoRepositoryImpl;
import com.upv.magicplace.detail.activity.SaveFavouriteRepositoryImpl;
import com.upv.magicplace.detail.activity.api.DetailPlaceApiService;
import com.upv.magicplace.detail.activity.ui.DetailActivityView;

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
                                                                      FirebasePhotoInteractor firebasePhotoInteractor,
                                                                      SaveFavouriteInteractor saveFavouriteInteractor) {

        return new DetailActivityPresenterImpl(eventBus,
                detailActivityView,
                detailActivityInteractor,
                firebasePhotoInteractor,
                saveFavouriteInteractor);
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

    @PerActivity
    @Provides
    public SaveFavouriteInteractor provideSaveFavouriteInteractorImpl(SaveFavouriteRepository saveFavouriteRepository) {
        return new SaveFavouriteInteractorImpl(saveFavouriteRepository);
    }

    @PerActivity
    @Provides
    public SaveFavouriteRepository SaveFavouriteRepositoryImpl(EventBus eventBus) {
        return new SaveFavouriteRepositoryImpl(eventBus);
    }

}
