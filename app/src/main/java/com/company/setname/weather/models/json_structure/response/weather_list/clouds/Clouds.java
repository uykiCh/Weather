package com.company.setname.weather.models.json_structure.response.weather_list.clouds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    Integer percent;

    public Integer getPercent() {
        return percent;
    }
}
