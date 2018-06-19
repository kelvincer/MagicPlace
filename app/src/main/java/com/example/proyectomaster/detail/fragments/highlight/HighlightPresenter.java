package com.example.proyectomaster.detail.fragments.highlight;

import com.example.proyectomaster.detail.fragments.highlight.events.HighlightEvent;

public interface HighlightPresenter {

    void onCreate();

    void onDestroy();

    void getFavoritePhotos(String placeId);

    void uploadPhoto(byte[] data, String id);

    void onEventMainThread(HighlightEvent event);
}
