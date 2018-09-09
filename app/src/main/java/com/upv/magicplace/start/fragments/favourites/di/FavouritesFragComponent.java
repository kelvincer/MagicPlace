package com.upv.magicplace.start.fragments.favourites.di;

import com.upv.magicplace.PerFragment;
import com.upv.magicplace.start.fragments.favourites.ui.FavouritesFragment;
import com.upv.magicplace.start.fragments.favourites.ui.FavouritesFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = {FavouritesFragModule.class})
public interface FavouritesFragComponent {

    void inject(FavouritesFragment fragment);
}
