package com.example.proyectomaster.note.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.note.NoteActivityInteractor;
import com.example.proyectomaster.note.NoteActivityInteractorImpl;
import com.example.proyectomaster.note.NoteActivityPresenter;
import com.example.proyectomaster.note.NoteActivityPresenterImpl;
import com.example.proyectomaster.note.NoteActivityRepository;
import com.example.proyectomaster.note.NoteActivityRepositoryImpl;
import com.example.proyectomaster.note.ui.NoteActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteActivityModule {

    NoteActivityView noteActivityView;

    public NoteActivityModule(NoteActivityView noteActivityView) {
        this.noteActivityView = noteActivityView;
    }

    @PerActivity
    @Provides
    public NoteActivityView provideNoteActivityView() {
        return noteActivityView;
    }

    @PerActivity
    @Provides
    public NoteActivityPresenter HighlightPresenterImpl(EventBus eventBus, NoteActivityView noteActivityView, NoteActivityInteractor noteActivityInteractor) {
        return new NoteActivityPresenterImpl(eventBus, noteActivityView, noteActivityInteractor);
    }

    @PerActivity
    @Provides
    public NoteActivityInteractor HighlightInteractorImpl(NoteActivityRepository noteActivityRepository) {
        return new NoteActivityInteractorImpl(noteActivityRepository);
    }

    @PerActivity
    @Provides
    public NoteActivityRepository HighlightRepositoryImpl(EventBus eventBus) {
        return new NoteActivityRepositoryImpl(eventBus);
    }
}
