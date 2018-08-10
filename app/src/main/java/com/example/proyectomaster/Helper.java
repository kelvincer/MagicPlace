package com.example.proyectomaster;


import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.proyectomaster.search.entities.Location;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Helper {

    public static String getDistanceFromQueryLocation(Location location) {

        android.location.Location locationA = new android.location.Location("Place");
        locationA.setLatitude(location.getLat());
        locationA.setLongitude(location.getLng());

        //Log.d("SEARCH QUERY LOCATION", CommonHelper.SEARCH_QUERY_LOCATION.toString());

        if (CommonHelper.SEARCH_QUERY_LOCATION != null) {
            double d = locationA.distanceTo(CommonHelper.SEARCH_QUERY_LOCATION);
            return String.format("%.2fkm", d / 1000);
        } else
            return "NO LOCATION";
    }

    public static String getDistanceFromMyLocation(Location location) {
        android.location.Location locationA = new android.location.Location("Place");
        locationA.setLatitude(location.getLat());
        locationA.setLongitude(location.getLng());

        if (CommonHelper.MY_LOCATION != null) {
            double d = locationA.distanceTo(CommonHelper.MY_LOCATION);
            return String.format("%.2fkm", d / 1000);
        } else
            return "NO LOCATION";
    }

    public static String generateUrl(String photoReference) {
        String url = String.format("https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&key=AIzaSyCwmYvGIV7owfcc7muneajVaIz6cXKA8Wg" +
                "&maxheight=800", photoReference);

        return url;
    }

    public static void hideKeyboard(Context context, IBinder iBinder) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(iBinder, 0);
    }
}
