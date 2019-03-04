package com.tuprojects.hd.callalarm;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "call_list.db"; //get the proper nomenclature if there is an sql naming convention
    private static final String TABLE_NAME = "call_list_table";

    private static final String COL0 = "id"; //integer, primary key
    private static final String COL1 = "stripped_phone_number"; //string
    private static final String COL2 = "last_call_datetime"; //date
    private static final String COL3 = "last_call_duration"; //integer, seconds
    private static final String COL4 = "calls_per_period"; //integer
    private static final String COL5 = "frequency"; //integer
    private static final String COL6 = "period"; //integer [1=day, 7=week, 30=month, 90=quarter]
    private static final String COL7 = "name"; //string

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1 + " TEXT, "
                + COL2 + " TEXT, "
                + COL3 + " INTEGER, "
                + COL4 + " INTEGER, "
                + COL5 + " INTEGER, "
                + COL6 + " INTEGER, "
                + COL7 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addData(String strippedNumber, int cpp, int freq, int per, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, strippedNumber);
        contentValues.put(COL2, cpp);
        contentValues.put(COL3, freq);
        contentValues.put(COL4, per);
        contentValues.put(COL7, name);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result==-1) { //negatively inserted data will be result in -1
            return false;
        } else {
            return true;
        }
    }

    public boolean removeData(String strippedNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, COL1 + " = ?", new String[]{strippedNumber});

        if (result==-1) { //negatively inserted data will be result in -1
            return false;
        } else {
            return true;
        }
    }

    public boolean hasContact(String strippedNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor contactsDBcursor = db.query(TABLE_NAME, null, COL1 + " =?", new String[]{strippedNumber}, null, null, null);

        if ((contactsDBcursor != null) && (contactsDBcursor.getCount() > 0)) { //check if Cursor (database) is empty after querying it
            contactsDBcursor.close();
            return true;
        } else  {
            contactsDBcursor.close();
            return false;
        }
    }

    public Cursor getCallListCursor() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<String> allStrippedNumbers = new ArrayList<>();

        try {
            return db.query(TABLE_NAME, null, null, null, null, null, null);
        } catch (Exception ex) {
            Log.e("Error on contact ", ex.getMessage());
        }

        return null;

    }

}
