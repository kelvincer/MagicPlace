package com.example.proyectomaster.search.activity;

import android.util.Log;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.response.PlaceApiResponse;
import com.example.proyectomaster.search.activity.api.PlacesApiService;
import com.example.proyectomaster.search.activity.events.SearchEvent;
import com.example.proyectomaster.search.entities.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivityRepositoryImpl implements SearchActivityRepository {

    private static final String TAG = SearchActivityRepositoryImpl.class.getSimpleName();
    EventBus eventBus;
    PlacesApiService service;

    public SearchActivityRepositoryImpl(EventBus eventBus, PlacesApiService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getPlaces(String query) {

        if (CommonHelper.SEARCH_MODE == 1) {

            Call<PlaceApiResponse> call = service.getTextResults(query,
                    ConstantsHelper.GOOGLE_PLACE_API_KEY,
                    CommonHelper.location,
                    CommonHelper.radius,
                    CommonHelper.minprice,
                    CommonHelper.opennow,
                    CommonHelper.NEXT_PAGE_TOKEN);
            call.enqueue(new Callback<PlaceApiResponse>() {
                @Override
                public void onResponse(Call<PlaceApiResponse> call, Response<PlaceApiResponse> response) {
                    if (response.isSuccessful()) {
                        PlaceApiResponse placeApiResponse = response.body();
                        CommonHelper.NEXT_PAGE_TOKEN = placeApiResponse.getNextPageToken();
                        if (placeApiResponse.getStatus().equals("OK")) {
                            post(SearchEvent.GET_EVENT, placeApiResponse.getResults());
                            Log.d(TAG, "PLACE 0 " + placeApiResponse.getResults().get(0).getPlaceId());
                        } else {
                            post(SearchEvent.ERROR, placeApiResponse.getStatus());
                        }
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

        } else if (CommonHelper.SEARCH_MODE == 2) {

            Call<PlaceApiResponse> call = service.getNearbyResults(ConstantsHelper.GOOGLE_PLACE_API_KEY,
                    query,
                    CommonHelper.radius,
                    CommonHelper.NEXT_PAGE_TOKEN,
                    CommonHelper.KEYWORD,
                    CommonHelper.minprice,
                    CommonHelper.opennow,
                    CommonHelper.RANKYBY);
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

        } else {
            throw new RuntimeException("Invalid SEARCH_MODE");
        }
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
