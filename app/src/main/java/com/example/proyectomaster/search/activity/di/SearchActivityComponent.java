package com.example.proyectomaster.search.activity.di;

import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.activity.ui.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SearchActivityModule.class, LibsModule.class})
public interface SearchActivityComponent {

    void inject(SearchActivity activity);
}
