package com.upv.magicplace.note.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.note.NoteActivityInteractor;
import com.upv.magicplace.note.NoteActivityInteractorImpl;
import com.upv.magicplace.note.NoteActivityPresenter;
import com.upv.magicplace.note.NoteActivityPresenterImpl;
import com.upv.magicplace.note.NoteActivityRepository;
import com.upv.magicplace.note.NoteActivityRepositoryImpl;
import com.upv.magicplace.note.ui.NoteActivityView;
import com.upv.magicplace.note.NoteActivityRepositoryImpl;
import com.upv.magicplace.note.ui.NoteActivityView;

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
