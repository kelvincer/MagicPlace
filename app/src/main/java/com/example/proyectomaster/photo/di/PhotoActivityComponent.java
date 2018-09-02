package com.example.proyectomaster.photo.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.photo.ui.PhotoActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PhotoActivityModule.class})
public interface PhotoActivityComponent {

    void inject(PhotoActivity activity);
}
