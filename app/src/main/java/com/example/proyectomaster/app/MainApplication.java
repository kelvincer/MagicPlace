package com.example.proyectomaster.app;

import android.app.Application;

import com.example.proyectomaster.app.di.DaggerMainApplicationComponent;
import com.example.proyectomaster.app.di.MainApplicationComponent;
import com.example.proyectomaster.app.di.MainApplicationModule;

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
