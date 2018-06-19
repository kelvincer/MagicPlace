package com.example.proyectomaster.detail.fragments.highlight.di;

import com.example.proyectomaster.ActivityScope;
import com.example.proyectomaster.detail.fragments.highlight.HighlightInteractor;
import com.example.proyectomaster.detail.fragments.highlight.HighlightInteractorImpl;
import com.example.proyectomaster.detail.fragments.highlight.HighlightPresenter;
import com.example.proyectomaster.detail.fragments.highlight.HighlightPresenterImpl;
import com.example.proyectomaster.detail.fragments.highlight.HighlightRepository;
import com.example.proyectomaster.detail.fragments.highlight.HighlightRepositoryImpl;
import com.example.proyectomaster.detail.fragments.highlight.ui.HighlightView;
import com.example.proyectomaster.lib.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class HighlightModule {

    HighlightView highlightView;

    public HighlightModule(HighlightView highlightView) {
        this.highlightView = highlightView;
    }

    @Provides
    @ActivityScope
    public HighlightView provideHighlightView() {
        return highlightView;
    }

    @Provides
    @ActivityScope
    public HighlightPresenter HighlightPresenterImpl(EventBus eventBus, HighlightView highlightView, HighlightInteractor highlightInteractor) {
        return new HighlightPresenterImpl(eventBus, highlightView, highlightInteractor);
    }

    @Provides
    @ActivityScope
    public HighlightInteractor HighlightInteractorImpl(HighlightRepository highlightRepository) {
        return new HighlightInteractorImpl(highlightRepository);
    }

    @Provides
    @ActivityScope
    public HighlightRepository HighlightRepositoryImpl(EventBus eventBus) {
        return new HighlightRepositoryImpl(eventBus);
    }
}
