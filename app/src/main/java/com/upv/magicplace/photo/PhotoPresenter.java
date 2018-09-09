package com.upv.magicplace.photo;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.upv.magicplace.photo.events.PhotoEvent;

public interface PhotoPresenter {

    void onCreate();

    void onDestroy();

    void deletePhoto(FavoritePhotoModel model, String placeId);

    void onEventMainThread(PhotoEvent event);

}
