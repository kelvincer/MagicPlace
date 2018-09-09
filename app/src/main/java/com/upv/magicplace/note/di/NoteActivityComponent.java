package com.upv.magicplace.note.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.note.ui.NoteActivity;
import com.upv.magicplace.note.ui.NoteActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {NoteActivityModule.class})
public interface NoteActivityComponent {

    void inject(NoteActivity activity);
}
