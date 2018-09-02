package com.example.proyectomaster.photo;

import com.example.proyectomaster.detail.entities.FavoritePhotoModel;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.photo.events.PhotoEvent;
import com.example.proyectomaster.photo.ui.PhotoView;

import org.greenrobot.eventbus.Subscribe;

public class PhotoPresenterImpl implements PhotoPresenter {

    EventBus eventBus;
    PhotoInteractor photoInteractor;
    PhotoView photoView;

    public PhotoPresenterImpl(EventBus eventBus, PhotoInteractor photoInteractor, PhotoView photoView) {
        this.eventBus = eventBus;
        this.photoInteractor = photoInteractor;
        this.photoView = photoView;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void deletePhoto(FavoritePhotoModel model, String placeId) {
        photoInteractor.deletePhoto(model, placeId);
    }

    @Subscribe
    @Override
    public void onEventMainThread(PhotoEvent event) {
        switch (event.getType()) {

            case PhotoEvent.ON_SUCCESS:
                photoView.showMessage(event.getMessage());
                break;
            case PhotoEvent.ON_ERROR:
                photoView.showMessage(event.getMessage());
                break;
            default:
                throw new IllegalArgumentException("Illegal event type");
        }
    }
}
