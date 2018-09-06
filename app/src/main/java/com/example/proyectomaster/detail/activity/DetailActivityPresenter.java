package com.example.proyectomaster.detail.activity;

import com.example.proyectomaster.detail.activity.events.DetailActivityEvent;
import com.example.proyectomaster.detail.activity.events.FirebasePhotoEvent;
import com.example.proyectomaster.detail.activity.events.SaveFavouriteEvent;
import com.example.proyectomaster.detail.entities.Result;

public interface DetailActivityPresenter {

    void onCreate();

    void onDestroy();

    void getPlaceDetail(String placeId);

    void uploadPhoto(byte[] data, String id);

    void saveFavourite(Result result);

    void checkIfFavourite(Result result);

    void onEventApiThread(DetailActivityEvent event);

    void onEventFirebaseThread(FirebasePhotoEvent firebasePhotoEvent);

    void onEventSaveFavorite(SaveFavouriteEvent saveFavouriteEvent);
}
