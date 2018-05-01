package com.example.proyectomaster.search.fragments.search_by_text.di;

import com.example.proyectomaster.search.fragments.search_by_text.api.PlaceApiService;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.search_by_text.SearchInteractor;
import com.example.proyectomaster.search.fragments.search_by_text.SearchInteractorImpl;
import com.example.proyectomaster.search.fragments.search_by_text.SearchPresenter;
import com.example.proyectomaster.search.fragments.search_by_text.SearchPresenterImpl;
import com.example.proyectomaster.search.fragments.search_by_text.SearchRepository;
import com.example.proyectomaster.search.fragments.search_by_text.SearchRepositoryImpl;
import com.example.proyectomaster.search.fragments.search_by_text.ui.SearchBytTextView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {LibsModule.class, PlaceApiModule.class})
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
}
