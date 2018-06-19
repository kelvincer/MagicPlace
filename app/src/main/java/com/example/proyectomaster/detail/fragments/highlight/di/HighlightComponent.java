package com.example.proyectomaster.detail.fragments.highlight.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.detail.fragments.highlight.ui.HighlightsFragment;
import com.example.proyectomaster.lib.di.LibsModule;

import dagger.Component;

@ActivityScope
@Component(modules = {HighlightModule.class, LibsModule.class})
public interface HighlightComponent {

    void inject(HighlightsFragment fragment);
}
