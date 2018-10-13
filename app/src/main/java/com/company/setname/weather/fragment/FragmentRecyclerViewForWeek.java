package com.company.setname.weather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.setname.weather.R;
import com.company.setname.weather.adapter.every_day_adapter.EveryDayAdapter;
import com.company.setname.weather.adapter.model_of_items.Model;
import com.company.setname.weather.data.model.ModelDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentRecyclerViewForWeek extends Fragment {

    private static final String TAG = "FragmentRVForWeek";

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_week, container, false);

        try {
            setEveryDayRV();
        } catch (Exception e) {

        }
        return view;
    }

    private void setEveryDayRV() {

        List<Model> list = getListFromArg();

        RecyclerView recyclerView = view.findViewById(R.id.framgent_week_recyclerview);
        EveryDayAdapter everyDayAdapter = new EveryDayAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerView.setAdapter(everyDayAdapter);

    }

    private List<Model> getListFromArg() {

        List<Model> list = new ArrayList<>();
        List<ModelDatabase> modelDatabaseList = ((List<ModelDatabase>) getArguments().getSerializable("list"));

        for (int i = 0; i < 40; i++) {
            ModelDatabase modelDatabaseForCheck = modelDatabaseList.get(i);
            if (modelDatabaseForCheck.getTime() % 86400000 == 0) {
                if (i - 4 >= 0) {
                    ModelDatabase modelDatabase = modelDatabaseList.get(i - 4);
                    list.add(new Model(modelDatabase.getId(), modelDatabase.getWeather_model().getWeather_icon_id(),
                            modelDatabase.getWeather_model().getMain_tem(), modelDatabase.getTime()));
                    Log.d(TAG, "getListFromArg: time: " + modelDatabase.getTime());
                }
            }
        }

        return list;
    }

    public static FragmentRecyclerViewForWeek newInstance(List<ModelDatabase> modelDatabaseList) {

        Bundle args = new Bundle();

        args.putSerializable("list", (Serializable) modelDatabaseList);

        FragmentRecyclerViewForWeek fragment = new FragmentRecyclerViewForWeek();
        fragment.setArguments(args);
        return fragment;
    }
}
