package com.example.proyectomaster.search.activity.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.search.activity.ui.SearchActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {SearchActivityModule.class})
public interface SearchActivityComponent {

    void inject(SearchActivity activity);
}
