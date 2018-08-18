package com.example.proyectomaster.photo.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.photo.PhotoActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent
public interface PhotoActivityComponent {

    void inject(PhotoActivity activity);
}
