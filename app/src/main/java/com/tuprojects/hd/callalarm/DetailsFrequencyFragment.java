package com.tuprojects.hd.callalarm;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class DetailsFrequencyFragment extends Fragment {

    public static final String TAG = "DetailsFreqFragment";

    DatabaseHelper contactDB;

    String name;
    String strippedContactNumber;
    ConstraintLayout parentLayout;
    CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.details_frequency_fragment, container, false);

        parentLayout = rootView.findViewById(R.id.details_frequency_layout);

        contactDB = new DatabaseHelper(getContext()); //have this interface w/ StoredCallList instead of database in the future

        //Remove these hasExtra conditionals later. This check is already done in the parent Activity.
        if(getActivity().getIntent().hasExtra("contactName")&&getActivity().getIntent().hasExtra("strippedContactNumber")){
            name = getActivity().getIntent().getStringExtra("contactName");
            strippedContactNumber = getActivity().getIntent().getStringExtra("strippedContactNumber");
            checkBox = parentLayout.findViewById(R.id.details_contacts_toggle);

            //Check if contact is in database
                //Set the box to checked if the contact is in db
                if(contactDB.hasContact(strippedContactNumber)){
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                //Set the box to unchecked if the contact isn't in db

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (isChecked) {
                        //Add contact to database
                        boolean insertData = contactDB.addData(strippedContactNumber, 0, 0, 0);
                        if (insertData) {
                            Log.d(TAG, "Successfully inserted data.");
                            Toast.makeText(getContext(), "Successfully added "+name+" to database!", Toast.LENGTH_SHORT).show();
                        } else { //insertData = false
                            Log.d(TAG, "Failed to insert data.");
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {//button is NOT checked
                        //Remove contact from database
                        boolean removeData = contactDB.removeData(strippedContactNumber);
                        if (removeData) {
                            Log.d(TAG, "Successfully removed data.");
                            Toast.makeText(getContext(), "Successfully removed "+name+" from database!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Failed to remove data.");
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        return rootView;
    }

}
