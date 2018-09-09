package com.upv.magicplace.photo.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.photo.ui.PhotoActivity;
import com.upv.magicplace.photo.ui.PhotoActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PhotoActivityModule.class})
public interface PhotoActivityComponent {

    void inject(PhotoActivity activity);
}
