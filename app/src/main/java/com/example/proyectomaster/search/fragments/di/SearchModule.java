package com.example.proyectomaster.search.fragments.di;

import com.example.proyectomaster.api.PlaceApiClient;
import com.example.proyectomaster.api.PlaceApiService;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.SearchInteractor;
import com.example.proyectomaster.search.fragments.SearchInteractorImpl;
import com.example.proyectomaster.search.fragments.SearchPresenter;
import com.example.proyectomaster.search.fragments.SearchPresenterImpl;
import com.example.proyectomaster.search.fragments.SearchRepository;
import com.example.proyectomaster.search.fragments.SearchRepositoryImpl;
import com.example.proyectomaster.search.fragments.ui.SearchBytTextView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {LibsModule.class})
public class SearchModule {

    SearchBytTextView searchBytTextView;

    public SearchModule(SearchBytTextView searchBytTextView) {
        this.searchBytTextView = searchBytTextView;
    }

    @Singleton
    @Provides
    public SearchBytTextView provideSearchByTextView() {
        return searchBytTextView;
    }

    @Singleton
    @Provides
    public SearchPresenter provideSearchPresenter(EventBus eventBus, SearchBytTextView searchBytTextView, SearchInteractor searchInteractor) {

        return new SearchPresenterImpl(eventBus, searchBytTextView, searchInteractor);
    }

    @Singleton
    @Provides
    public SearchInteractor provideSearchInteractor(SearchRepository searchRepository) {

        return new SearchInteractorImpl(searchRepository);
    }

    @Singleton
    @Provides
    public SearchRepository provideSearchRepository(EventBus eventBus, PlaceApiService service) {
        return new SearchRepositoryImpl(eventBus, service);
    }

    @Singleton
    @Provides
    PlaceApiService providePlaceApiService() {
        PlaceApiClient client = new PlaceApiClient();
        return client.getPlaceApiClient();
    }
}
