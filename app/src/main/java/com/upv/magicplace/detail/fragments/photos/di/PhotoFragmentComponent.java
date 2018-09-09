package com.upv.magicplace.detail.fragments.photos.di;

import com.upv.magicplace.PerFragment;
import com.upv.magicplace.detail.fragments.photos.PhotosFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent
public interface PhotoFragmentComponent {

    void inject(PhotosFragment fragment);
}
