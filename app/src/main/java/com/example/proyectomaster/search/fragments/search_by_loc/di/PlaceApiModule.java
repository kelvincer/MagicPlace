package com.example.proyectomaster.search.fragments.search_by_loc.di;

import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.search.fragments.search_by_loc.api.GooglePlaceLocationApiService;
import com.example.proyectomaster.search.fragments.search_by_text.api.GooglePlaceTextApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class PlaceApiModule {

    @Singleton
    @Provides
    public GooglePlaceLocationApiService geonameServiceApi(Retrofit retrofit) {
        return retrofit.create(GooglePlaceLocationApiService.class);
    }

    @Singleton
    @Provides
    public Retrofit PlaceApiClient(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(ConstantsHelper.BASE_URL)
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
