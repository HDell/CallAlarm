package com.tuprojects.hd.callalarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
VIEW
 */

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

public class MainActivity extends AppCompatActivity {

    //STATE

    //Necessary to ensure that the screen loads contacts once permission is granted
    Boolean restart = true;

    //Solution to direct UI to Call List (0) or Navigation History (1)
    int navScreen;

        //Fragments
    ContactsFragment contactsFragment = new ContactsFragment();
    CallListFragment callListFragment = new CallListFragment();
    HistoryFragment historyFragment = new HistoryFragment();

            //Manager Set Up
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Views (Widgets)
    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {//listens for clicks

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {//anonymous method called when a button (item) is pressed
            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_contacts: // (item.getItemId() == R.id.navigation_contacts)

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    fragmentTransaction.replace(R.id.fragment_container, contactsFragment).addToBackStack(null).commit();

                    return true;
                case R.id.navigation_call_list:

                    navScreen = 0;
                    restart = false;

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALL_LOG)) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                        }
                    } else {
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        fragmentTransaction.replace(R.id.fragment_container, callListFragment).addToBackStack(null).commit();
                    }

                    return true;
                case R.id.navigation_history:

                    navScreen = 1;
                    restart = false;

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALL_LOG)) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                        }
                    } else {
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        fragmentTransaction.replace(R.id.fragment_container, historyFragment).addToBackStack(null).commit();
                    }

                    return true;
            }
            return false;
        }
    };

    //BEHAVIOR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default Methods
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        } else {
            //Initializations
            //Views
            //is casting to BottomNavigationView really redundant?
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation); //syncs nav object w/ layout

            //View Behavior
            navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

            //Start Fragment on Contacts
            fragmentTransaction.add(R.id.fragment_container, contactsFragment).commit();

            /* (Not sure about this method)
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.fragment_container) != null) {

                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }


            }*/
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: { //curly braces used to visually separate the return statement below
                //General permission check
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Specifically checks for READ_CONTACTS permission
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(this, "Contacts permission granted!", Toast.LENGTH_SHORT).show();

                        //Restarts Activity if permission has been granted for the first time
                        if(restart) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }

                    }

                    //Specifically checks for READ_CALL_LOG permission
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(this, "Call Log permission granted!", Toast.LENGTH_SHORT).show();

                        //Directs UI to appropriate screen
                        if (navScreen == 0) {
                            fragmentTransaction.replace(R.id.fragment_container, callListFragment).addToBackStack(null).commit();
                        } else { //navScreen == 1
                            fragmentTransaction.replace(R.id.fragment_container, historyFragment).addToBackStack(null).commit();
                        }
                    }

                } else {

                    Toast.makeText(this, "Permission denied !", Toast.LENGTH_SHORT).show();

                }
            }
            return;
        }
    }


}
