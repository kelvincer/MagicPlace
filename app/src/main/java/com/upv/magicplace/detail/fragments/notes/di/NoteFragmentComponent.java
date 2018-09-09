package com.upv.magicplace.detail.fragments.notes.di;

import com.upv.magicplace.PerFragment;
import com.upv.magicplace.detail.fragments.notes.ui.NotesFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = {NotesFragmentModule.class})
public interface NoteFragmentComponent {

    void inject(NotesFragment fragment);
}
