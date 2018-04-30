package com.example.proyectomaster.search.fragments;

import android.util.Log;

import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.api.PlaceApiService;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.model_place_api.Result;
import com.example.proyectomaster.response.PlaceApiResponse;
import com.example.proyectomaster.search.activity.events.SearchActivityEvent;
import com.example.proyectomaster.search.fragments.events.SearchEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepositoryImpl implements SearchRepository {

    private static final String TAG = SearchRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;
    private PlaceApiService service;

    public SearchRepositoryImpl(EventBus eventBus, PlaceApiService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getPlaces(String query) {
        Call<PlaceApiResponse> call = service.getPlaces(query, ConstantsHelper.PLACE_API_KEY);
        call.enqueue(new Callback<PlaceApiResponse>() {
            @Override
            public void onResponse(Call<PlaceApiResponse> call, Response<PlaceApiResponse> response) {
                if (response.isSuccessful()) {
                    PlaceApiResponse placeApiResponse = response.body();
                    post(SearchActivityEvent.GET_EVENT, placeApiResponse.getResults());
                } else {

                    Log.d(TAG, response.body() != null ? response.body().getStatus() : "NO RESPONSE");
                }

                Log.d(TAG, response.raw().toString());
            }

            @Override
            public void onFailure(Call<PlaceApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void post(int type, List<Result> data) {
        SearchEvent event = new SearchEvent();
        event.setData(data);
        event.setType(type);
        eventBus.post(event);
    }
}
