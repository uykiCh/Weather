package com.company.setname.weather.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.company.setname.weather.data.database.WeatherDatabase;

public class App extends Application {

    public static App instance;

    private WeatherDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, WeatherDatabase.class, "weather")
                .allowMainThreadQueries()
                .build();

    }

    public static App getInstance() {
        return instance;
    }

    public WeatherDatabase getDatabase() {
        return database;
    }
}
