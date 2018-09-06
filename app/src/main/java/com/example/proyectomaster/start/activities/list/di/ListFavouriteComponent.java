package com.example.proyectomaster.start.activities.list.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.start.activities.list.ui.ListFavouritesActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {ListFavouriteModule.class})
public interface ListFavouriteComponent {

    void inject(ListFavouritesActivity activity);
}
