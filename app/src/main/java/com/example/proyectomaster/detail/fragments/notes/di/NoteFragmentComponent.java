package com.example.proyectomaster.detail.fragments.notes.di;

import com.example.proyectomaster.PerFragment;
import com.example.proyectomaster.detail.fragments.notes.ui.NotesFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = {NotesFragmentModule.class})
public interface NoteFragmentComponent {

    void inject(NotesFragment fragment);
}
