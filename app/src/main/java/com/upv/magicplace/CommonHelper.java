package com.upv.magicplace;

import android.location.Location;

public class CommonHelper {

    public static final String PLACE_ID = "place_id";
    public static final String RESULT = "result";
    public static final String PHOTO_REF = "photo_ref";
    public static final String BITMAP_PATH = "bitmap_path";
    public static final String PLACE_NAME = "Title";
    public static final String MESSAGE = "Message";
    public static final String PATH = "Path";
    public static final String FROM_FRAGMENT = "From_Fragment";
    public static final int FROM_HIGHLIGHTS = 101;
    public static final int FROM_PHOTOS = 102;
    public static final String PHOTO_URL = "photo_url";
    public static final String FIRE_PHOTO_MODEL = "firebase_photo_model";
    public static final String CATEGORY = "place_category";
    public static final String FROM_ACTIVITY = "from_activity";
    public static Location MY_LOCATION;
    public static Location SEARCH_QUERY_LOCATION;
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
