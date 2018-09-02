package com.example.proyectomaster.search.activity.ui;

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
import android.text.InputType;
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

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.app.MainApplication;
import com.example.proyectomaster.detail.activity.ui.DetailActivity;
import com.example.proyectomaster.location.ApiLocationManager;
import com.example.proyectomaster.location.LocationCallback;
import com.example.proyectomaster.search.activity.SearchActivityPresenter;
import com.example.proyectomaster.search.activity.adapters.OnItemClickListener;
import com.example.proyectomaster.search.activity.adapters.RecyclerViewResultAdapter;
import com.example.proyectomaster.search.activity.di.SearchActivityModule;
import com.example.proyectomaster.search.entities.Result;
import com.example.proyectomaster.search.fragments.filter.SideFilterFragment;

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
        setContentView(R.layout.activity_search_demo);
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

        /*DaggerSearchActivityComponent.builder()
                .libsModule(new LibsModule(this))
                .searchActivityModule(new SearchActivityModule(this, this))
                .build()
                .inject(this);*/
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
                } else {
                    Toast.makeText(SearchActivity.this, "TOKEN == NULL", Toast.LENGTH_SHORT).show();
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
                        newSearch();
                        return true;
                    } else if (CommonHelper.SEARCH_MODE == 2) {
                        if (searchTextView.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Debes ingresar los parámetros requeridos", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (!ConstantsHelper.LAT_LNG_PATTERN.matcher(searchTextView.getText().toString()).matches()) {
                            Toast.makeText(getApplicationContext(), "Los datos no son válidos", Toast.LENGTH_LONG).show();
                            return false;
                        }

                        CommonHelper.SEARCH_QUERY_LOCATION = new Location("LOCATION");
                        List<String> coordinates = Arrays.asList(CommonHelper.QUERY.replaceAll("\\s+", "").split(","));
                        CommonHelper.SEARCH_QUERY_LOCATION.setLatitude(Double.valueOf(coordinates.get(0)));
                        CommonHelper.SEARCH_QUERY_LOCATION.setLongitude(Double.valueOf(coordinates.get(1)));
                        //CommonHelper.SEARCH_QUERY_LOCATION = CommonHelper.MY_LOCATION;
                        Log.d("QUERY", "location: " + CommonHelper.SEARCH_QUERY_LOCATION.toString());
                        newSearch();
                        return true;
                    }
                }
                return false;
            }
        });
        searchTextView.setText("restaurants in new York");
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
    }

    private void newSearch() {

        Log.d(TAG, "query " + CommonHelper.QUERY);
        if (CommonHelper.QUERY != null) {
            newSearch(CommonHelper.QUERY);
        } else {
            Toast.makeText(getApplicationContext(), "QUERY IS NULL", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(String placeId) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(CommonHelper.PLACE_ID, placeId);
        startActivity(intent);
    }

    public void newSearch(String currentQuery) {
        CommonHelper.NEXT_PAGE_TOKEN = null;
        presenter.newSearch(currentQuery);
    }

    public void changeSearchBarHint() {
        if (CommonHelper.SEARCH_MODE == 1) {
            searchTextView.setText("restaurants in new york");
            searchTextView.setSelection(searchTextView.getText().length());
            /*searchTextView.setInputType(InputType.TYPE_CLASS_TEXT);
            searchTextView.setSingleLine();
            searchTextView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);*/
            //searchTextView.setEnabled(true);
        } else if (CommonHelper.SEARCH_MODE == 2) {
            searchTextView.setText("40.730610,-73.935242");
            searchTextView.setSelection(searchTextView.getText().length());
            //searchTextView.setSelection(searchTextView.getText().length());
            //searchTextView.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            //searchTextView.setKeyListener(DigitsKeyListener.getInstance("0123456789.,-"));
        } else {
            throw new IllegalArgumentException("Invalid search mode");
        }
    }

    @OnClick(R.id.filter_btn)
    public void onViewClicked() {
        hideKeyboard();
        openDrawer();
    }
}
