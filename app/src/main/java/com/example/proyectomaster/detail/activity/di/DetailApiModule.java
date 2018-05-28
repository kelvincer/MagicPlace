package com.example.proyectomaster.detail.activity.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.detail.activity.api.DetailPlaceApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DetailApiModule {

    @ActivityScope
    @Provides
    public DetailPlaceApiService geonameServiceApi(Retrofit retrofit) {
        return retrofit.create(DetailPlaceApiService.class);
    }
}
