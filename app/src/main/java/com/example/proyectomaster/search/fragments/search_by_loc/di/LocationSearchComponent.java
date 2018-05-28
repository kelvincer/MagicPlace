package com.example.proyectomaster.search.fragments.search_by_loc.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.search_by_loc.ui.LocalizationSearchFragment;

import dagger.Component;

@ActivityScope
@Component(modules = {LocationSearchModule.class, LibsModule.class, PlaceApiModule.class})
public interface LocationSearchComponent {

    void inject(LocalizationSearchFragment fragment);
}