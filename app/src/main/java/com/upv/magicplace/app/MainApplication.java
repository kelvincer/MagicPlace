package com.upv.magicplace.app;

import android.app.Application;

import com.upv.magicplace.app.di.DaggerMainApplicationComponent;
import com.upv.magicplace.app.di.MainApplicationComponent;
import com.upv.magicplace.app.di.MainApplicationModule;


public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();
    private static MainApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
        component.inject(this);
    }

    protected MainApplicationComponent buildComponent() {
        component = DaggerMainApplicationComponent.builder()
                .mainApplicationModule(new MainApplicationModule(this))
                .build();


        return component;
    }

    public static MainApplicationComponent getAppComponent() {
        return component;
    }
}
