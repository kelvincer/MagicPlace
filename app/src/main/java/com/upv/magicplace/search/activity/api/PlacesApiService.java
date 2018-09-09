package com.upv.magicplace.search.activity.api;

import com.upv.magicplace.response.PlaceApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApiService {

    @GET("textsearch/json")
    Call<PlaceApiResponse> getTextResults(@Query("query") String query,           // required
                                          @Query("key") String key,               // required
                                          @Query("location") String location,     // optional
                                          @Query("radius") String radius,         // optional
                                          @Query("minprice") String minprice,     // optional
                                          @Query("opennow") String opennow,       // optional
                                          @Query("pagetoken") String token);      // required if exist

    @GET("nearbysearch/json")
    Call<PlaceApiResponse> getNearbyResults(@Query("key") String key,             // required
                                            @Query("location") String location,   // required
                                            @Query("radius") String radius,       // required with condition
                                            @Query("pagetoken") String token,     // optional with condition
                                            @Query("keyword") String keyword,     // optional
                                            @Query("minprice") String minprice,   // optional
                                            @Query("opennow") String opennow,     // optional
                                            @Query("rankby") String rankby);      // optional

}
