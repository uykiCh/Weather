package com.company.setname.weather;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.company.setname.weather.adapter.model_of_items.Model;
import com.company.setname.weather.adapter.three_hours_adapter.ThreeHoursAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

        getWeather();

        /*setViewPager();

        setThreeHoursRV();*/

    }

    private void setThreeHoursRV() {

        RecyclerView recyclerView = findViewById(R.id.activity_main_recyclerview_three_hours);

        List<Model> listOfModels = new ArrayList<>();
        ThreeHoursAdapter threeHoursAdapter = new ThreeHoursAdapter(listOfModels);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false
        ));
        recyclerView.setAdapter(threeHoursAdapter);

        for (int i = 0; i < 10; i++) {
            listOfModels.add(new Model(1, "04d", 12d, 1539359816519L - i * 1000000));
        }

        threeHoursAdapter.notifyDataSetChanged();

    }

    private void setViewPager() {

        viewPager = findViewById(R.id.activity_main_viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

    }

    void getWeather() {

        database = App.getInstance().getDatabase();
        weatherDAO = database.weatherDAO();

        Call<ModelResponse> callToday = weatherService.getForecast(MOSCOW_CITY_CODE, UNITS, API_KEY);

        callToday.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModelResponse> call,
                                   @NonNull Response<ModelResponse> response) {

                Log.e(TAG, "onResponse");
                Log.d(TAG, response.toString());

                if (response.isSuccessful()) {

                    List<WeatherList> listOfData = response.body().getList();
                    List<ModelDatabase> listWMFD = weatherDAO.getAll();

                    Log.i(TAG, "Size(listWMFD): " + listWMFD.size());
                    Log.i(TAG, "Size(listOfData): " + listOfData.size());

                    Set<Long> fromListOfData = new LinkedHashSet<>();
                    for (int i = 0; i < listOfData.size(); i++) {
                        fromListOfData.add(listOfData.get(i).getTimestamp() * S_TO_MS);
                        Log.i(TAG, "Time(fromListOfData): " + listOfData.get(i).getTimestamp() * 1000);
                    }

                    Set<Long> fromListWMFD = new LinkedHashSet<>();
                    for (int i = 0; i < listWMFD.size(); i++) {
                        fromListWMFD.add(listWMFD.get(i).getTime());
                        Log.i(TAG, "Time(fromListWMFD): " + listWMFD.get(i).getTime());
                    }

                    fromListOfData.removeAll(fromListWMFD);

                    Log.i(TAG, "Size(fromListWMFD): " + fromListWMFD.size());
                    Log.i(TAG, "Size(fromListOfData): " + fromListOfData.size());

                    List<Long> fromListOfDataList = new ArrayList<>(fromListOfData);
                    Log.i(TAG, "fromListOfDataList: " + Arrays.toString(fromListOfDataList.toArray()));

                    for (int i = 0; i < fromListOfDataList.size(); i++) {

                        for (int j = 0; j < listOfData.size(); j++) {

                            if (fromListOfDataList.get(i)/1000 == listOfData.get(j).getTimestamp()) {

                                Log.i(TAG, "" + System.currentTimeMillis());
                                Log.i(TAG, "" + fromListOfDataList.get(i));
                                Log.i(TAG, "" + (System.currentTimeMillis()-fromListOfDataList.get(i)));

                                if (System.currentTimeMillis()-fromListOfDataList.get(i)>=10800000){

                                    Log.i(TAG, "onResponse: ");

                                }

                                break;

                            }
                        }
                    }


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
            switch (pos) {

                case 0:
                    return new FragmentRecyclerViewForWeek();
                case 1:
                    return FragmentMoreAbout.newInstance(new WeatherModelForDatabase());
                default:
                    return new FragmentRecyclerViewForWeek();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
