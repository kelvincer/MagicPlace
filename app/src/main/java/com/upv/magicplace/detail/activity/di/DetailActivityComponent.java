package com.upv.magicplace.detail.activity.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.detail.activity.ui.DetailActivity;
import com.upv.magicplace.detail.fragments.highlight.di.HighlightFragmentComponent;
import com.upv.magicplace.detail.fragments.highlight.di.HighlightFragmentModule;
import com.upv.magicplace.detail.fragments.notes.di.NoteFragmentComponent;
import com.upv.magicplace.detail.fragments.notes.di.NotesFragmentModule;
import com.upv.magicplace.detail.fragments.photos.di.PhotoFragmentComponent;
import com.upv.magicplace.detail.activity.ui.DetailActivity;
import com.upv.magicplace.detail.fragments.highlight.di.HighlightFragmentComponent;
import com.upv.magicplace.detail.fragments.highlight.di.HighlightFragmentModule;
import com.upv.magicplace.detail.fragments.notes.di.NoteFragmentComponent;
import com.upv.magicplace.detail.fragments.notes.di.NotesFragmentModule;
import com.upv.magicplace.detail.fragments.photos.di.PhotoFragmentComponent;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {DetailModule.class, DetailApiModule.class})
public interface DetailActivityComponent {

    void inject(DetailActivity activity);

    HighlightFragmentComponent newHighlightComponent(HighlightFragmentModule module);

    NoteFragmentComponent newNoteFragmentComponent(NotesFragmentModule module);

    PhotoFragmentComponent newPhotoFragmentComponent();
}
