package com.company.setname.weather.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "weather")
public class ModelDatabase {

    public ModelDatabase() {}

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "weather_model")
    public WeatherModelForDatabase weather_model;

    @ColumnInfo(name = "time")
    public long time;

    public ModelDatabase(WeatherModelForDatabase weather_model, long time) {
        this.weather_model = weather_model;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeatherModelForDatabase getWeather_model() {
        return weather_model;
    }

    public void setWeather_model(WeatherModelForDatabase weather_model) {
        this.weather_model = weather_model;
    }

    public long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ModelDatabase{" +
                "id=" + id +
                ", weather_model=" + weather_model +
                ", time=" + time +
                '}';
    }
}
