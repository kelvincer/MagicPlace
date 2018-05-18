package com.example.proyectomaster.search.fragments.search_by_loc;

import android.util.Log;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.model_place_api.Result;
import com.example.proyectomaster.response.PlaceApiResponse;
import com.example.proyectomaster.search.fragments.search_by_loc.api.GooglePlaceLocationApiService;
import com.example.proyectomaster.search.fragments.search_by_loc.events.SearchEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationSearchRepositoryImpl implements LocationSearchRepository {

    private static final String TAG = LocationSearchRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;
    private GooglePlaceLocationApiService service;

    public LocationSearchRepositoryImpl(EventBus eventBus, GooglePlaceLocationApiService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getPlaces(String location) {
        Call<PlaceApiResponse> call = service.getPlaces(location, "1000", ConstantsHelper.PLACE_API_KEY, CommonHelper.NEXT_PAGE_TOKEN, null);
        call.enqueue(new Callback<PlaceApiResponse>() {
            @Override
            public void onResponse(Call<PlaceApiResponse> call, Response<PlaceApiResponse> response) {
                if (response.isSuccessful()) {
                    PlaceApiResponse placeApiResponse = response.body();
                    CommonHelper.NEXT_PAGE_TOKEN = placeApiResponse.getNextPageToken();
                    if (placeApiResponse.getStatus().equals("OK"))
                        post(SearchEvent.GET_EVENT, placeApiResponse.getResults());
                    else
                        post(SearchEvent.ERROR, placeApiResponse.getStatus());
                } else {

                    Log.d(TAG, response.body() != null ? response.body().getStatus() : "NO RESPONSE");
                    post(SearchEvent.ERROR, response.message());
                }

                Log.d(TAG, response.raw().toString());
            }

            @Override
            public void onFailure(Call<PlaceApiResponse> call, Throwable t) {
                post(SearchEvent.ERROR, t.getLocalizedMessage());
            }
        });
    }

    private void post(int type, String message) {
        SearchEvent event = new SearchEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }

    private void post(int type, List<Result> data) {
        SearchEvent event = new SearchEvent();
        event.setData(data);
        event.setType(type);
        eventBus.post(event);
    }
}
