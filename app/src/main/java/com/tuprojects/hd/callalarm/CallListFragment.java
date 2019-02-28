package com.tuprojects.hd.callalarm;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this); //this assumes that we're creating directly from Activity
        //recyclerView.setLayoutManager(layoutManager || RecyclerView.LayoutManager);

        //My test data
        ArrayList<String> testData = new ArrayList<>();
        testData.add("aa");
        testData.add("bb");
        testData.add("cc");
        testData.add("dd");
        testData.add("ee");
        testData.add("ff");
        testData.add("gg");
        testData.add("hh");
        testData.add("ii");
        testData.add("jj");
        testData.add("kk");
        testData.add("ll");
        testData.add("mm");
        testData.add("nn");
        testData.add("oo");
        testData.add("pp");
        testData.add("qq");
        testData.add("rr");
        testData.add("ss");
        testData.add("tt");
        testData.add("uu");
        testData.add("vv");



        // 3. create an adapter
        CallListAdapter adapter = new CallListAdapter(testData);

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
