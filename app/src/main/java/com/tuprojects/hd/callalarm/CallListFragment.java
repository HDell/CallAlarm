package com.tuprojects.hd.callalarm;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallListFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.call_list_fragment, container, false);
/*
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.call_list_recycler_view);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
*/
        /*
        // this is data fro recycler view
        ItemData itemsData[] = {
            new ItemData("Indigo", R.drawable.circle),
            new ItemData("Red", R.drawable.color_ic_launcher),
            new ItemData("Blue", R.drawable.indigo),
            new ItemData("Green", R.drawable.circle),
            new ItemData("Amber", R.drawable.color_ic_launcher),
            new ItemData("Deep Orange", R.drawable.indigo)
         };
         */
/*
        // 3. create an adapter
        MyAdapter mAdapter = new MyAdapter(itemsData);

        // 4. set adapter
        recyclerView.setAdapter(mAdapter);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
*/
        return rootView;
    }
}
