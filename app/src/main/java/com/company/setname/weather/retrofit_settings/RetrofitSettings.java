package com.company.setname.weather.retrofit_settings;

import com.company.setname.weather.models.json_structure.ModelResponse;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitSettings {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    public retrofit2.Retrofit getClient(){
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public interface WeatherAPI {
        @GET("forecast")
        Call<ModelResponse> getForecast(
                @Query("id") Integer id,
                @Query("units") String units,
                @Query("appid") String appid
        );
    }

}
