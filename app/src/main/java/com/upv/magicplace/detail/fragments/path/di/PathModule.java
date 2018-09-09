package com.upv.magicplace.detail.fragments.path.di;

import com.upv.magicplace.PerFragment;
import com.upv.magicplace.detail.fragments.path.api.DirectionsService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class PathModule {

    private final static String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";

    @PerFragment
    @Provides
    public Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @PerFragment
    @Provides
    public DirectionsService provideDirectionService(Retrofit retrofit) {
        return retrofit.create(DirectionsService.class);
    }
}
