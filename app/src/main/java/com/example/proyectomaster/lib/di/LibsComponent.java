package com.example.proyectomaster.lib.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.detail.fragments.photos.PhotosFragment;
import com.example.proyectomaster.photo.PhotoActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LibsModule.class)
public interface LibsComponent {

    void inject(PhotosFragment fragment);

    void inject(PhotoActivity activity);

}
