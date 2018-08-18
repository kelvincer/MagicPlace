package com.example.proyectomaster.detail.fragments.highlight;

import com.example.proyectomaster.detail.fragments.highlight.events.HighlightEvent;
import com.example.proyectomaster.detail.fragments.highlight.ui.HighlightView;
import com.example.proyectomaster.lib.EventBus;

import org.greenrobot.eventbus.Subscribe;

public class HighlightPresenterImpl implements HighlightPresenter {

    private EventBus eventBus;
    private HighlightView highlightView;
    private HighlightInteractor highlightInteractor;

    public HighlightPresenterImpl(EventBus eventBus, HighlightView highlightView, HighlightInteractor highlightInteractor) {

        this.eventBus = eventBus;
        this.highlightView = highlightView;
        this.highlightInteractor = highlightInteractor;
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
    public void getFavoritePhotos(String placeId) {
        highlightInteractor.execute(placeId);
    }

    @Override
    public void uploadPhoto(byte[] data, String placeId) {
        highlightInteractor.uploadPhoto(data, placeId);
    }

    @Subscribe
    @Override
    public void onEventMainThread(HighlightEvent event) {

        switch (event.getType()) {
            case HighlightEvent.GET_PHOTOS_SUCCESS:
                highlightView.setOptions(event.getOpciones());
                break;
            case HighlightEvent.ERROR:
                highlightView.showMessage(event.getMessage());
                break;

            /*case HighlightEvent.ON_SUCCESS_UPLOAD_PHOTO:
                highlightView.loadFavoritePhotos();
                break;*/
            case HighlightEvent.NO_PHOTOS:
                highlightView.showMessage(event.getMessage());
                break;
        }
    }
}
