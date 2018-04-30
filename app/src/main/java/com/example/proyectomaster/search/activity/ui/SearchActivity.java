package com.example.proyectomaster.search.activity.ui;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.example.proyectomaster.R;
import com.example.proyectomaster.SideFilterFragment;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.model_place_api.Result;
import com.example.proyectomaster.search.activity.SearchActivityPresenter;
import com.example.proyectomaster.search.activity.adapters.PlacesApiAdapter;
import com.example.proyectomaster.search.activity.di.DaggerSearchActivityComponent;
import com.example.proyectomaster.search.activity.di.SearchActivityModule;
import com.example.proyectomaster.search.fragments.ui.SearchByTextFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SideFilterFragment.OnFragmentInteractionListener
        , SearchActivityView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.places_recycler)
    RecyclerView placesRecyclers;

    @Inject
    SearchActivityPresenter presenter;
    @Inject
    PlacesApiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        /*floatingSearchView.findViewById(R.id.left_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floatingSearchView.isSearchBarFocused()) {
                    floatingSearchView.clearSearchFocus();
                    finish();
                } else {
                    floatingSearchView.findViewById(R.id.search_bar_text).requestFocus();
                    Toast.makeText(SearchActivity.this, "CLICK", Toast.LENGTH_SHORT).show();
                }
            }
        });

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Toast.makeText(SearchActivity.this, "Suggestions", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchAction(String currentQuery) {
                Toast.makeText(SearchActivity.this, "Search action", Toast.LENGTH_SHORT).show();
            }
        });*/

        setupInjection();
        setFragments();
        setupRecyclerView();

        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupInjection() {

        DaggerSearchActivityComponent.builder()
                .libsModule(new LibsModule(this))
                .searchActivityModule(new SearchActivityModule(this))
                .build()
                .inject(this);
    }

    private void setupRecyclerView() {

        placesRecyclers.setLayoutManager(new LinearLayoutManager(this));
        placesRecyclers.setAdapter(adapter);
    }

    private void setFragments() {

        Fragment f = SideFilterFragment.newInstance(null, null);
        getFragmentManager().beginTransaction().add(R.id.side_filter_container, f).commit();

        Fragment fragment = SearchByTextFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.search_head_container, fragment).commit();
    }

    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.END);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setPlaces(List<Result> data) {
        adapter.updateData(data);
    }

    @Override
    public void showProgressBar() {

    }
}
