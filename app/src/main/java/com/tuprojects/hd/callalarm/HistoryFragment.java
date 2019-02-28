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

public class HistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.history_fragment, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.history_recycler_view);
        //private RecyclerView recyclerView;
        //recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // 2. set layoutManger
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this); //this assumes that we're creating directly from Activity
        //recyclerView.setLayoutManager(layoutManager || RecyclerView.LayoutManager);

        //My test data
        List<String> testDataNames = new ArrayList<>();
        testDataNames.add("Mom");
        testDataNames.add("Ashley");
        testDataNames.add("Ashley");
        testDataNames.add("Mom");
        testDataNames.add("Ashley");
        testDataNames.add("Ashley");
        testDataNames.add("Mom");
        testDataNames.add("Dad");
        testDataNames.add("Ashley");
        testDataNames.add("Mom");
        testDataNames.add("Dad");
        testDataNames.add("Dad");

        List<String> testDataDetails = new ArrayList<>();
        testDataDetails.add("Incoming (missed), Yesterday, 4:32 PM");
        testDataDetails.add("Incoming (received), Yesterday, 12:16 PM");
        testDataDetails.add("Incoming (missed), Yesterday, 12:08 PM");
        testDataDetails.add("Incoming (received), 2 days ago, 3:32 PM");
        testDataDetails.add("Incoming (received), 3 days ago, 4:32 PM");
        testDataDetails.add("Incoming (received), 4 days ago, 5:32 PM");
        testDataDetails.add("Incoming (received), 5 days ago, 6:32 PM");
        testDataDetails.add("Incoming (received), 6 days ago, 7:32 PM");
        testDataDetails.add("Incoming (received), October 30, 8:32 AM");
        testDataDetails.add("Incoming (received), October 29, 9:32 AM");
        testDataDetails.add("Incoming (received), October 28, 12:32 PM");
        testDataDetails.add("Incoming (missed), October 28, 10:32 AM");
        
        List<List<String>> testData = new ArrayList<>();
        testData.add(testDataNames);
        testData.add(testDataDetails);

        // 3. create an adapter
        HistoryAdapter adapter = new HistoryAdapter(testData);

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }
}
