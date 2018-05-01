package com.example.proyectomaster.search.fragments.search_by_text.di;

import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.search_by_text.ui.SearchByTextFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SearchModule.class)
public interface SearchComponent {

    void inject(SearchByTextFragment fragment);
    //SearchPresenter getSearchPresenter();
}
