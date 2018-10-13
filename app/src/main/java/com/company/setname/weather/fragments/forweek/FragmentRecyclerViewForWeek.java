package com.company.setname.weather.fragments.forweek;

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

public class FragmentRecyclerViewForWeek extends Fragment implements Updatable{

    private static final String TAG = "FragmentRVForWeek";

    private View view;

    private List<Model> list;
    private RecyclerView recyclerView;
    private EveryDayAdapter everyDayAdapter;

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

        list = getListFromArg((List<ModelDatabase>) getArguments().getSerializable("list"));

        recyclerView = view.findViewById(R.id.framgent_week_recyclerview);
        everyDayAdapter = new EveryDayAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerView.setAdapter(everyDayAdapter);

    }

    private List<Model> getListFromArg(List<ModelDatabase> modelDatabaseList) {

        List<Model> list = new ArrayList<>();

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

    @Override
    public void update(List<ModelDatabase> modelDatabaseList) {
        list.clear();
        /*list.addAll(getListFromArg(modelDatabaseList));*/
        everyDayAdapter.notifyDataSetChanged();
    }
}
