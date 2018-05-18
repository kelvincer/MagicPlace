package com.example.proyectomaster.search.fragments.search_by_text.di;

import com.example.proyectomaster.search.fragments.search_by_text.ui.SearchByTextFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TextSearchModule.class)
public interface TextSearchComponent {

    void inject(SearchByTextFragment fragment);
    //TextSearchPresenter getSearchPresenter();
}
