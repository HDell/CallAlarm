package com.tuprojects.hd.callalarm;

import android.content.Context;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

//Model to interface directly w/ database

//Query database (cursor) row by row
//Get a contactName and strippedNumber
//Instantiate an AndroidContact w/ the name and call get

public class CallListContact {

//    private static final String COL0 = "id"; //integer, primary key
//    private static final String COL1 = "stripped_phone_number"; //string
//    private static final String COL2 = "last_call_datetime"; //date
//    private static final String COL3 = "last_call_duration"; //integer, seconds
//    private static final String COL4 = "calls_per_period"; //integer
//    private static final String COL5 = "frequency"; //integer
//    private static final String COL6 = "period"; //integer [1=day, 7=week, 30=month, 90=quarter]

    private AndroidContact androidContact;
    private List<CallLogData> callLogData;

    //My test data
    private List<String> testDataNames = new ArrayList<>();
    private List<String> testDataFrequency = new ArrayList<>();
    private List<String> testDataHistory = new ArrayList<>();

    private List<List<String>> testData = new ArrayList<>();

    CallListContact(AndroidContact androidContact, List<CallLogData> callLogDataList){

        this.androidContact = androidContact;
        callLogData = callLogDataList;

        testDataNames.add("Ashley");
        testDataNames.add("Dad");
        testDataNames.add("Mom");

        testDataFrequency.add("Daily");
        testDataFrequency.add("Once every 3 weeks");
        testDataFrequency.add("Weekly");

        testDataHistory.add("Spoke for 19 min. yesterday");
        testDataHistory.add("Spoke 6 days ago");
        testDataHistory.add("Missed 1 call yesterday");

        testData.add(testDataNames);
        testData.add(testDataFrequency);
        testData.add(testDataHistory);

    }

    public AndroidContact getAndroidContact() {
        return androidContact;
    }

    public List<CallLogData> getCallLogData() {
        return callLogData;
    }

}
