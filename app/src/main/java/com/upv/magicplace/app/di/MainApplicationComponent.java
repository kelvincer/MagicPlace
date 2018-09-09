package com.upv.magicplace.app.di;

import com.upv.magicplace.app.MainApplication;
import com.upv.magicplace.detail.activity.di.DetailApiModule;
import com.upv.magicplace.detail.activity.di.DetailActivityComponent;
import com.upv.magicplace.detail.activity.di.DetailModule;
import com.upv.magicplace.note.di.NoteActivityComponent;
import com.upv.magicplace.note.di.NoteActivityModule;
import com.upv.magicplace.photo.di.PhotoActivityComponent;
import com.upv.magicplace.photo.di.PhotoActivityModule;
import com.upv.magicplace.photo.ui.PhotoActivity;
import com.upv.magicplace.search.activity.di.SearchActivityComponent;
import com.upv.magicplace.search.activity.di.SearchActivityModule;
import com.upv.magicplace.start.activities.list.di.ListFavouriteComponent;
import com.upv.magicplace.start.activities.list.di.ListFavouriteModule;
import com.upv.magicplace.start.fragments.favourites.di.FavouritesFragComponent;
import com.upv.magicplace.start.fragments.favourites.di.FavouritesFragModule;
import com.upv.magicplace.detail.activity.di.DetailActivityComponent;
import com.upv.magicplace.detail.activity.di.DetailApiModule;
import com.upv.magicplace.detail.activity.di.DetailModule;
import com.upv.magicplace.note.di.NoteActivityComponent;
import com.upv.magicplace.note.di.NoteActivityModule;
import com.upv.magicplace.photo.di.PhotoActivityComponent;
import com.upv.magicplace.photo.di.PhotoActivityModule;
import com.upv.magicplace.search.activity.di.SearchActivityComponent;
import com.upv.magicplace.search.activity.di.SearchActivityModule;
import com.upv.magicplace.start.activities.list.di.ListFavouriteComponent;
import com.upv.magicplace.start.activities.list.di.ListFavouriteModule;
import com.upv.magicplace.start.fragments.favourites.di.FavouritesFragComponent;
import com.upv.magicplace.start.fragments.favourites.di.FavouritesFragModule;

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
