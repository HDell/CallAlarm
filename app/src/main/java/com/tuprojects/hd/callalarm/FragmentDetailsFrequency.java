package com.tuprojects.hd.callalarm;

import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FragmentDetailsFrequency extends Fragment {

    public static final String TAG = "DetailsFreqFragment";

    DatabaseHelper contactDB;

    String name;
    String strippedContactNumber;
    ConstraintLayout parentLayout;
    CheckBox checkBox;

    EditText callPerPeriod;
    EditText callFrequency;

    RadioGroup callPeriods;
    RadioButton days;
    RadioButton weeks;
    RadioButton months;
    RadioButton quarters;

    int cpp, freq, per, interval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.details_frequency_fragment, container, false);

        parentLayout = rootView.findViewById(R.id.details_frequency_layout);

        contactDB = new DatabaseHelper(getContext()); //have this interface w/ CallListContact instead of database in the future

        final FragmentDetailsHistory fragmentDetailsHistory = new FragmentDetailsHistory();

        name = getActivity().getIntent().getStringExtra("contactName");
        strippedContactNumber = getActivity().getIntent().getStringExtra("strippedContactNumber");

        checkBox = parentLayout.findViewById(R.id.details_contacts_toggle);

        callPerPeriod = parentLayout.findViewById(R.id.calls_per_period);
        callFrequency = parentLayout.findViewById(R.id.call_frequency);

        callPeriods = parentLayout.findViewById(R.id.periods);
        days = parentLayout.findViewById(R.id.period_day);
        weeks = parentLayout.findViewById(R.id.period_week);
        months = parentLayout.findViewById(R.id.period_month);
        quarters = parentLayout.findViewById(R.id.period_quarter);

        //Check if contact is in database
        if(contactDB.hasContact(strippedContactNumber)){
            //Set the box to checked if the contact is in db
            checkBox.setChecked(true);
            //
            Cursor cursor = contactDB.getCallListCursor();
            while(cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("stripped_phone_number")).equals(strippedContactNumber)) {
                    callPerPeriod.setText(cursor.getString(cursor.getColumnIndex("calls_per_period")));
                    callFrequency.setText(cursor.getString(cursor.getColumnIndex("frequency")));

                    Log.d(TAG, "The mistake is here.");
                    if (cursor.getString(cursor.getColumnIndex("period")).equals("1")) {
                        days.setChecked(true);
                    } else if (cursor.getString(cursor.getColumnIndex("period")).equals("7")) {
                        weeks.setChecked(true);
                    } else if (cursor.getString(cursor.getColumnIndex("period")).equals("30")) {
                        months.setChecked(true);
                    } else if (cursor.getString(cursor.getColumnIndex("period")).equals("90")) {
                        quarters.setChecked(true);
                    }
                }
            }
            cursor.close();

            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_history_container, fragmentDetailsHistory).commit();
                //This line was causing the bottom navigation view to rise.
                //Added android:windowSoftInputMode="adjustPan" to manifest and android:imeOptions="actionDone" to EditText to fix this.
        } else {
            //Set the box to unchecked if the contact isn't in db
            checkBox.setChecked(false);
        }


        //Listen for changes to Period
        callPeriods.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked;
                try {
                    isChecked = checkedRadioButton.isChecked();
                } catch (NullPointerException e) {
                    isChecked = false;
                }
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {

                    if (checkedRadioButton.equals(days)){
                        per = 1;
                    } else if (checkedRadioButton.equals(weeks)){
                        per = 7;
                    } else if (checkedRadioButton.equals(months)){
                        per = 30;
                    } else if (checkedRadioButton.equals(quarters)){
                        per = 90;
                    }

                    Log.d(TAG, "New Period: "+per);
                    boolean updated = contactDB.updatePeriod(per, strippedContactNumber);
                    if(updated) {
                        Log.d(TAG, "Period successfully changed.");
                    }

                }
            }
        });

        //Listen for changes to EditText
        callPerPeriod.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try {
                    cpp = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    cpp = 0;
                }
                Log.d(TAG, "New CPP: "+cpp);
                boolean updated = contactDB.updateCallsPerPeriod(cpp, strippedContactNumber);
                if(updated) {
                    Log.d(TAG, "CPP successfully changed.");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        callFrequency.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try {
                    freq = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    freq = 0;
                }
                Log.d(TAG, "New Freq: " + freq);
                boolean updated = contactDB.updateFrequency(freq, strippedContactNumber);
                if(updated) {
                    Log.d(TAG, "Freq successfully changed.");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //Listen for changes to Checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    //Set Default Settings if Null
                    if (callPerPeriod.getText().toString().equals("")) {
                        callPerPeriod.setText("1");
                        cpp = 1;
                    } else {
                        cpp = Integer.parseInt(callPerPeriod.getText().toString());
                        Log.d(TAG, "CPP: "+cpp);
                    }

                    if (callFrequency.getText().toString().equals("")) {
                        callFrequency.setText("1");
                        freq = 1;
                    } else {
                        freq = Integer.parseInt(callFrequency.getText().toString());
                        Log.d(TAG, "Freq: "+freq);
                    }

                    if (!(days.isChecked()||weeks.isChecked()||months.isChecked()||quarters.isChecked())) {
                        days.setChecked(true);
                        per = 1;
                    }

                    //Add contact to database
                    boolean insertData = contactDB.addData(strippedContactNumber, cpp, freq, per, name);
                    if (insertData) {
                        Log.d(TAG, "Successfully inserted data.");
                        Toast.makeText(getContext(), "Successfully added "+name+" to database!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_history_container, fragmentDetailsHistory).commit();
                    } else { //insertData = false
                        Log.d(TAG, "Failed to insert data.");
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                } else {//button is NOT checked
                    //Remove contact from database
                    boolean removeData = contactDB.removeData(strippedContactNumber);
                    if (removeData) {
                        callPerPeriod.getText().clear();
                        callFrequency.getText().clear();
                        callPeriods.clearCheck();
                        Log.d(TAG, "Successfully removed data.");
                        Toast.makeText(getContext(), "Successfully removed "+name+" from database!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(fragmentDetailsHistory).commit();
                    } else {
                        Log.d(TAG, "Failed to remove data.");
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rootView;
    }

}
