package com.company.setname.weather.fragments.more_about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.company.setname.weather.R;
import com.company.setname.weather.data.model.WeatherModelForDatabase;

public class FragmentMoreAbout extends Fragment implements Updatable {

    private final static String TAG = "FragmentMoreAbout";

    private View view;

    private TextView minTemp;
    private TextView maxTemp;
    private TextView midTemp;
    private TextView pressure;
    private TextView humidity;
    private TextView cloudPercent;
    private TextView windSpeed;
    private TextView windDegrees;
    private TextView description;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_more, container, false);

        setViews();

        assert getArguments() != null;
        setData((WeatherModelForDatabase) getArguments().getSerializable("list_more"));

        return view;
    }

    public static FragmentMoreAbout newInstance(WeatherModelForDatabase weatherModelForDatabase) {

        Bundle args = new Bundle();
        args.putSerializable("list_more", weatherModelForDatabase);

        FragmentMoreAbout fragment = new FragmentMoreAbout();
        fragment.setArguments(args);
        return fragment;
    }

    private void setData(WeatherModelForDatabase weatherModelForDatabase) {
        minTemp.setText(String.valueOf(weatherModelForDatabase.getMain_min_temp()));
        maxTemp.setText(String.valueOf(weatherModelForDatabase.getMain_max_temp()));
        midTemp.setText(String.valueOf(weatherModelForDatabase.getMain_tem()));
        pressure.setText(String.valueOf(weatherModelForDatabase.getMain_pressure()));
        humidity.setText(String.valueOf(weatherModelForDatabase.getMain_humidity()));
        cloudPercent.setText(String.valueOf(weatherModelForDatabase.getCloud_percent()));
        windSpeed.setText(String.valueOf(weatherModelForDatabase.getWind_speed()));
        windDegrees.setText(String.valueOf(weatherModelForDatabase.getWind_deg()));
        description.setText(String.valueOf(weatherModelForDatabase.getWeather_description()));
    }

    private void setViews() {

        minTemp = view.findViewById(R.id.fragment_more_min_temp);
        maxTemp = view.findViewById(R.id.fragment_more_max_temp);
        midTemp = view.findViewById(R.id.fragment_more_temp);
        pressure = view.findViewById(R.id.fragment_more_pressure);
        humidity = view.findViewById(R.id.fragment_more_humidity);
        cloudPercent = view.findViewById(R.id.fragment_more_cloud_percent);
        windSpeed = view.findViewById(R.id.fragment_more_wind_speed);
        windDegrees = view.findViewById(R.id.fragment_more_wind_degrees);
        description = view.findViewById(R.id.fragment_more_description);

    }

    @Override
    public void update(WeatherModelForDatabase weatherModelForDatabase) {
        minTemp.setText(String.valueOf(weatherModelForDatabase.getMain_min_temp()));
        maxTemp.setText(String.valueOf(weatherModelForDatabase.getMain_max_temp()));
        midTemp.setText(String.valueOf(weatherModelForDatabase.getMain_tem()));
        pressure.setText(String.valueOf(weatherModelForDatabase.getMain_pressure()));
        humidity.setText(String.valueOf(weatherModelForDatabase.getMain_humidity()));
        cloudPercent.setText(String.valueOf(weatherModelForDatabase.getCloud_percent()));
        windSpeed.setText(String.valueOf(weatherModelForDatabase.getWind_speed()));
        windDegrees.setText(String.valueOf(weatherModelForDatabase.getWind_deg()));
        description.setText(String.valueOf(weatherModelForDatabase.getWeather_description()));
    }

}
