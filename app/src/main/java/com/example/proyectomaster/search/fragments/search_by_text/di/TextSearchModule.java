package com.example.proyectomaster.search.fragments.search_by_text.di;

import com.example.proyectomaster.search.fragments.search_by_text.SearchGoogleInteractor;
import com.example.proyectomaster.search.fragments.search_by_text.TextSearchPresenterImpl;
import com.example.proyectomaster.search.fragments.search_by_text.TextSearchRepository;
import com.example.proyectomaster.search.fragments.search_by_text.TextSearchRepositoryImpl;
import com.example.proyectomaster.search.fragments.search_by_text.api.GooglePlaceTextApiService;
import com.example.proyectomaster.lib.EventBus;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.fragments.search_by_text.SearchGoogleInteractorImpl;
import com.example.proyectomaster.search.fragments.search_by_text.TextSearchPresenter;
import com.example.proyectomaster.search.fragments.search_by_text.ui.SearchBytTextView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {LibsModule.class, PlaceApiModule.class})
public class TextSearchModule {

    SearchBytTextView searchBytTextView;

    public TextSearchModule(SearchBytTextView searchBytTextView) {
        this.searchBytTextView = searchBytTextView;
    }

    @Singleton
    @Provides
    public SearchBytTextView provideSearchByTextView() {
        return searchBytTextView;
    }

    @Singleton
    @Provides
    public TextSearchPresenter provideSearchPresenter(EventBus eventBus, SearchBytTextView searchBytTextView, SearchGoogleInteractor searchGoogleInteractor) {

        return new TextSearchPresenterImpl(eventBus, searchBytTextView, searchGoogleInteractor);
    }

    @Singleton
    @Provides
    public SearchGoogleInteractor provideSearchInteractor(TextSearchRepository textSearchRepository) {

        return new SearchGoogleInteractorImpl(textSearchRepository);
    }

    @Singleton
    @Provides
    public TextSearchRepository provideSearchRepository(EventBus eventBus, GooglePlaceTextApiService service) {
        return new TextSearchRepositoryImpl(eventBus, service);
    }
}
