package com.company.setname.weather;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.company.setname.weather.data.App;
import com.company.setname.weather.data.dao.WeatherDAO;
import com.company.setname.weather.data.database.WeatherDatabase;
import com.company.setname.weather.data.model.ModelDatabase;
import com.company.setname.weather.data.model.WeatherModelForDatabase;
import com.company.setname.weather.fragment.FragmentRecyclerViewForWeek;
import com.company.setname.weather.fragment.FragmentMoreAbout;
import com.company.setname.weather.models.json_structure.ModelResponse;
import com.company.setname.weather.models.json_structure.response.weather_list.WeatherList;
import com.company.setname.weather.models.json_structure.response.weather_list.weather.Weather;
import com.company.setname.weather.models.json_structure.response.weather_list.weather_list_main.WeatherListMain;
import com.company.setname.weather.retrofit_settings.RetrofitSettings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityLog";
    private static final String API_KEY = "fabd53f6a71e2f82c551ff0c5eda930b";
    private static final Integer MOSCOW_CITY_CODE = 524901;
    private static final String UNITS = "metric";
    private static final Integer S_TO_MS = 1000;

    private RetrofitSettings.WeatherAPI weatherService;

    private WeatherDatabase database;
    private WeatherDAO weatherDAO;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherService = new RetrofitSettings().getClient().create(RetrofitSettings.WeatherAPI.class);

        /*getWeather();*/

        setViewPager();

    }

    private void setViewPager() {

        viewPager = findViewById(R.id.activity_main_viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

    }

    void getWeather() {

        Call<ModelResponse> callToday = weatherService.getForecast(MOSCOW_CITY_CODE, UNITS, API_KEY);

        callToday.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModelResponse> call,
                                   @NonNull Response<ModelResponse> response) {

                ModelResponse data = response.body();

                Log.e(TAG, "onResponse");
                Log.d(TAG, response.toString());

                assert data != null;
                List<WeatherList> list = data.getList();

                if (response.isSuccessful()) {

                    Log.i(TAG, String.valueOf(response.body().getCnt()));

                    database = App.getInstance().getDatabase();
                    weatherDAO = database.weatherDAO();

                    Log.i(TAG, "Size: " + weatherDAO.getAll().size());

                    weatherDAO.insert(new ModelDatabase(parserFromListItemToWeatherModelForDatabase(data.getList().get(2)),
                            list.get(2).getTimestamp() * S_TO_MS));

                    Log.i(TAG, "Size: " + weatherDAO.getAll().size());

                    weatherDAO.deleteUseless(System.currentTimeMillis()/1000);

                    Log.i(TAG, "Size: " + weatherDAO.getAll().size());

                    Log.i(TAG, weatherDAO.getAll().get(weatherDAO.getAll().size() - 1).toString());

                }

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {

                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());

            }

        });

    }

    WeatherModelForDatabase parserFromListItemToWeatherModelForDatabase(WeatherList weatherList) {

        WeatherModelForDatabase weatherModelForDatabase = new WeatherModelForDatabase();
        WeatherListMain weatherListMain = weatherList.getWeatherListMain();

        weatherModelForDatabase.setMain_min_temp(weatherListMain.getMin_temp());
        weatherModelForDatabase.setMain_max_temp(weatherListMain.getMax_temp());
        weatherModelForDatabase.setMain_tem(weatherListMain.getTem());
        weatherModelForDatabase.setMain_pressure(weatherListMain.getPressure());
        weatherModelForDatabase.setMain_humidity(weatherListMain.getHumidity());
        weatherModelForDatabase.setCloud_percent(weatherList.getClouds().getPercent());
        weatherModelForDatabase.setWind_speed(weatherList.getWind().getSpeed());
        weatherModelForDatabase.setWind_deg(weatherList.getWind().getDeg());

        //Да, это комментарий на русском
        //Непонятно почему дают списком
        //Чтобы внести больше непонятности буду брать только первый элемент(всегда только один в JSON от openweathermap)
        Weather weather = weatherList.getWeather().get(0);
        weatherModelForDatabase.setWeather_id(weather.getId());
        weatherModelForDatabase.setWeather_main(weather.getMain());
        weatherModelForDatabase.setWeather_description(weather.getDescription());
        weatherModelForDatabase.setWeather_icon_id(weather.getIcon_id());

        return weatherModelForDatabase;

    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return new FragmentRecyclerViewForWeek();
                case 1: return new FragmentMoreAbout();
                default: return new FragmentRecyclerViewForWeek();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
