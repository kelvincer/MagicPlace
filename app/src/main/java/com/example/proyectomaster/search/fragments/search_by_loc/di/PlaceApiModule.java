package com.example.proyectomaster.search.fragments.search_by_loc.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.search.fragments.search_by_loc.api.GooglePlaceLocationApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PlaceApiModule {

    @ActivityScope
    @Provides
    public GooglePlaceLocationApiService geonameServiceApi(Retrofit retrofit) {
        return retrofit.create(GooglePlaceLocationApiService.class);
    }
}
