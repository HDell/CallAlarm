package com.tuprojects.hd.callalarm;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this); //this assumes that we're creating directly from Activity
        //recyclerView.setLayoutManager(layoutManager || RecyclerView.LayoutManager);

        //My test data
        ArrayList<String> testData = new ArrayList<>();
        testData.add("a");
        testData.add("b");
        testData.add("c");
        testData.add("d");
        testData.add("e");
        testData.add("f");
        testData.add("g");
        testData.add("h");
        testData.add("i");
        testData.add("j");
        testData.add("k");
        testData.add("l");
        testData.add("m");
        testData.add("n");
        testData.add("o");
        testData.add("p");
        testData.add("q");
        testData.add("r");
        testData.add("s");
        testData.add("t");
        testData.add("u");
        testData.add("v");



        // 3. create an adapter
        HistoryAdapter adapter = new HistoryAdapter(testData);

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
