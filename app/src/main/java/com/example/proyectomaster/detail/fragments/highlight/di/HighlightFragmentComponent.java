package com.example.proyectomaster.detail.fragments.highlight.di;

import com.example.proyectomaster.detail.fragments.highlight.ui.HighlightsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {HighlightFragmentModule.class})
public interface HighlightFragmentComponent {

    void inject(HighlightsFragment fragment);
}
