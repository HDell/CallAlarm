package com.tuprojects.hd.callalarm;

import android.content.Context;

import java.util.List;

//Model to interface directly w/ database

//Query database (cursor) row by row
//Get a contactName and strippedNumber
//Instantiate an AndroidContact w/ the name and call get

public class StoredCallList {

//    private static final String COL0 = "id"; //integer, primary key
//    private static final String COL1 = "stripped_phone_number"; //string
//    private static final String COL2 = "last_call_datetime"; //date
//    private static final String COL3 = "last_call_duration"; //integer, seconds
//    private static final String COL4 = "calls_per_period"; //integer
//    private static final String COL5 = "frequency"; //integer
//    private static final String COL6 = "period"; //integer [1=day, 7=week, 30=month, 90=quarter]

    Context context;
    private AndroidContact androidContact;
    private CallLogData lastCall;
    DatabaseHelper contactDB;


    /*StoredCallList(Context context){
    contactDB = new DatabaseHelper(getContext());
    }*/

}
