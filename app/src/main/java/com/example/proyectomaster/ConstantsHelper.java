package com.example.proyectomaster;

import java.util.regex.Pattern;

public class ConstantsHelper {

    public static final String GOOGLE_PLACE_API_KEY = "AIzaSyCwmYvGIV7owfcc7muneajVaIz6cXKA8Wg";
    public final static String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    public static final String FIREBASE_STORAGE = "gs://master-aeea3.appspot.com/";
    public static final Pattern LAT_LNG_PATTERN = Pattern.compile("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$");

}