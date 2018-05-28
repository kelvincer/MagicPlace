package com.example.proyectomaster.lib.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.detail.fragments.PhotosFragment;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.photo.PhotoActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LibsModule.class)
public interface LibsComponent {

    void inject(PhotosFragment fragment);
    void inject(PhotoActivity activity);
}
