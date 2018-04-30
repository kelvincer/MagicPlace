package com.example.proyectomaster.search.activity;

import android.util.Log;
import android.widget.Toast;

import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.api.PlaceApiService;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.model_place_api.Result;
import com.example.proyectomaster.response.PlaceApiResponse;
import com.example.proyectomaster.search.activity.events.SearchActivityEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivityRepositoryImpl implements SearchActivityRepository {

    private static final String TAG = SearchActivityRepositoryImpl.class.getSimpleName();
    EventBus eventBus;

    public SearchActivityRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;

    }

    @Override
    public void getPlaces() {


    }
}
