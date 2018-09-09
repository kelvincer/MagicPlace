package com.upv.magicplace.detail.fragments.highlight.di;

import com.upv.magicplace.detail.fragments.highlight.ui.HighlightsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {HighlightFragmentModule.class})
public interface HighlightFragmentComponent {

    void inject(HighlightsFragment fragment);
}
