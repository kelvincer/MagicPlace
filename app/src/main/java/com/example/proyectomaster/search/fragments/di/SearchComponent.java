package com.example.proyectomaster.search.fragments.di;

import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.ui.SearchByTextFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SearchModule.class, LibsModule.class})
public interface SearchComponent {

    void inject(SearchByTextFragment fragment);
    //SearchPresenter getSearchPresenter();
}
