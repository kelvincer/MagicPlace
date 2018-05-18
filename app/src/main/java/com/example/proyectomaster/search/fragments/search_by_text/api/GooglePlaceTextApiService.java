package com.example.proyectomaster.search.fragments.search_by_text.api;

import com.example.proyectomaster.response.PlaceApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlaceTextApiService {

    @GET("textsearch/json")
    Call<PlaceApiResponse> getPlaces(@Query("query") String query,
                                     @Query("key") String key,
                                     @Query("pagetoken") String token);

}
