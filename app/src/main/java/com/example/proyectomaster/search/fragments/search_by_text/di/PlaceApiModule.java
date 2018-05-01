package com.example.proyectomaster.search.fragments.search_by_text.di;

import com.example.proyectomaster.search.fragments.search_by_text.api.PlaceApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class PlaceApiModule {

    private final static String BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/";

    @Singleton
    @Provides
    public PlaceApiService geonameServiceApi(Retrofit retrofit) {
        return retrofit.create(PlaceApiService.class);
    }

    @Singleton
    @Provides
    public Retrofit PlaceApiClient(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Singleton
    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
