package com.upv.magicplace.start.activities.list.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.start.activities.list.ui.ListFavouritesActivity;
import com.upv.magicplace.start.activities.list.ui.ListFavouritesActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {ListFavouriteModule.class})
public interface ListFavouriteComponent {

    void inject(ListFavouritesActivity activity);
}
