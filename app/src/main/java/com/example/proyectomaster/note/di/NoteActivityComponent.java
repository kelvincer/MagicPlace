package com.example.proyectomaster.note.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.note.ui.NoteActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {NoteActivityModule.class})
public interface NoteActivityComponent {

    void inject(NoteActivity activity);
}
