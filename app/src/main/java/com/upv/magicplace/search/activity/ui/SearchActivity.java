package com.upv.magicplace.search.activity.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.ConstantsHelper;
import com.upv.magicplace.Helper;
import com.upv.magicplace.R;
import com.upv.magicplace.app.MainApplication;
import com.upv.magicplace.detail.activity.ui.DetailActivity;
import com.upv.magicplace.location.ApiLocationManager;
import com.upv.magicplace.location.LocationCallback;
import com.upv.magicplace.search.activity.SearchActivityPresenter;
import com.upv.magicplace.search.activity.adapters.OnItemClickListener;
import com.upv.magicplace.search.activity.adapters.RecyclerViewResultAdapter;
import com.upv.magicplace.search.activity.di.SearchActivityModule;
import com.upv.magicplace.search.entities.Result;
import com.upv.magicplace.search.fragments.SideFilterFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements
        SearchActivityView, LocationCallback, OnItemClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.places_recycler)
    RecyclerView placesRecycler;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.txv_info)
    TextView txvInfo;
    @BindView(R.id.search_etx)
    EditText searchTextView;
    @BindView(R.id.action_empty_btn)
    ImageButton actionEmptyBtn;
    @BindView(R.id.action_search_btn)
    ImageButton actionSearchBtn;
    @BindView(R.id.action_up_btn)
    ImageButton actionUpBtn;
    @BindView(R.id.filter_btn)
    Button filterBtn;
    private List<String> listaPermisos = new ArrayList<>();
    private int SOLICITUD_PERMISO = 100;
    ApiLocationManager locationManager;

    @Inject
    SearchActivityPresenter presenter;
    @Inject
    RecyclerViewResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initVariables();
        setupInjection();
        addFragments();
        setupViews();
        presenter.onCreate();
        setupPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SOLICITUD_PERMISO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpLocationManager();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationManager != null)
            locationManager.onInit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null)
            locationManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (withPermissions()) {
                setUpLocationManager();
            } else {
                requestPermissions(SOLICITUD_PERMISO, this);
            }
        } else {
            setUpLocationManager();
        }
    }

    public boolean withPermissions() {

        listaPermisos.clear();

        int accessFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listaPermisos.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listaPermisos.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listaPermisos.add(Manifest.permission.CAMERA);
        }

        return listaPermisos.isEmpty();
    }

    private void setUpLocationManager() {

        locationManager = new ApiLocationManager(this);
        locationManager.setLocationCallback(this);
        locationManager.onInit();
    }

    public void requestPermissions(int requestCode, Activity activity) {
        ActivityCompat.requestPermissions(activity, listaPermisos.toArray((new String[listaPermisos.size()])), requestCode);
    }

    private void setupInjection() {

        MainApplication.getAppComponent()
                .newSearchActivityComponent(new SearchActivityModule(this, this))
                .inject(this);
    }

    private void setupViews() {

        placesRecycler.setLayoutManager(new LinearLayoutManager(this));
        placesRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        placesRecycler.setAdapter(adapter);
        placesRecycler.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {

                if (CommonHelper.NEXT_PAGE_TOKEN != null) {
                    getNextTokenResult();
                }
            }
        });

        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    actionEmptyBtn.setVisibility(View.INVISIBLE);
                    actionSearchBtn.setVisibility(View.VISIBLE);
                    CommonHelper.QUERY = null;
                } else {
                    actionEmptyBtn.setVisibility(View.VISIBLE);
                    actionSearchBtn.setVisibility(View.INVISIBLE);
                    CommonHelper.QUERY = s.toString();
                    if (CommonHelper.SEARCH_MODE == 2) {
                        if (isValidLocationInput(CommonHelper.QUERY)) {
                            saveQueryLocation();
                        }
                    }
                }
            }
        });
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (CommonHelper.SEARCH_MODE == 1) {
                        if (searchTextView.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Debes ingresar los parámetros requeridos", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        newSearch(searchTextView.getText().toString());
                        hideKeyboard();
                        return true;
                    } else if (CommonHelper.SEARCH_MODE == 2) {

                        if (!isValidLocationInput(searchTextView.getText().toString())) {
                            Toast.makeText(SearchActivity.this, "Entrada no válida", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        saveQueryLocation();
                        newSearch(searchTextView.getText().toString());
                        hideKeyboard();
                        return true;
                    }
                }
                return false;
            }
        });

        actionEmptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTextView.getText().clear();
            }
        });
        actionUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchTextView.setSelection(searchTextView.getText().length());
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Helper.hideKeyboard(getApplicationContext(), drawerLayout.getWindowToken());
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void saveQueryLocation() {
        CommonHelper.SEARCH_QUERY_LOCATION = new Location("LOCATION");
        List<String> coordinates = Arrays.asList(CommonHelper.QUERY.replaceAll("\\s+", "").split(","));
        CommonHelper.SEARCH_QUERY_LOCATION.setLatitude(Double.valueOf(coordinates.get(0)));
        CommonHelper.SEARCH_QUERY_LOCATION.setLongitude(Double.valueOf(coordinates.get(1)));
        Log.d("QUERY", "location: " + CommonHelper.SEARCH_QUERY_LOCATION.toString());
    }

    private void getNextTokenResult() {
        presenter.getNextResults(CommonHelper.QUERY);
    }

    private void addFragments() {

       /* Fragment fragment = TextSearchFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.search_head_container, fragment).commit();*/

        Fragment f = SideFilterFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.side_filter_container, f).commit();
    }

    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.END);
    }

    @Override
    public void updatePlaces(List<Result> data) {
        if (CommonHelper.SEARCH_MODE == 2) {
            searchTextView.clearFocus();
        }
        adapter.updateData(data);
    }

    @Override
    public void showProgressBar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {

        switch (message) {

            case "INVALID_REQUEST":
                showInfoText("Solicitud no válida");
                break;
            case "ZERO_RESULTS":
                showInfoText("No hay resultados");
                break;
            default:
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        CommonHelper.MY_LOCATION = location;
        Log.d(TAG, location.toString());
    }

    @Override
    public void clearData() {
        adapter.clearData();
    }

    @Override
    public void hideKeyboard() {
        Helper.hideKeyboard(this, drawerLayout.getWindowToken());
    }

    @Override
    public void hideInfoText() {
        txvInfo.setVisibility(View.GONE);
        Log.d(TAG, "searchactivity hide info text");
    }

    public void showInfoText(String message) {
        txvInfo.setVisibility(View.VISIBLE);
        txvInfo.setText(message);
    }

    private void initVariables() {

        CommonHelper.MY_LOCATION = null;
        CommonHelper.NEXT_PAGE_TOKEN = null;
        CommonHelper.QUERY = null;
        CommonHelper.SEARCH_MODE = 1;
        CommonHelper.location = null;
        CommonHelper.radius = "1000";
        CommonHelper.KEYWORD = null;
        CommonHelper.opennow = null;
        CommonHelper.RANKYBY = "prominence";
        CommonHelper.SEARCH_QUERY_LOCATION = null;
        CommonHelper.minprice = null;
    }

    @Override
    public void onItemClick(String placeId) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(CommonHelper.PLACE_ID, placeId);
        startActivity(intent);
    }

    public void newSearch(String currentQuery) {

        if (!Helper.isNetworkAvailable(this)) {
            Toast.makeText(this, "No tienes conexión a la red", Toast.LENGTH_SHORT).show();
            return;
        }

        CommonHelper.QUERY = currentQuery;
        if (CommonHelper.SEARCH_MODE == 1) {
            CommonHelper.NEXT_PAGE_TOKEN = null;
            presenter.newSearch(currentQuery);
        } else if (CommonHelper.SEARCH_MODE == 2) {
            if (isValidLocationInput(currentQuery)) {
                CommonHelper.NEXT_PAGE_TOKEN = null;
                presenter.newSearch(currentQuery);
            }
        }
    }

    public void changeSearchBarHint() {
        if (CommonHelper.SEARCH_MODE == 1) {
            //searchTextView.setText("restaurants in new york");
            //searchTextView.setSelection(searchTextView.getText().length());
            searchTextView.setText(null);
        } else if (CommonHelper.SEARCH_MODE == 2) {
            if (CommonHelper.MY_LOCATION != null) {
                searchTextView.setText(String.format("%1$.6f,%2$.6f", CommonHelper.MY_LOCATION.getLatitude(), CommonHelper.MY_LOCATION.getLongitude()));
                //searchTextView.setSelection(searchTextView.getText().length());
                searchTextView.clearFocus();
            } else {
                Toast.makeText(this, "No es posible ubicarte", Toast.LENGTH_LONG).show();
            }
        } else {
            throw new IllegalArgumentException("Invalid search mode");
        }
    }

    private boolean isValidLocationInput(String location) {

        if (location.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debes ingresar los parámetros requeridos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!ConstantsHelper.LAT_LNG_PATTERN.matcher(location).matches()) {
            Toast.makeText(getApplicationContext(), "Los datos no son válidos", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.filter_btn)
    public void onViewClicked() {
        hideKeyboard();
        openDrawer();
    }
}
