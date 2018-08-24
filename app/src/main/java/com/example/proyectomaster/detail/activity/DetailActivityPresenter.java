package com.example.proyectomaster.detail.activity;

import com.example.proyectomaster.detail.activity.events.DetailActivityEvent;
import com.example.proyectomaster.detail.activity.events.FirebasePhotoEvent;

public interface DetailActivityPresenter {

    void onCreate();

    void onDestroy();

    void getPlaceDetail(String placeId);

    void uploadPhoto(byte[] data, String id);

    void onEventApiThread(DetailActivityEvent event);

    void onEventFirebaseThread(FirebasePhotoEvent firebasePhotoEvent);
}
