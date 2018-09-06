package com.example.proyectomaster.start.activities.list.di;

import com.example.proyectomaster.PerActivity;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.start.activities.list.ListFavouriteInteractor;
import com.example.proyectomaster.start.activities.list.ListFavouriteInteractorImpl;
import com.example.proyectomaster.start.activities.list.ListFavouritePresenter;
import com.example.proyectomaster.start.activities.list.ListFavouritePresenterImpl;
import com.example.proyectomaster.start.activities.list.ListFavouriteRepository;
import com.example.proyectomaster.start.activities.list.ListFavouriteRepositoryImpl;
import com.example.proyectomaster.start.activities.list.ui.ListFavouriteView;

import dagger.Module;
import dagger.Provides;

@Module
public class ListFavouriteModule {

    ListFavouriteView listFavouriteView;

    public ListFavouriteModule(ListFavouriteView listFavouriteView) {
        this.listFavouriteView = listFavouriteView;
    }

    @PerActivity
    @Provides
    public ListFavouriteView provideListFavouriteView() {
        return listFavouriteView;
    }

    @PerActivity
    @Provides
    public ListFavouritePresenter provideListFavouritePresenterImpl(EventBus eventBus, ListFavouriteView listFavouriteView, ListFavouriteInteractor listFavouriteInteractor) {
        return new ListFavouritePresenterImpl(eventBus, listFavouriteView, listFavouriteInteractor);
    }

    @PerActivity
    @Provides
    public ListFavouriteInteractor provideListFavouriteInteractorImpl(ListFavouriteRepository listFavouriteRepository) {
        return new ListFavouriteInteractorImpl(listFavouriteRepository);
    }

    @PerActivity
    @Provides
    public ListFavouriteRepository provideListFavouriteRepositoryImpl(EventBus eventBus) {
        return new ListFavouriteRepositoryImpl(eventBus);
    }
}
