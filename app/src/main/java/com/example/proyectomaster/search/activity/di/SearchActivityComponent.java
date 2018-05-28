package com.example.proyectomaster.search.activity.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.activity.ui.SearchActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {SearchActivityModule.class, LibsModule.class})
public interface SearchActivityComponent {

    void inject(SearchActivity activity);
}
