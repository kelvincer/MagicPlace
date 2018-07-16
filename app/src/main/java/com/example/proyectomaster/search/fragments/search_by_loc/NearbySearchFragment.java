package com.example.proyectomaster.search.fragments.search_by_loc;

import android.app.Fragment;
import android.location.Location;
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
import com.example.proyectomaster.search.activity.ui.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NearbySearchFragment extends Fragment {

    @BindView(R.id.search_lat)
    EditText searchLat;
    @BindView(R.id.search_long)
    EditText searchLong;
    @BindView(R.id.filter_btn)
    Button filterBtn;

    Unbinder unbinder;

    public static NearbySearchFragment newInstance() {
        NearbySearchFragment fragment = new NearbySearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonHelper.SEARCH_QUERY_LOCATION = new Location("QUERY_LOCATION");
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
                    CommonHelper.SEARCH_QUERY_LOCATION.setLatitude(Double.valueOf(searchLat.getText().toString()));
                    String query = String.format("%s,%s", searchLat.getText().toString(), searchLong.getText().toString());
                    newSearch(query);
                    return true;
                }
                return false;
            }
        });

        searchLat.setText("-12.046374");
        searchLong.setText("-77.042793");
        CommonHelper.SEARCH_QUERY_LOCATION.setLatitude(Double.valueOf(searchLat.getText().toString()));
        CommonHelper.SEARCH_QUERY_LOCATION.setLongitude(Double.valueOf(searchLong.getText().toString()));

        searchLong.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (searchLong.getText().toString().isEmpty()
                            || searchLat.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Debes ingresar los parámetros requeridos", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    CommonHelper.SEARCH_QUERY_LOCATION.setLongitude(Double.valueOf(searchLong.getText().toString()));
                    String query = String.format("%s,%s", searchLat.getText().toString(), searchLong.getText().toString());
                    newSearch(query);
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
        hideInfoText();
        ((SearchActivity) getActivity()).getResults(query);
    }

    private void hideKeyBoard() {
        ((SearchActivity) getActivity()).hideKeyboard();
    }

    private void clearData() {
        ((SearchActivity) getActivity()).clearData();
    }

    private void showProgressBar() {
        ((SearchActivity) getActivity()).showProgressBar();
    }

    private void hideProgressBar() {
        ((SearchActivity) getActivity()).hideProgressBar();
    }

    private void hideInfoText() {
        ((SearchActivity) getActivity()).hideInfoText();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
