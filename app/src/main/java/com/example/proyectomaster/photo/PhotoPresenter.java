package com.example.proyectomaster.photo;

import com.example.proyectomaster.detail.entities.FavoritePhotoModel;
import com.example.proyectomaster.photo.events.PhotoEvent;

public interface PhotoPresenter {

    void onCreate();

    void onDestroy();

    void deletePhoto(FavoritePhotoModel model, String placeId);

    void onEventMainThread(PhotoEvent event);

}
