package com.tuprojects.hd.callalarm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentDetailsFrequency extends Fragment {

    public static final String TAG = "DetailsFreqFragment";

    //Declarations
    DatabaseHelper contactDB;

    String name;
    String strippedContactNumber;
    ConstraintLayout parentLayout;
    CheckBox checkBox;
    boolean removed = false;

    EditText callPerPeriod;
    EditText callFrequency;

    TextView cppText;
    TextView freqText;
    TextView nextCallText;

    RadioGroup callPeriods;
    RadioButton days;
    RadioButton weeks;
    RadioButton months;
    RadioButton quarters;

    int cpp, freq, per, intervalHr, intervalMin;

    String lastCallDate;
    String nextCallDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.details_frequency_fragment, container, false);

        //Initializations
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

        cppText = parentLayout.findViewById(R.id.text_calls_per_period);
        freqText = parentLayout.findViewById(R.id.text_frequency);
        nextCallText = parentLayout.findViewById(R.id.text_next_call);


        //Check if contact is in database
        if(contactDB.hasContact(strippedContactNumber)){
            //Set the box to checked if the contact is in db
            checkBox.setChecked(true);
            //
            Cursor cursor = contactDB.getCallListCursor();
            while(cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("stripped_phone_number")).equals(strippedContactNumber)) {
                    cpp = Integer.parseInt(cursor.getString(cursor.getColumnIndex("calls_per_period")));
                    callPerPeriod.setText(""+cpp);
                    setCallsPerPeriodText();

                    freq = Integer.parseInt(cursor.getString(cursor.getColumnIndex("frequency")));
                    callFrequency.setText(""+freq);
                    setCallFrequencyText();

                    if (cursor.getString(cursor.getColumnIndex("period")).equals("1")) {
                        per = 1;
                        days.setChecked(true);
                    } else if (cursor.getString(cursor.getColumnIndex("period")).equals("7")) {
                        per = 7;
                        weeks.setChecked(true);
                    } else if (cursor.getString(cursor.getColumnIndex("period")).equals("30")) {
                        per = 30;
                        months.setChecked(true);
                    } else if (cursor.getString(cursor.getColumnIndex("period")).equals("90")) {
                        per = 90;
                        quarters.setChecked(true);
                    }

                    lastCallDate = cursor.getString(cursor.getColumnIndex("last_call_datetime"));
                    nextCallDate = cursor.getString(cursor.getColumnIndex("next_call_datetime"));
                    Log.d(TAG, "In DB, Last Call DateTime: "+lastCallDate);
                    Log.d(TAG, "In DB, Next Call DateTime: "+nextCallDate);
                    nextCallText.setText("Reminder: "+nextCallDate);
                }
            }
            cursor.close();
            contactDB.closeReadableDatabase(); //prevents memory leaks
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_history_container, fragmentDetailsHistory).commit();
                //This line was causing the bottom navigation view to rise.
                //Added android:windowSoftInputMode="adjustPan" to manifest and android:imeOptions="actionDone" to EditText to fix this.
        } else {
            //Set the box to unchecked if the contact isn't in db
            checkBox.setChecked(false);
            removed = true;

        }

        //Listen for changes to EditText
            //Calls Per Period
        callPerPeriod.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try {
                    cpp = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    if (removed) {
                        cpp = 0;
                    } else {
                        cpp = 1; //Default is 1
                    }
                }

                setCallsPerPeriodText();
                if (lastCallDate!=null) {
                    updateInterval();
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
            //Frequency
        callFrequency.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try {
                    freq = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    if (removed) {
                        freq = 0;
                    } else {
                        freq = 1; //Default is 1
                    }
                }

                setCallFrequencyText();
                if (lastCallDate!=null) {
                    updateInterval();
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
                        if (lastCallDate!=null) {
                            updateInterval();
                        }
                    }

                }
            }
        });

        //Listen for changes to Checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    //Set Default Settings if Null
                    if (callPerPeriod.getText().toString().equals("")||callPerPeriod.getText().toString().equals("0")) {
                        callPerPeriod.setText("1");
                        cpp = 1;
                    } else {
                        cpp = Integer.parseInt(callPerPeriod.getText().toString());
                        Log.d(TAG, "CPP: "+cpp);
                    }

                    if (callFrequency.getText().toString().equals("")||callFrequency.getText().toString().equals("0")) {
                        callFrequency.setText("1");
                        freq = 1;
                    } else {
                        freq = Integer.parseInt(callFrequency.getText().toString());
                        Log.d(TAG, "Freq: "+freq);
                    }

                    if (!(days.isChecked()||weeks.isChecked()||months.isChecked()||quarters.isChecked())) {
                        weeks.setChecked(true);
                        per = 7;
                    }

                    //Add contact to database
                    List<CallLogData> callDetails = getCallDetails();

                    boolean insertData;
                    try {
                        lastCallDate = callDetails.get(0).getDate();
                        Log.d(TAG, "Last Call Log - Date: "+lastCallDate);
                        int duration = callDetails.get(0).getDurationInSeconds();
                        insertData = contactDB.addData(strippedContactNumber, lastCallDate, duration, cpp, freq, per, name);
                        Log.d(TAG, "Data/Duration Insert");
                    } catch (IndexOutOfBoundsException e) {
                        lastCallDate = new SimpleDateFormat("MM-dd-yy hh:mm a").format(Calendar.getInstance().getTime());
                        Log.d(TAG, "No Call Log - Date: "+lastCallDate);
                        insertData = contactDB.addData(strippedContactNumber, lastCallDate, 0, cpp, freq, per, name);
                        Log.d(TAG, "No History Insert");
                    }

                    if (insertData) {
                        updateInterval(); //Won't be called until lastCallDate is set.
                        removed = false;
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
                        removed = true;
                        lastCallDate = null;
                        cpp = 0;
                        freq = 0;
                        callPerPeriod.getText().clear();
                        callFrequency.getText().clear();
                        callPeriods.clearCheck();
                        nextCallText.setText("Reminder: Not Scheduled");
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

    private List<CallLogData> getCallDetails() {
        List<CallLogData> callDetails = new ArrayList<>();
        //Because this check is required, it may be redundant to check for this in the onClickListener in ActivityMain
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {
            Cursor cursorCallDetails = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            cursorCallDetails.moveToPosition(-1);//Ensures that entire call log is traversed
            int numberIndex = cursorCallDetails.getColumnIndex(CallLog.Calls.NUMBER);
            int callTypeIndex = cursorCallDetails.getColumnIndex(CallLog.Calls.TYPE);
            int callDurationIndex = cursorCallDetails.getColumnIndex(CallLog.Calls.DURATION); //Returns Duration in seconds

            int date = cursorCallDetails.getColumnIndex(CallLog.Calls.DATE);
            while (cursorCallDetails.moveToNext()) {

                //Initializations
                String name = cursorCallDetails.getString(cursorCallDetails.getColumnIndex(CallLog.Calls.CACHED_NAME));

                String number = cursorCallDetails.getString(numberIndex);
                String callType = cursorCallDetails.getString(callTypeIndex);
                String callDate = cursorCallDetails.getString(date);
                String callDuration = cursorCallDetails.getString(callDurationIndex);

                //Call Type Formatting
                String callTypeString = null;
                int callTypeCode = Integer.parseInt(callType);
                switch (callTypeCode) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callTypeString = "Incoming (Received)";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callTypeString = "Outgoing";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callTypeString = "Incoming (Missed)";
                        break;
                    case CallLog.Calls.REJECTED_TYPE:
                        callTypeString = "Incoming (Rejected)";
                        break;
                    case CallLog.Calls.BLOCKED_TYPE:
                        callTypeString = "Incoming (Blocked)";
                        break;
                }

                CallLogData callLogData = new CallLogData(name, number, callTypeString, callDate, callDuration);

                //before adding, check the number against the contact and only add if the number is present there
                if (callLogData.getStrippedNumber().equals(strippedContactNumber)) {
                    callDetails.add(callLogData);
                }
            }
            cursorCallDetails.close();
            List<CallLogData> callDetailsReversed = Lists.reverse(callDetails);
            return callDetailsReversed;
        }
        return callDetails;
    }

    private void setCallsPerPeriodText() {
        if(cpp==1){
            cppText.setText("Once");
        } else if(cpp==2) {
            cppText.setText("Twice");
        } else {
            cppText.setText(cpp+" Times");
        }
    }

    private void setCallFrequencyText() {
        if(freq==1){
            freqText.setText("Every");
        } else if(freq==2) {
            freqText.setText("Every Other");
        } else if (freq==0) {
            freqText.setText("N/A");
        } else {
            freqText.setText("Every "+freq);
        }
    }

    private void updateInterval() {
        if(cpp==0) {
            //contactDB.updateInterval(0,"", strippedContactNumber);
            Toast.makeText(getContext(), "Calls Per Period must be greater than 0!", Toast.LENGTH_SHORT).show();
        } else {
            intervalHr = ((per * 24) * freq) / cpp;
            intervalMin = (((per * 24 * 60) * freq) / cpp) % 60;

            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new SimpleDateFormat("MM-dd-yy hh:mm a").parse(lastCallDate)); //Sets cal to time of 'last' call
                calendar.add(Calendar.HOUR_OF_DAY, intervalHr); //Adds the intervalHr to the calendar for the dateTime of next call
                calendar.add(Calendar.MINUTE, intervalMin);
                if (!calendar.before(Calendar.getInstance())) {
                    nextCallDate = new SimpleDateFormat("MM-dd-yy hh:mm a").format(calendar.getTime());
                } else {
                    calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR_OF_DAY, intervalHr); //Adds the intervalHr to the calendar for the dateTime of next call
                    calendar.add(Calendar.MINUTE, intervalMin);
                    nextCallDate = new SimpleDateFormat("MM-dd-yy hh:mm a").format(calendar.getTime());
                }
                boolean updatedInterval = contactDB.updateInterval(intervalHr, intervalMin, nextCallDate, strippedContactNumber);
                if (updatedInterval) {
                    Log.d(TAG, "Interval Hr Updated to: "+intervalHr);
                    Log.d(TAG, "Interval Min Updated to: "+intervalMin);
                    Log.d(TAG, "New Call Reminder DateTime: "+nextCallDate);
                    nextCallText.setText("Reminder: "+nextCallDate);
                } else {
                    Log.d(TAG, "Something went wrong!");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
