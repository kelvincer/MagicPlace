package com.example.proyectomaster.detail.activity.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.detail.activity.ui.DetailActivity;
import com.example.proyectomaster.detail.fragments.highlight.di.HighlightFragmentComponent;
import com.example.proyectomaster.detail.fragments.highlight.di.HighlightFragmentModule;
import com.example.proyectomaster.detail.fragments.notes.di.NoteFragmentComponent;
import com.example.proyectomaster.detail.fragments.notes.di.NotesFragmentModule;
import com.example.proyectomaster.detail.fragments.photos.di.PhotoFragmentComponent;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {DetailModule.class, DetailApiModule.class})
public interface DetailActivityComponent {

    void inject(DetailActivity activity);

    HighlightFragmentComponent newHighlightComponent(HighlightFragmentModule module);

    NoteFragmentComponent newNoteFragmentComponent(NotesFragmentModule module);

    PhotoFragmentComponent newPhotoFragmentComponent();
}
