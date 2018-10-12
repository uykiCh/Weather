package com.company.setname.weather.models.json_structure.response.weather_list;

import android.arch.persistence.room.Entity;

import com.company.setname.weather.models.json_structure.response.weather_list.clouds.Clouds;
import com.company.setname.weather.models.json_structure.response.weather_list.weather.Weather;
import com.company.setname.weather.models.json_structure.response.weather_list.weather_list_main.WeatherListMain;
import com.company.setname.weather.models.json_structure.response.weather_list.wind.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class WeatherList {

    @SerializedName("main")
    @Expose
    WeatherListMain weatherListMain;

    @SerializedName("weather")
    @Expose
    ArrayList<Weather> weather;

    @SerializedName("clouds")
    @Expose
    Clouds clouds;

    @SerializedName("wind")
    @Expose
    Wind wind;

    @SerializedName("dt")
    private Long timestamp;

    public WeatherListMain getWeatherListMain() {
        return weatherListMain;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setWeatherListMain(WeatherListMain weatherListMain) {
        this.weatherListMain = weatherListMain;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
