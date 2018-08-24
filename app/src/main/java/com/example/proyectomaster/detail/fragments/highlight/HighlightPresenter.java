package com.example.proyectomaster.detail.fragments.highlight;

import com.example.proyectomaster.detail.fragments.highlight.events.HighlightEvent;

public interface HighlightPresenter {

    void onCreate();

    void onDestroy();

    void getFavoritePhotos(String placeId);

    void onEventMainThread(HighlightEvent event);
}
