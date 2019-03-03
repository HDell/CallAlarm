package com.tuprojects.hd.callalarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailsFrequencyFragment extends Fragment {

    String name;
    String contactNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.details_frequency_fragment, container, false);

        if(getActivity().getIntent().hasExtra("name")&&getActivity().getIntent().hasExtra("number")){
            name = getActivity().getIntent().getStringExtra("name");
            contactNumber = getActivity().getIntent().getStringExtra("number");
        }

        return rootView;
    }

}
