package com.example.proyectomaster.detail.activity.di;


import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.detail.activity.ui.DetailActivity;
import com.example.proyectomaster.lib.di.LibsModule;

import dagger.Component;

@ActivityScope
@Component(modules = {DetailModule.class, LibsModule.class})
public interface DetailComponent {

    void inject(DetailActivity activity);
}
