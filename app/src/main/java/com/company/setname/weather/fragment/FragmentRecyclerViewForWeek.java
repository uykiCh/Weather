package com.company.setname.weather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.setname.weather.R;
import com.company.setname.weather.adapter.every_day_adapter.EveryDayAdapter;
import com.company.setname.weather.adapter.model_of_items.Model;

import java.util.ArrayList;
import java.util.List;

public class FragmentRecyclerViewForWeek extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_week, container, false);

        setEveryDayRV();

        return view;
    }

    private void setEveryDayRV() {

        List<Model> list = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.framgent_week_recyclerview);
        EveryDayAdapter everyDayAdapter = new EveryDayAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerView.setAdapter(everyDayAdapter);

        list.add(new Model(2, "01d", 1d, 1539361231852L));
        list.add(new Model(2, "01d", 1d, 1539274831802L));
        list.add(new Model(2, "01d", 1d, 1539188431802L));
        list.add(new Model(2, "01d", 1d, 1539102031802L));
        list.add(new Model(2, "01d", 1d, 1539015631802L));
        list.add(new Model(2, "01d", 1d, 1538929231802L));

    }
}
