package com.company.setname.weather.models.json_structure.response.weather_list.weather_list_main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherListMain {

    @SerializedName("temp_min")
    @Expose
    Double min_temp;

    @SerializedName("temp_max")
    @Expose
    Double max_temp;

    @SerializedName("temp")
    @Expose
    Double tem;

    @SerializedName("pressure")
    @Expose
    Double pressure;

    @SerializedName("humidity")
    @Expose
    Integer humidity;

    public Double getMin_temp() {
        return min_temp;
    }

    public Double getMax_temp() {
        return max_temp;
    }

    public Double getTem() {
        return tem;
    }

    public Double getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }
}
