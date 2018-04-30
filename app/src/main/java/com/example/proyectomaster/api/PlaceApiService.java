package com.example.proyectomaster.api;

import com.example.proyectomaster.response.PlaceApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceApiService {

    @GET("json")
    Call<PlaceApiResponse> getPlaces(@Query("query") String query, @Query("key") String key);

}
