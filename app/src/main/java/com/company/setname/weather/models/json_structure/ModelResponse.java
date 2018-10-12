package com.company.setname.weather.models.json_structure;

import com.company.setname.weather.models.json_structure.response.weather_list.WeatherList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ModelResponse {

    @SerializedName("cnt")
    @Expose
    Integer cnt;

    @SerializedName("list")
    @Expose
    ArrayList<WeatherList> list;

    public Integer getCnt() {
        return cnt;
    }

    public ArrayList<WeatherList> getList() {
        return list;
    }
}
