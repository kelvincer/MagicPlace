package com.example.proyectomaster.lib.di;

import android.app.Activity;

import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.GlideImageLoader;
import com.example.proyectomaster.lib.GreenRobotEventBus;
import com.example.proyectomaster.lib.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    @Singleton
    EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity);
        }
        return imageLoader;
    }

    @Provides
    @Singleton
    Activity provideActivity() {
        return this.activity;
    }

}
