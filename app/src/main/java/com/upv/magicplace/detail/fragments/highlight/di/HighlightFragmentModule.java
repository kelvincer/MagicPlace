package com.upv.magicplace.detail.fragments.highlight.di;

import com.upv.magicplace.detail.fragments.highlight.HighlightInteractor;
import com.upv.magicplace.detail.fragments.highlight.HighlightInteractorImpl;
import com.upv.magicplace.detail.fragments.highlight.HighlightPresenter;
import com.upv.magicplace.detail.fragments.highlight.HighlightPresenterImpl;
import com.upv.magicplace.detail.fragments.highlight.HighlightRepository;
import com.upv.magicplace.detail.fragments.highlight.HighlightRepositoryImpl;
import com.upv.magicplace.detail.fragments.highlight.ui.HighlightView;
import com.upv.magicplace.lib.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class HighlightFragmentModule {

    HighlightView highlightView;

    public HighlightFragmentModule(HighlightView highlightView) {
        this.highlightView = highlightView;
    }

    @Provides
    public HighlightView provideHighlightView() {
        return highlightView;
    }

    @Provides
    public HighlightPresenter HighlightPresenterImpl(EventBus eventBus, HighlightView highlightView, HighlightInteractor highlightInteractor) {
        return new HighlightPresenterImpl(eventBus, highlightView, highlightInteractor);
    }

    @Provides
    public HighlightInteractor HighlightInteractorImpl(HighlightRepository highlightRepository) {
        return new HighlightInteractorImpl(highlightRepository);
    }

    @Provides
    public HighlightRepository HighlightRepositoryImpl(EventBus eventBus) {
        return new HighlightRepositoryImpl(eventBus);
    }
}
