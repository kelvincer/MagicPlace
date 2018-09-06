package com.example.proyectomaster.start.fragments.favourites.di;

import com.example.proyectomaster.PerFragment;
import com.example.proyectomaster.start.fragments.favourites.ui.FavouritesFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = {FavouritesFragModule.class})
public interface FavouritesFragComponent {

    void inject(FavouritesFragment fragment);
}
