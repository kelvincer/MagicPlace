package com.example.proyectomaster.photo.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.photo.PhotoInteractor;
import com.example.proyectomaster.photo.PhotoInteractorImpl;
import com.example.proyectomaster.photo.PhotoPresenter;
import com.example.proyectomaster.photo.PhotoPresenterImpl;
import com.example.proyectomaster.photo.PhotoRepository;
import com.example.proyectomaster.photo.PhotoRepositoryImpl;
import com.example.proyectomaster.photo.ui.PhotoView;

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
