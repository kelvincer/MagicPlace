package com.example.proyectomaster.detail.activity;

import com.example.proyectomaster.detail.activity.events.DetailActivityEvent;

public interface DetailActivityPresenter {

    void onCreate();
    void onDestroy();
    void getPlaceDetail(String placeId);
    void onEventMainThread(DetailActivityEvent event);
}
