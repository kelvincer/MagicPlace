package com.upv.magicplace.detail.activity;

import com.upv.magicplace.detail.activity.events.DetailActivityEvent;
import com.upv.magicplace.detail.activity.events.FirebasePhotoEvent;
import com.upv.magicplace.detail.activity.events.SaveFavouriteEvent;
import com.upv.magicplace.detail.entities.Result;
import com.upv.magicplace.detail.activity.events.DetailActivityEvent;
import com.upv.magicplace.detail.activity.events.FirebasePhotoEvent;
import com.upv.magicplace.detail.activity.events.SaveFavouriteEvent;
import com.upv.magicplace.detail.entities.Result;

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
