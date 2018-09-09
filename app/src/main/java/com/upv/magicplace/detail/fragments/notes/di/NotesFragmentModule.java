package com.upv.magicplace.detail.fragments.notes.di;


import com.upv.magicplace.PerFragment;
import com.upv.magicplace.detail.fragments.notes.NoteFragmentInteractor;
import com.upv.magicplace.detail.fragments.notes.NoteFragmentInteractorImpl;
import com.upv.magicplace.detail.fragments.notes.NoteFragmentPresenter;
import com.upv.magicplace.detail.fragments.notes.NoteFragmentPresenterImpl;
import com.upv.magicplace.detail.fragments.notes.NoteFragmentRepository;
import com.upv.magicplace.detail.fragments.notes.NoteFragmentRepositoryImpl;
import com.upv.magicplace.detail.fragments.notes.ui.NoteFragmentView;
import com.upv.magicplace.lib.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class NotesFragmentModule {

    NoteFragmentView noteFragmentView;

    public NotesFragmentModule(NoteFragmentView noteFragmentView) {
        this.noteFragmentView = noteFragmentView;
    }

    @PerFragment
    @Provides
    public NoteFragmentView provideNoteFragmentView() {
        return noteFragmentView;
    }

    @PerFragment
    @Provides
    public NoteFragmentPresenter provideNoteFragmentPresenterImpl(EventBus eventBus, NoteFragmentView noteFragmentView, NoteFragmentInteractor noteFragmentInteractor) {
        return new NoteFragmentPresenterImpl(eventBus, noteFragmentView, noteFragmentInteractor);
    }

    @PerFragment
    @Provides
    public NoteFragmentInteractor provideNoteFragmentInteractorImpl(NoteFragmentRepository highlightRepository) {
        return new NoteFragmentInteractorImpl(highlightRepository);
    }

    @PerFragment
    @Provides
    public NoteFragmentRepository provideNoteFragmentRepositoryImpl(EventBus eventBus) {
        return new NoteFragmentRepositoryImpl(eventBus);
    }
}
