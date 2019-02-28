package com.tuprojects.hd.callalarm;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.contacts_fragment, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.contacts_recycler_view);
        //private RecyclerView recyclerView;
        //recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this); //this assumes that we're creating directly from Activity
        //recyclerView.setLayoutManager(layoutManager || RecyclerView.LayoutManager);

        /*
        https://stackoverflow.com/questions/26621060/display-a-recyclerview-in-fragment
        // this is data for recycler view
        ItemData itemsData[] = {
            new ItemData("Indigo", R.drawable.circle),
            new ItemData("Red", R.drawable.color_ic_launcher),
            new ItemData("Blue", R.drawable.indigo),
            new ItemData("Green", R.drawable.circle),
            new ItemData("Amber", R.drawable.color_ic_launcher),
            new ItemData("Deep Orange", R.drawable.indigo)
         };
         */
        //My test data
        ArrayList<String> testData = new ArrayList<>();
        testData.add("aaa");
        testData.add("bbb");
        testData.add("ccc");
        testData.add("ddd");
        testData.add("eee");
        testData.add("fff");
        testData.add("ggg");
        testData.add("hhh");
        testData.add("iii");
        testData.add("jjj");
        testData.add("kkk");
        testData.add("lll");
        testData.add("mmm");
        testData.add("nnn");
        testData.add("ooo");
        testData.add("ppp");
        testData.add("qqq");
        testData.add("rrr");
        testData.add("sss");
        testData.add("ttt");
        testData.add("uuu");
        testData.add("vvv");



        // 3. create an adapter
        ContactsAdapter adapter = new ContactsAdapter(testData);

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
