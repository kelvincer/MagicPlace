package com.upv.magicplace.photo.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.photo.PhotoInteractor;
import com.upv.magicplace.photo.PhotoInteractorImpl;
import com.upv.magicplace.photo.PhotoPresenter;
import com.upv.magicplace.photo.PhotoPresenterImpl;
import com.upv.magicplace.photo.PhotoRepository;
import com.upv.magicplace.photo.PhotoRepositoryImpl;
import com.upv.magicplace.photo.ui.PhotoView;
import com.upv.magicplace.photo.PhotoPresenterImpl;
import com.upv.magicplace.photo.PhotoRepositoryImpl;
import com.upv.magicplace.photo.ui.PhotoView;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoActivityModule {

    PhotoView photoView;

    public PhotoActivityModule(PhotoView photoView) {
        this.photoView = photoView;
    }

    @Provides
    @PerActivity
    public PhotoView providePhotoView() {
        return photoView;
    }

    @Provides
    @PerActivity
    public PhotoPresenter PhotoPresenterImpl(EventBus eventBus, PhotoInteractor photoInteractor, PhotoView photoView) {
        return new PhotoPresenterImpl(eventBus, photoInteractor, photoView);
    }

    @Provides
    @PerActivity
    public PhotoInteractor PhotoInteractorImpl(PhotoRepository photoRepsitory) {
        return new PhotoInteractorImpl(photoRepsitory);
    }

    @Provides
    @PerActivity
    public PhotoRepository PhotoRepositoryImpl(EventBus eventBus) {
        return new PhotoRepositoryImpl(eventBus);
    }
}
