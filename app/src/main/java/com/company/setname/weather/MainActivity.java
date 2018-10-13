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
import android.widget.TextView;

import com.company.setname.weather.adapter.model_of_items.Model;
import com.company.setname.weather.adapter.three_hours_adapter.ItemListener;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemListener {

    private static final String TAG = "MainActivityLog";
    private static final String API_KEY = "fabd53f6a71e2f82c551ff0c5eda930b";
    private static final Integer MOSCOW_CITY_CODE = 524901;
    private static final String UNITS = "metric";
    private static final Integer S_TO_MS = 1000;

    private RetrofitSettings.WeatherAPI weatherService;

    private WeatherDatabase database;
    private WeatherDAO weatherDAO;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private AtomicReference<Long> weatherModelForDatabaseAtomicReferenceNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSettings();

        weatherModelForDatabaseAtomicReferenceNumber = new AtomicReference<>();

        //update database
        weatherDAO.deleteUseless(System.currentTimeMillis());
        //load forecast
        getWeather(MOSCOW_CITY_CODE);

        setViewPager();
        setThreeHoursRV(getThreeHoursFCFromDatabase(MOSCOW_CITY_CODE));
        setWidgets(MOSCOW_CITY_CODE, weatherDAO.getMinTime());

        Log.i(TAG, "Cur. time: " + System.currentTimeMillis());

    }

    private void setSettings() {
        weatherService = new RetrofitSettings().getClient().create(RetrofitSettings.WeatherAPI.class);
        database = App.getInstance().getDatabase();
        weatherDAO = database.weatherDAO();
    }

    private List<Model> getThreeHoursFCFromDatabase(long code) {

        List<ModelDatabase> modelDatabaseList = weatherDAO.getAllWithLimit8(code);
        List<Model> modelList = new ArrayList<>();
        for (int i = 0; i < modelDatabaseList.size(); i++) {
            ModelDatabase modelDatabase = modelDatabaseList.get(i);
            modelList.add(new Model(modelDatabase.getId(), modelDatabase.getWeather_model().getWeather_icon_id(),
                    modelDatabase.getWeather_model().getMain_tem(), modelDatabase.getTime()));
        }

        return modelList;

    }

    private void setWidgets(long city_code, long timestamp) {

        List<ModelDatabase> modelDatabaseList = weatherDAO.getAll(city_code);
        WeatherModelForDatabase weatherModelForDatabase = weatherDAO.getRowByTime(timestamp).getWeather_model();

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), modelDatabaseList, weatherModelForDatabase);
        viewPager.setAdapter(pagerAdapter);

        setCurrentTemp(weatherModelForDatabase.getMain_tem(), weatherModelForDatabase.getWeather_description());

    }

    private void setThreeHoursRV(List<Model> listOfModels) {

        RecyclerView recyclerView = findViewById(R.id.activity_main_recyclerview_three_hours);
        ThreeHoursAdapter threeHoursAdapter = new ThreeHoursAdapter( this, listOfModels);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false
        ));
        recyclerView.setAdapter(threeHoursAdapter);

        threeHoursAdapter.notifyDataSetChanged();

    }

    private void setViewPager() {

        viewPager = findViewById(R.id.activity_main_viewpager);

    }

    void getWeather(final long cityCode) {

        Call<ModelResponse> callToday = weatherService.getForecast(cityCode, UNITS, API_KEY);

        callToday.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModelResponse> call,
                                   @NonNull Response<ModelResponse> response) {

                Log.e(TAG, "onResponse");
                Log.d(TAG, response.toString());

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    List<WeatherList> listOfData = response.body().getList();
                    List<ModelDatabase> listWMFD = weatherDAO.getAll(cityCode);

                    weatherDAO.deleteUseless(System.currentTimeMillis());
                    selectActual(listOfData, listWMFD, cityCode);

                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResponse> call, @NonNull Throwable t) {

                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());

            }

        });

    }

    private void selectActual(List<WeatherList> listOfData, List<ModelDatabase> listWMFD, long cityCode) {

        /*

        This void select only actual data

         */

        //get timestamp from list1 to heep for find that is not exist in database
        Set<Long> fromListOfData = new LinkedHashSet<>();
        for (int i = 0; i < listOfData.size(); i++) {
            fromListOfData.add(listOfData.get(i).getTimestamp() * S_TO_MS);
            Log.i(TAG, "Time(fromListOfData): " + listOfData.get(i).getTimestamp() * 1000);
        }

        //get timestamp from list2 to heep for find that is not exist in database
        Set<Long> fromListWMFD = new LinkedHashSet<>();
        for (int i = 0; i < listWMFD.size(); i++) {
            fromListWMFD.add(listWMFD.get(i).getTime());
            Log.i(TAG, "Time(fromListWMFD): " + listWMFD.get(i).getTime());
        }

        //remove non actually information
        fromListOfData.removeAll(fromListWMFD);

        List<Long> fromListOfDataList = new ArrayList<>(fromListOfData);

        //find from listOfData the timestamps that equals with elements from fromListOfData

        for (int i = 0; i < fromListOfDataList.size(); i++) {

            //time1 from ms to s
            long checkSum = fromListOfDataList.get(i) / 1000;

            for (int j = 0; j < listOfData.size(); j++) {

                //if time1 == time2
                if (checkSum == listOfData.get(j).getTimestamp()) {

                    //check if forecast is out of 3h
                    if (!(System.currentTimeMillis() - fromListOfDataList.get(i) >= 10800000)) {

                        //add new row in database
                        weatherDAO.insert(new ModelDatabase(parserFromListItemToWeatherModelForDatabase(listOfData.get(j)),
                                fromListOfDataList.get(i), cityCode));

                        Log.i(TAG, "New row in table");

                        break;

                    }
                }
            }
        }


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

    @Override
    public void onClickItemListener(long id) {
        weatherModelForDatabaseAtomicReferenceNumber.set(id);
        notifyViewPagerDataSetChanged();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        List<ModelDatabase> modelDatabaseList;
        WeatherModelForDatabase weatherModelForDatabase;

        public PagerAdapter(FragmentManager fm, List<ModelDatabase> modelDatabaseList, WeatherModelForDatabase weatherModelForDatabase) {
            super(fm);
            this.modelDatabaseList = modelDatabaseList;
            this.weatherModelForDatabase = weatherModelForDatabase;
        }

        @Override
        public Fragment getItem(int pos) {
            if (pos == 0){
                return FragmentRecyclerViewForWeek.newInstance(modelDatabaseList);
            }
            else if (pos == 1){
                return FragmentMoreAbout.newInstance(weatherModelForDatabase);
            }
            else {
                return null;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof FragmentMoreAbout){
                ((FragmentMoreAbout) object).update(weatherDAO.getRowById(weatherModelForDatabaseAtomicReferenceNumber.get()).getWeather_model());
            }
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void destroyItem(android.view.ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        };

    }

    private void notifyViewPagerDataSetChanged() {
        pagerAdapter.notifyDataSetChanged();
    }

    private void setCurrentTemp(double d, String desc){
        TextView currentTemp = findViewById(R.id.activity_main_current_temp);
        currentTemp.setText(String.valueOf((int) d));
        TextView currentDesc = findViewById(R.id.activity_main_current_desc);
        currentDesc.setText(desc);
    }
}
