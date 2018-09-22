package com.upv.magicplace.detail.activity;

import com.upv.magicplace.detail.activity.events.DetailActivityEvent;
import com.upv.magicplace.detail.activity.events.FirebasePhotoEvent;
import com.upv.magicplace.detail.activity.events.SaveFavouriteEvent;
import com.upv.magicplace.detail.activity.ui.DetailActivityView;
import com.upv.magicplace.detail.entities.Result;
import com.upv.magicplace.lib.EventBus;

import org.greenrobot.eventbus.Subscribe;

public class DetailActivityPresenterImpl implements DetailActivityPresenter {

    private EventBus eventBus;
    private DetailActivityView detailActivityView;
    private DetailActivityInteractor detailActivityInteractor;
    private FirebasePhotoInteractor firebasePhotoInteractor;
    private SaveFavouriteInteractor saveFavouriteInteractor;


    public DetailActivityPresenterImpl(EventBus eventBus, DetailActivityView detailActivityView
            , DetailActivityInteractor detailActivityInteractor
            , FirebasePhotoInteractor firebasePhotoInteractor
            , SaveFavouriteInteractor saveFavouriteInteractor) {
        this.eventBus = eventBus;
        this.detailActivityView = detailActivityView;
        this.detailActivityInteractor = detailActivityInteractor;
        this.firebasePhotoInteractor = firebasePhotoInteractor;
        this.saveFavouriteInteractor = saveFavouriteInteractor;
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
    public void getPlaceDetail(String placeId) {
        detailActivityInteractor.execute(placeId);
    }

    @Override
    public void uploadPhoto(byte[] data, String id) {
        firebasePhotoInteractor.uploadPhoto(data, id);
    }

    @Override
    public void saveFavourite(Result result) {
        saveFavouriteInteractor.saveFavourite(result);
    }

    @Override
    public void checkIfFavourite(Result result) {
        saveFavouriteInteractor.checkIfFavourite(result);
    }

    @Subscribe
    @Override
    public void onEventApiThread(DetailActivityEvent event) {
        switch (event.getType()) {

            case DetailActivityEvent.GET_DETAIL_SUCCESS:
                detailActivityView.hideProgressBar();
                detailActivityView.setResult(event.getData());
                break;
            case DetailActivityEvent.GET_DETAIL_ERROR:
                detailActivityView.hideProgressBar();
                detailActivityView.showMessage(event.getMessage());
                break;
        }
    }

    @Subscribe
    @Override
    public void onEventFirebaseThread(FirebasePhotoEvent firebasePhotoEvent) {
        switch (firebasePhotoEvent.getType()) {
            case FirebasePhotoEvent.ON_SUCCESS_UPLOAD_PHOTO:
                detailActivityView.showMessage(firebasePhotoEvent.getMessage());
                detailActivityView.loadFavoritesPhotos();
                break;
            case FirebasePhotoEvent.ERROR:
                detailActivityView.showMessage(firebasePhotoEvent.getMessage());
                break;
        }
    }

    @Subscribe
    @Override
    public void onEventSaveFavorite(SaveFavouriteEvent saveFavouriteEvent) {
        switch (saveFavouriteEvent.getType()) {

            case SaveFavouriteEvent.ON_SUCCESS:
                detailActivityView.showMessage(saveFavouriteEvent.getMessage());
                detailActivityView.removeFavouriteOption();
                break;
            case SaveFavouriteEvent.ON_ERROR:
                detailActivityView.showMessage(saveFavouriteEvent.getMessage());
                break;
            case SaveFavouriteEvent.IS_FAVOURITE:
                detailActivityView.seFavourite(saveFavouriteEvent.isFavourite());
                break;
            default:
                throw new IllegalArgumentException("Illegal event id");
        }
    }
}
