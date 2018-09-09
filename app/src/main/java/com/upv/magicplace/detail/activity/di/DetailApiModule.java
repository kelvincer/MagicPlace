package com.upv.magicplace.detail.activity.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.detail.activity.api.DetailPlaceApiService;
import com.upv.magicplace.detail.activity.api.DetailPlaceApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DetailApiModule {

    @PerActivity
    @Provides
    public DetailPlaceApiService geonameServiceApi(Retrofit retrofit) {
        return retrofit.create(DetailPlaceApiService.class);
    }
}
