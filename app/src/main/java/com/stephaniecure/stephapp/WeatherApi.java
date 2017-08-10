package com.stephaniecure.stephapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stephaniecure on 8/9/17.
 */


public interface WeatherApi {

    @GET("/data/2.5/weather/")
    Call<WeatherResponse> getWeatherForCity(@Query("zip") String cityId, @Query("appid") String appId);
}