package com.example.proyectomaster.detail.activity;

import android.widget.Toast;

import com.example.proyectomaster.detail.activity.events.DetailActivityEvent;
import com.example.proyectomaster.detail.activity.events.FirebasePhotoEvent;
import com.example.proyectomaster.detail.activity.ui.DetailActivityView;
import com.example.proyectomaster.detail.fragments.highlight.events.HighlightEvent;
import com.example.proyectomaster.lib.EventBus;

import org.greenrobot.eventbus.Subscribe;

public class DetailActivityPresenterImpl implements DetailActivityPresenter {

    EventBus eventBus;
    DetailActivityView detailActivityView;
    DetailActivityInteractor detailActivityInteractor;
    FirebasePhotoInteractor firebasePhotoInteractor;

    public DetailActivityPresenterImpl(EventBus eventBus, DetailActivityView detailActivityView
            , DetailActivityInteractor detailActivityInteractor
            , FirebasePhotoInteractor firebasePhotoInteractor) {
        this.eventBus = eventBus;
        this.detailActivityView = detailActivityView;
        this.detailActivityInteractor = detailActivityInteractor;
        this.firebasePhotoInteractor = firebasePhotoInteractor;
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

    @Subscribe
    @Override
    public void onEventApiThread(DetailActivityEvent event) {
        switch (event.getType()) {

            case DetailActivityEvent.GET_DETAIL:
                detailActivityView.showMessage("GOOD");
                detailActivityView.setResult(event.getData());
                break;
            case DetailActivityEvent.GET_DETAIL_ERROR:
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
                break;
            case FirebasePhotoEvent.ERROR:
                detailActivityView.showMessage(firebasePhotoEvent.getMessage());
                break;
        }
    }
}
