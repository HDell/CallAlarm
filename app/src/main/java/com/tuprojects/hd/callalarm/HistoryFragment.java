package com.tuprojects.hd.callalarm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.history_fragment, container, false);

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
        HistoryAdapter adapter = new HistoryAdapter(getCallDetails());

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }

    public class CallLogData {
        private String name = "";
        private String number = "";
        private String strippedNumber = "";
        private String type = "";

        private String date = "";
        private String formattedDate = "";

        private String duration = "";
        private int durationInt;
        private int seconds = 0;
        private int minutes = 0;
        private int hours = 0;

        public CallLogData(String name, String number, String type, String date, String duration) {

            if (name == null){
                this.name = number;
            } else {
                this.name = name;
            }

            this.number = number;

            for (int i = 0; i < number.length(); i++) {
                String character = "";
                character += this.number.charAt(i);

                try {
                    Integer.parseInt(character);
                    strippedNumber += character;
                } catch (NumberFormatException e) {
                    //do nothing
                }

            }

            if (type.equals("Outgoing") && Integer.parseInt(duration) <= 5){ //proxy for missed outgoing calls until better method is found
                this.type = type+" (Missed)";
            } else {
                this.type = type;
            }

            this.date = date;

            //Date Formatting
            formattedDate = new SimpleDateFormat("MM-dd-yy hh:mm a").format(new Date(Long.valueOf(date)));

            this.duration = duration;

            durationInt = Integer.parseInt(duration);
            seconds = durationInt%60;
            minutes = durationInt/60;
            if(minutes>=60) {
                hours = minutes/60;
                minutes = minutes%60;
            }
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public String getStrippedNumber() {
            return strippedNumber;
        }

        public String getType() {
            return type;
        }

        public String getDuration() { //formatted
            if (hours>0) {
                return hours + " hrs., " + minutes + " min., " + seconds + " sec.";
            } else if (minutes>0) {
                return minutes + " min., " + seconds + " sec.";
            } else {
                return seconds + " sec.";
            }
        }

        public String getDate() {
            return formattedDate;
        }
    }

    private List<CallLogData> getCallDetails() {
        List<CallLogData> callDetails = new ArrayList<>();
        //Because this check is required, it may be redundant to check for this in the onClickListener in MainActivity
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {
            Cursor cursorCallDetails = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
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

                //before adding, check the number against the database and only add if the number is present there

                callDetails.add(callLogData);
            }
            cursorCallDetails.close();
            List<CallLogData> callDetailsReversed = Lists.reverse(callDetails);
            return callDetailsReversed;
        }
        return callDetails;
    }


}
