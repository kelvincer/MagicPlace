package com.example.proyectomaster.api;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceApiClient {

    private Retrofit retrofit;
    private final static String BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/";

    public PlaceApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }

    public PlaceApiService getPlaceApiClient() {
        return retrofit.create(PlaceApiService.class);
    }

}
