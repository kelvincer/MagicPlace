package com.example.proyectomaster;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;

import com.example.proyectomaster.search.entities.Location;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Helper {

    public static String getDistanceFromQueryLocation(Location location) {

        android.location.Location locationA = new android.location.Location("Place");
        locationA.setLatitude(location.getLat());
        locationA.setLongitude(location.getLng());

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

    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static ArrayList<String> getAllShownImagesPath(Context context) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = context.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
}
