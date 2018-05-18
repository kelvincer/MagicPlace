package com.example.proyectomaster.search.fragments.search_by_loc.di;

import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.search_by_loc.ui.LocalizationSearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LocationSearchModule.class})
public interface LocationSearchComponent {

    void inject(LocalizationSearchFragment fragment);
}