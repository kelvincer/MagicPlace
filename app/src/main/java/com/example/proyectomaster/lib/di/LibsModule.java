package com.example.proyectomaster.lib.di;

import android.app.Activity;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.GlideImageLoader;
import com.example.proyectomaster.lib.GreenRobotEventBus;
import com.example.proyectomaster.lib.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ykro.
 */
@Module
public class LibsModule {

    Activity activity;

    public LibsModule() {
    }

    public LibsModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @ActivityScope
    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity);
        }
        return imageLoader;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    public Retrofit PlaceApiClient(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(ConstantsHelper.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    @ActivityScope
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @ActivityScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
