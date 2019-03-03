package com.tuprojects.hd.callalarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/*
MODEL:

    This class will serve as an abstraction of the contact list data that is pulled from the phone.
    Ultimately the data will be formatted as such:

    [
        ("Call ID", "FirstName", "LastName", "Length of call", "Date of call", "Time of call", "Incoming vs. Outgoing"),
        ("Call ID", "FirstName", "LastName", "Length of call", "Date of call", "Time of call", "Incoming vs. Outgoing"),
        ...,
    ]

    [
        (Consider only looking at the last x number of calls in the users call log)
        - Look into the new Call Log Permission policy implemented by Android
    ]
 */

public class CallListDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "call_list.db"; //get the proper nomenclature if there is an sql naming convention
    private static final String TABLE_NAME = "call_list_table";

    private static final String COL0 = "ID";
    private static final String COL1 = "CONTACT NAME";
    private static final String COL2 = "NUMBER";
    private static final String COL3 = "LAST CALL TYPE";
    private static final String COL4 = "LAST CALL DURATION";
    private static final String COL5 = "LAST CALL DATE"; //how should this be broken up?

    public CallListDatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
