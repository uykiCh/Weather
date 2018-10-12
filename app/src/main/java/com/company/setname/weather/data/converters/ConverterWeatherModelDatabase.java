package com.company.setname.weather.data.converters;

import android.arch.persistence.room.TypeConverter;
import com.company.setname.weather.data.model.ModelDatabase;
import com.company.setname.weather.data.model.WeatherModelForDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class ConverterWeatherModelDatabase {

    @TypeConverter
    public WeatherModelForDatabase fromString(String value) {
        Type listType = new TypeToken<WeatherModelForDatabase>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromArrayList(WeatherModelForDatabase list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
