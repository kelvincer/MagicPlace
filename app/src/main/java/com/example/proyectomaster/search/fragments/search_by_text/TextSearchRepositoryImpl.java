package com.example.proyectomaster.search.fragments.search_by_text;

import android.util.Log;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.search.fragments.search_by_text.api.GooglePlaceTextApiService;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.search.entities.Result;
import com.example.proyectomaster.response.PlaceApiResponse;
import com.example.proyectomaster.search.fragments.search_by_text.events.SearchEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextSearchRepositoryImpl implements TextSearchRepository {

    private static final String TAG = TextSearchRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;
    private GooglePlaceTextApiService service;

    public TextSearchRepositoryImpl(EventBus eventBus, GooglePlaceTextApiService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getPlaces(String query) {
        Call<PlaceApiResponse> call = service.getPlaces(query, ConstantsHelper.PLACE_API_KEY, CommonHelper.NEXT_PAGE_TOKEN);
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
                t.printStackTrace();
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
