package com.tuprojects.hd.callalarm;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CallListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.call_list_fragment, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.call_list_recycler_view);
        //private RecyclerView recyclerView;
        //recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // 2. set layoutManger
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this); //this assumes that we're creating directly from Activity
        //recyclerView.setLayoutManager(layoutManager || RecyclerView.LayoutManager);

        //My test data
        ArrayList<String> testDataNames = new ArrayList<>();
        testDataNames.add("Ashley");
        testDataNames.add("Dad");
        testDataNames.add("Mom");

        List<String> testDataFrequency = new ArrayList<>();
        testDataFrequency.add("Daily");
        testDataFrequency.add("Once every 3 weeks");
        testDataFrequency.add("Weekly");

        List<String> testDataHistory = new ArrayList<>();
        testDataHistory.add("Spoke for 19 min. yesterday");
        testDataHistory.add("Spoke 6 days ago");
        testDataHistory.add("Missed 1 call yesterday");

        List<List<String>> testData = new ArrayList<>();
        testData.add(testDataNames);
        testData.add(testDataFrequency);
        testData.add(testDataHistory);

        // 3. create an adapter
        CallListAdapter adapter = new CallListAdapter(testData);

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }
}
