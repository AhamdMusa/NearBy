package com.example.nearby.retrofit;

import com.example.nearby.pogos.NearbyLocation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPI {
    @GET
    Call<NearbyLocation> getNearByPlaces(@Url String url);
}
