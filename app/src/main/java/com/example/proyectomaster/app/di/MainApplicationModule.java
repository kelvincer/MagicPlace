package com.example.proyectomaster.app.di;

import android.content.Context;

import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.GlideImageLoader;
import com.example.proyectomaster.lib.GreenRobotEventBus;
import com.example.proyectomaster.lib.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MainApplicationModule {

    Context appContext;

    public MainApplicationModule() {
    }

    public MainApplicationModule(Context appContext) {
        this.appContext = appContext;
    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }

    @Singleton
    @Provides
    ImageLoader provideImageLoader(Context appContext) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (appContext != null) {
            imageLoader.setLoaderContext(appContext);
        }
        return imageLoader;
    }

    @Singleton
    @Provides
    Context provideAppContext() {
        return this.appContext;
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
