package com.example.proyectomaster.search.fragments.search_by_text.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.proyectomaster.R;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.model_place_api.Result;
import com.example.proyectomaster.search.activity.ui.SearchActivity;
import com.example.proyectomaster.search.fragments.search_by_text.SearchPresenter;
import com.example.proyectomaster.search.fragments.search_by_text.di.DaggerSearchComponent;
import com.example.proyectomaster.search.fragments.search_by_text.di.SearchComponent;
import com.example.proyectomaster.search.fragments.search_by_text.di.SearchModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchByTextFragment extends Fragment implements SearchBytTextView {

    @BindView(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;
    @BindView(R.id.filter_button)
    Button filterButton;
    @BindView(R.id.parent_view)
    RelativeLayout parentView;
    @Inject
    SearchPresenter searchPresenter;
    Unbinder unbinder;

    public static Fragment newInstance() {
        SearchByTextFragment fragment = new SearchByTextFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        searchPresenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_text, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFloatingSearchView();
    }

    private void setupFloatingSearchView() {

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                Toast.makeText(getActivity(), "SEARCH", Toast.LENGTH_SHORT).show();
                showProgressBar();
                searchPresenter.getPlaces(currentQuery);
            }
        });
    }

    private void showProgressBar() {
        ((SearchActivity) getActivity()).showProgressBar();
    }

    private void setupInjection() {

        SearchComponent component = DaggerSearchComponent.builder()
                .searchModule(new SearchModule(this))
                .libsModule(new LibsModule(getActivity()))
                .build();
        component.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchPresenter.onDestroy();
    }

    @Override
    public void showMessage() {
        Toast.makeText(getActivity(), "COMPLETED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(List<Result> results) {
        hideProgressBar();
        ((SearchActivity) getActivity()).setPlaces(results);
    }

    private void hideProgressBar() {
        ((SearchActivity) getActivity()).hideProgressBar();
    }

    @OnClick(R.id.filter_button)
    public void onViewClicked() {
        ((SearchActivity) getActivity()).openDrawer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
