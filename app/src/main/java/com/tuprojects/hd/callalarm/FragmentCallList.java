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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class FragmentCallList extends Fragment {

    public static final String TAG = "FragmentCallList";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.call_list_fragment, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.call_list_recycler_view);
        //private RecyclerView recyclerView;
        //recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // 2. set layoutManger
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this); //this assumes that we're creating directly from Activity
        //recyclerView.setLayoutManager(layoutManager || RecyclerView.LayoutManager);

        //My test data
        /*ArrayList<String> testDataNames = new ArrayList<>();
        testDataNames.add("Ashley");
        testDataNames.add("Dad");
        testDataNames.add("Mom");

        List<String> testDataFrequency = new ArrayList<>();
        testDataFrequency.add("Daily");
        testDataFrequency.add("Once every 3 weeks");
        testDataFrequency.add("Weekly");

        List<String> testDataHistory = new ArrayList<>();
        testDataHistory.add("Spoke for 19 min. yesterday");
        testDataHistory.add("Spoke 6 days ago");
        testDataHistory.add("Missed 1 call yesterday");

        List<List<String>> testData = new ArrayList<>();
        testData.add(testDataNames);
        testData.add(testDataFrequency);
        testData.add(testDataHistory);*/

        // 3. create an adapter
        AdapterCallList adapter = new AdapterCallList(getContext(), getCallList());

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }

    private List<CallListContact> getCallList(){
        List<CallListContact> callListContacts = new ArrayList<CallListContact>();

        AndroidContact androidContact;
        List<CallLogData> callLogDataList = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper(getContext());

        Cursor callListCursor = db.getCallListCursor();

        if ((callListCursor != null) && (callListCursor.getCount() > 0)) {
            while (callListCursor.moveToNext()) { //check if Cursor (database) is empty after querying it

                //allStrippedNumbers.add(contactsDBcursor.getString(contactsDBcursor.getColumnIndex(COL1)));
                androidContact = new AndroidContact(callListCursor.getString(callListCursor.getColumnIndex("name")));
                androidContact.addPhoneNumber(callListCursor.getString(callListCursor.getColumnIndex("stripped_phone_number")));
                callLogDataList = getCallDetails(callListCursor);
                callListContacts.add(new CallListContact(androidContact, callLogDataList));
             }
        }

        callListCursor.close();
        return callListContacts;

    }

    private List<CallLogData> getCallDetails(Cursor cursor) {
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
                //Log.d(TAG, "Before return: "+callLogData.getStrippedNumber());

                //Log.d(TAG, "Compare against: "+cursor.getString(cursor.getColumnIndex("stripped_phone_number")));

                //before adding, check the number against the database && against the contact and only add if the number is present there
                if (callLogData.getStrippedNumber().equals(cursor.getString(cursor.getColumnIndex("stripped_phone_number")))) {
                    callDetails.add(callLogData);
                    //Log.d(TAG, "Proving size: "+callDetails.get(0).getStrippedNumber());
                }
            }
            cursorCallDetails.close();

            return Lists.reverse(callDetails);
        }
        return callDetails;
    }
}
