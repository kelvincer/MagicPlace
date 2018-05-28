package com.example.proyectomaster.search.fragments.search_by_text.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.search_by_text.ui.SearchByTextFragment;

import dagger.Component;

@ActivityScope
@Component(modules = {TextSearchModule.class, LibsModule.class, PlaceApiModule.class})
public interface TextSearchComponent {

    void inject(SearchByTextFragment fragment);
    //TextSearchPresenter getSearchPresenter();
}
