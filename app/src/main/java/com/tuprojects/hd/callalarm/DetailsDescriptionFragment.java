package com.tuprojects.hd.callalarm;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsDescriptionFragment extends Fragment {

    public static final String TAG = "DetailsDescFragment";

    String name;
    String contactNumber;
    ConstraintLayout parentLayout;
    TextView textViewName;
    TextView textViewNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.details_description_fragment, container, false);

        parentLayout = rootView.findViewById(R.id.details_description_layout);

        if(getActivity().getIntent().hasExtra("contactName")&&getActivity().getIntent().hasExtra("contactNumber")){
            Log.d(TAG, "Successfully passed intent to Fragment.");
            name = getActivity().getIntent().getStringExtra("contactName");
            contactNumber = getActivity().getIntent().getStringExtra("contactNumber");
            textViewName = (TextView) parentLayout.findViewById(R.id.details_contacts_name);
            textViewNumber = (TextView) parentLayout.findViewById(R.id.details_contacts_number);
            textViewName.setText(name);
            textViewNumber.setText(contactNumber);
        }

        return rootView;
    }

}
