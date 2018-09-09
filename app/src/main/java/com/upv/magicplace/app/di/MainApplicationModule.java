package com.upv.magicplace.app.di;

import android.content.Context;

import com.upv.magicplace.ConstantsHelper;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.lib.GlideImageLoader;
import com.upv.magicplace.lib.GreenRobotEventBus;
import com.upv.magicplace.lib.ImageLoader;
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
