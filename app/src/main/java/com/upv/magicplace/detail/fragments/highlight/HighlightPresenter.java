package com.upv.magicplace.detail.fragments.highlight;

import com.upv.magicplace.detail.fragments.highlight.events.HighlightEvent;

public interface HighlightPresenter {

    void onCreate();

    void onDestroy();

    void getFavoritePhotos(String placeId);

    void onEventMainThread(HighlightEvent event);
}
