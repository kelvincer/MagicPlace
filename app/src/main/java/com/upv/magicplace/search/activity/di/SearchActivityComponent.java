package com.upv.magicplace.search.activity.di;

import com.upv.magicplace.PerActivity;
import com.upv.magicplace.search.activity.ui.SearchActivity;
import com.upv.magicplace.search.activity.ui.SearchActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {SearchActivityModule.class})
public interface SearchActivityComponent {

    void inject(SearchActivity activity);
}
