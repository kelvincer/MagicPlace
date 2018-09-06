package com.example.proyectomaster.app.di;

import com.example.proyectomaster.app.MainApplication;
import com.example.proyectomaster.detail.activity.di.DetailApiModule;
import com.example.proyectomaster.detail.activity.di.DetailActivityComponent;
import com.example.proyectomaster.detail.activity.di.DetailModule;
import com.example.proyectomaster.note.di.NoteActivityComponent;
import com.example.proyectomaster.note.di.NoteActivityModule;
import com.example.proyectomaster.photo.di.PhotoActivityComponent;
import com.example.proyectomaster.photo.di.PhotoActivityModule;
import com.example.proyectomaster.photo.ui.PhotoActivity;
import com.example.proyectomaster.search.activity.di.SearchActivityComponent;
import com.example.proyectomaster.search.activity.di.SearchActivityModule;
import com.example.proyectomaster.start.activities.list.di.ListFavouriteComponent;
import com.example.proyectomaster.start.activities.list.di.ListFavouriteModule;
import com.example.proyectomaster.start.fragments.favourites.di.FavouritesFragComponent;
import com.example.proyectomaster.start.fragments.favourites.di.FavouritesFragModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainApplicationModule.class})
public interface MainApplicationComponent {

    void inject(MainApplication app);

    SearchActivityComponent newSearchActivityComponent(SearchActivityModule module);

    PhotoActivityComponent newPhotoComponent(PhotoActivityModule module);

    DetailActivityComponent newDetailComponent(DetailApiModule module, DetailModule detailModule);

    NoteActivityComponent newNoteActivityComponent(NoteActivityModule module);

    FavouritesFragComponent newFavouritesFragComponent(FavouritesFragModule module);

    ListFavouriteComponent newListFavouriteComponent(ListFavouriteModule module);
}
