package com.example.proyectomaster.detail.fragments.notes.di;


import com.example.proyectomaster.PerFragment;
import com.example.proyectomaster.detail.fragments.notes.NoteFragmentInteractor;
import com.example.proyectomaster.detail.fragments.notes.NoteFragmentInteractorImpl;
import com.example.proyectomaster.detail.fragments.notes.NoteFragmentPresenter;
import com.example.proyectomaster.detail.fragments.notes.NoteFragmentPresenterImpl;
import com.example.proyectomaster.detail.fragments.notes.NoteFragmentRepository;
import com.example.proyectomaster.detail.fragments.notes.NoteFragmentRepositoryImpl;
import com.example.proyectomaster.detail.fragments.notes.ui.NoteFragmentView;
import com.example.proyectomaster.lib.EventBus;

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
