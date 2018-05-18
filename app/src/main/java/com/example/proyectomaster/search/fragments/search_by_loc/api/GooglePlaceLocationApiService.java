package com.example.proyectomaster.search.fragments.search_by_loc.api;

import com.example.proyectomaster.response.PlaceApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlaceLocationApiService {

    @GET("nearbysearch/json")
    Call<PlaceApiResponse> getPlaces(@Query("location") String location,
                                     @Query("radius") String radius,
                                     @Query("key") String key,
                                     @Query("pagetoken") String token,
                                     @Query("rankby") String rankby);
}
