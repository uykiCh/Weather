package com.company.setname.weather.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.company.setname.weather.data.converters.ConverterWeatherModelDatabase;
import com.company.setname.weather.data.dao.WeatherDAO;
import com.company.setname.weather.data.model.ModelDatabase;

@Database(entities = {ModelDatabase.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterWeatherModelDatabase.class})
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDAO weatherDAO();
}
