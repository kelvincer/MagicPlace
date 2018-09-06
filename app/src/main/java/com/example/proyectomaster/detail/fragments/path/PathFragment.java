package com.example.proyectomaster.detail.fragments.path;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.fragments.path.api.DirectionsService;
import com.example.proyectomaster.detail.fragments.path.di.DaggerPathComponent;
import com.example.proyectomaster.detail.fragments.path.di.PathModule;
import com.example.proyectomaster.detail.fragments.path.model.DirectionsServiceResponse;
import com.example.proyectomaster.detail.fragments.path.model.GeoLatLng;
import com.example.proyectomaster.detail.fragments.path.model.Leg;
import com.example.proyectomaster.detail.fragments.path.model.Step;
import com.example.proyectomaster.location.ApiLocationManager;
import com.example.proyectomaster.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kelvin on 11/12/2017.
 */

public class PathFragment extends Fragment implements OnMapReadyCallback, LocationCallback {

    private static final String TAG = PathFragment.class.getSimpleName();
    private GoogleMap mMap;
    private LatLng myPosition;
    SupportMapFragment mapFragment;
    LatLng destino;
    boolean loadRoute = false;
    float mapZoom = 18f;
    Result result;
    ApiLocationManager locationManager;

    @Inject
    DirectionsService directionsService;

    public static Fragment getInstance(Result result) {

        Fragment fragment = new PathFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonHelper.RESULT, result);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        result = (Result) bundle.getSerializable(CommonHelper.RESULT);
        setupInjection();
        setUpLocationManager();
    }

    private void setupInjection() {

        DaggerPathComponent.builder().pathModule(new PathModule())
                .build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        setupViews();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, "Map ready");
        if (!loadRoute && myPosition != null) {
            setPathAndPosition();
            loadRoute = true;
        }
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                mapZoom = mMap.getCameraPosition().zoom;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationManager != null)
            locationManager.onInit();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationManager != null)
            locationManager.onPause();
    }

    private void setupViews() {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        destino = new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng());

    }

    private void setPathAndPosition() {
        mMap.clear();
        setMyPosition();
        traceRoute();
    }

    private void setMyPosition() {

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, mapZoom));
        MarkerOptions options = new MarkerOptions();
        options.position(myPosition);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.addMarker(options.position(myPosition).title("Mi posición"));
    }

    private void traceRoute() {

        MarkerOptions options = new MarkerOptions();
        options.position(destino);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(options.position(destino).title(result.getName()));


        GeoLatLng origen = new GeoLatLng();
        origen.setLat(myPosition.latitude);
        origen.setLng(myPosition.longitude);

        GeoLatLng destino = new GeoLatLng();
        destino.setLat(this.destino.latitude);
        destino.setLng(this.destino.longitude);

        Call<DirectionsServiceResponse> call = directionsService.buscarRuta(origen, destino, "driving", ConstantsHelper.DIRECTIONS_KEY);
        call.enqueue(new Callback<DirectionsServiceResponse>() {
            @Override
            public void onResponse(Call<DirectionsServiceResponse> call, Response<DirectionsServiceResponse> response) {

                Log.d(TAG, response.raw().toString());

                if (response.isSuccessful()) {

                    DirectionsServiceResponse directionsServiceResponse = response.body();

                    Log.d(TAG, directionsServiceResponse.getStatus());

                    List<List<HashMap<String, String>>> routes = getRoutes(directionsServiceResponse);

                    drawRoute(routes);
                }
            }

            @Override
            public void onFailure(Call<DirectionsServiceResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    public List<List<HashMap<String, String>>> getRoutes(DirectionsServiceResponse jObject) {

        List<List<HashMap<String, String>>> routes = new ArrayList<>();
        List<Leg> legs;
        List<Step> steps;

        for (int i = 0; i < jObject.getRoutes().size(); i++) {

            legs = jObject.getRoutes().get(i).getLegs();
            List path = new ArrayList<HashMap<String, String>>();

            for (int j = 0; j < legs.size(); j++) {

                steps = legs.get(j).getSteps();

                for (int k = 0; k < steps.size(); k++) {


                    String polyline = steps.get(k).getPolyline().getPoints();
                    List list = decodePoly(polyline);

                    for (int l = 0; l < list.size(); l++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                        hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                        path.add(hm);
                    }
                }

                routes.add(path);
            }
        }

        return routes;
    }


    private List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    protected void drawRoute(List<List<HashMap<String, String>>> result) {

        ArrayList points = null;
        PolylineOptions lineOptions = null;

        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList();
            lineOptions = new PolylineOptions();

            List<HashMap<String, String>> path = result.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            lineOptions.addAll(points);
            lineOptions.width(12);
            lineOptions.color(Color.RED);
            lineOptions.geodesic(true);
        }

        if (lineOptions != null)
            mMap.addPolyline(lineOptions);
        else
            Toast.makeText(getContext(), "No es posible trazar la ruta", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "location change " + location.toString());
        myPosition = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMap != null) {
            setPathAndPosition();
            loadRoute = true;
        }
    }

    private void setUpLocationManager() {
        locationManager = new ApiLocationManager(getContext());
        locationManager.setLocationCallback(this);
    }
}
