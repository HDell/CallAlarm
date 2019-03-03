package com.tuprojects.hd.callalarm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class DetailsHistoryFragment extends Fragment {

    String name;
    String number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.details_history_fragment, container, false);

        if(getActivity().getIntent().hasExtra("name")&&getActivity().getIntent().hasExtra("number")){
            name = getActivity().getIntent().getStringExtra("name");
            number = getActivity().getIntent().getStringExtra("number");

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.details_history_recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            DetailsHistoryAdapter adapter = new DetailsHistoryAdapter(getCallDetails());
            recyclerView.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
        }

        // 1. get a reference to recyclerView

        //private RecyclerView recyclerView;
        //recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // 2. set layoutManger


        // 3. create an adapter
        //ContactsAdapter adapter = new ContactsAdapter(getContext(), getAndroidContacts());

        // 4. set adapter
        //recyclerView.setAdapter(adapter);

        return rootView;
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

                //before adding, check the number against the database && against the contact and only add if the number is present there
                if (callLogData.getStrippedNumber().equals(number)) {
                    callDetails.add(callLogData);
                }
            }
            cursorCallDetails.close();
            List<CallLogData> callDetailsReversed = Lists.reverse(callDetails);
            return callDetailsReversed;
        }
        return callDetails;
    }

}
