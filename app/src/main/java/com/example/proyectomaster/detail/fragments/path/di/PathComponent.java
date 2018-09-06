package com.example.proyectomaster.detail.fragments.path.di;

import com.example.proyectomaster.PerFragment;
import com.example.proyectomaster.detail.fragments.path.PathFragment;

import dagger.Component;

@PerFragment
@Component(modules = {PathModule.class})
public interface PathComponent {

    void inject(PathFragment fragment);
}
