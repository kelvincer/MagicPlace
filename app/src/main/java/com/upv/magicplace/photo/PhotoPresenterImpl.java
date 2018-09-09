package com.upv.magicplace.photo;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.photo.events.PhotoEvent;
import com.upv.magicplace.photo.ui.PhotoView;

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
