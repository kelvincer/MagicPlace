package com.example.proyectomaster;

import android.location.Location;

public class CommonHelper {

    public static final String PLACE_ID = "place_id";
    public static final String RESULT = "result";
    public static final String PHOTO_REF = "photo_ref";
    public static final String BITMAP_PATH = "bitmap_path";
    public static final String PLACE_NAME = "Title";
    public static Location MY_LOCATION;
    public static Location SEARCH_QUERY_LOCATION;
    public static int SOURCE_MODE = 1;
    public static int SEARCH_MODE = 1;

    //required parameters

    public static String NEXT_PAGE_TOKEN;
    public static String QUERY;

    public static String radius = "1000";

    //optional parameters

    public static String location;
    public static String opennow; // also nearby
    public static String minprice; // also nearby

    // optional parameters for nearby search

    public static String KEYWORD;
    public static String RANKYBY = "prominence";
}
