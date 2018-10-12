package com.company.setname.weather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.setname.weather.R;
import com.company.setname.weather.models.json_structure.response.weather_list.WeatherList;

public class FragmentMoreAbout extends Fragment {

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

        return view;
    }

    private void setData(WeatherList weatherList) {



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
}
