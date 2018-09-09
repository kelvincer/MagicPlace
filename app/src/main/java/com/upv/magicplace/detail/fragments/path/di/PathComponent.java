package com.upv.magicplace.detail.fragments.path.di;

import com.upv.magicplace.PerFragment;
import com.upv.magicplace.detail.fragments.path.PathFragment;

import dagger.Component;

@PerFragment
@Component(modules = {PathModule.class})
public interface PathComponent {

    void inject(PathFragment fragment);
}
