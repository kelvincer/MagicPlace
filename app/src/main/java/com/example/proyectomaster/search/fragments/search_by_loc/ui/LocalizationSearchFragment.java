package com.example.proyectomaster.search.fragments.search_by_loc.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.search.entities.Result;
import com.example.proyectomaster.search.activity.ui.SearchActivity;
import com.example.proyectomaster.search.fragments.search_by_loc.LocationSearchPresenter;
import com.example.proyectomaster.search.fragments.search_by_loc.di.DaggerLocationSearchComponent;
import com.example.proyectomaster.search.fragments.search_by_loc.di.LocationSearchModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LocalizationSearchFragment extends Fragment implements LocationSearchView {

    @BindView(R.id.search_lat)
    EditText searchLat;
    @BindView(R.id.search_long)
    EditText searchLong;
    @BindView(R.id.filter_btn)
    Button filterBtn;

    @Inject
    LocationSearchPresenter presenter;

    Unbinder unbinder;

    public static LocalizationSearchFragment newInstance() {
        LocalizationSearchFragment fragment = new LocalizationSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_lat_lng, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.filter_btn)
    public void onViewClicked() {
        ((SearchActivity) getActivity()).hideKeyboard();
        ((SearchActivity) getActivity()).openDrawer();
    }

    private void setupViews() {

        searchLat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (searchLong.getText().toString().isEmpty()
                            || searchLat.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Debes ingresar los parámetros requeridos", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    performSearch();
                    ((SearchActivity) getActivity()).hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        searchLong.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (searchLong.getText().toString().isEmpty()
                            || searchLat.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Debes ingresar los parámetros requeridos", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    CommonHelper.QUERY = String.format("%s,%s", searchLat.getText().toString(), searchLong.getText().toString());
                    newSearch(CommonHelper.QUERY);
                    ((SearchActivity) getActivity()).hideKeyboard();
                    return true;
                }
                return false;
            }
        });
    }

    private void newSearch(String query) {

        CommonHelper.NEXT_PAGE_TOKEN = null;
        clearData();
        showProgressBar();
        CommonHelper.QUERY = query;
        hideKeyBoard();
        findPlaces(query);
    }

    private void hideKeyBoard() {
        ((SearchActivity) getActivity()).hideKeyboard();
    }

    private void clearData() {
        ((SearchActivity) getActivity()).clearData();
    }

    private void performSearch() {

        presenter.getPlaces(CommonHelper.QUERY);
    }

    private void showProgressBar() {
        ((SearchActivity) getActivity()).showProgressBar();
    }

    private void hideProgressBar() {
        ((SearchActivity) getActivity()).hideProgressBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMessage() {

    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(List<Result> results) {
        hideProgressBar();
        ((SearchActivity) getActivity()).setPlaces(results);
    }

    private void setupInjection() {

        DaggerLocationSearchComponent.builder()
                .libsModule(new LibsModule())
                .locationSearchModule(new LocationSearchModule(this))
                .build()
                .inject(this);
    }

    public void findPlaces(String query) {
        presenter.getPlaces(query);
    }
}
