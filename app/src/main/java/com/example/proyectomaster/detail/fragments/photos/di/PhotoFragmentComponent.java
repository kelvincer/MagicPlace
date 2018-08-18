package com.example.proyectomaster.detail.fragments.photos.di;

import com.example.proyectomaster.PerFragment;
import com.example.proyectomaster.detail.fragments.photos.PhotosFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent
public interface PhotoFragmentComponent {

    void inject(PhotosFragment fragment);
}
