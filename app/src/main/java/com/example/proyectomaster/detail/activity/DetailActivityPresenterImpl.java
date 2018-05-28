package com.example.proyectomaster.detail.activity;

import android.widget.Toast;

import com.example.proyectomaster.detail.activity.events.DetailActivityEvent;
import com.example.proyectomaster.detail.activity.ui.DetailActivityView;
import com.example.proyectomaster.lib.EventBus;

import org.greenrobot.eventbus.Subscribe;

public class DetailActivityPresenterImpl implements DetailActivityPresenter {

    EventBus eventBus;
    DetailActivityView detailActivityView;
    DetailActivityInteractor detailActivityInteractor;

    public DetailActivityPresenterImpl(EventBus eventBus, DetailActivityView detailActivityView, DetailActivityInteractor detailActivityInteractor) {
        this.eventBus = eventBus;
        this.detailActivityView = detailActivityView;
        this.detailActivityInteractor = detailActivityInteractor;
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

    @Subscribe
    @Override
    public void onEventMainThread(DetailActivityEvent event) {

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
}
