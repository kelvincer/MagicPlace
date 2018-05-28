package com.example.proyectomaster.search.fragments.search_by_text.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.search.fragments.search_by_text.api.GooglePlaceTextApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PlaceApiModule {

    @ActivityScope
    @Provides
    public GooglePlaceTextApiService geonameServiceApi(Retrofit retrofit) {
        return retrofit.create(GooglePlaceTextApiService.class);
    }
}
