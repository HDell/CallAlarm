package com.tuprojects.hd.callalarm;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

        //Fragments
    ContactsFragment contactsFragment = new ContactsFragment();
    CallListFragment callListFragment = new CallListFragment();
    HistoryFragment historyFragment = new HistoryFragment();

            //Manager Set Up
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Views (Widgets)
    TextView textView;
    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {//listens for clicks

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {//anonymous method called when a button (item) is pressed
            switch (item.getItemId()) {
                case R.id.navigation_contacts: // (item.getItemId() == R.id.navigation_contacts)

                    textView.setText("Contacts");

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    fragmentTransaction.replace(R.id.fragment_container, contactsFragment).addToBackStack(null).commit();

                    return true;
                    //Unique Fragment Text
                case R.id.navigation_call_list:

                    textView.setText("Call List");

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    fragmentTransaction.replace(R.id.fragment_container, callListFragment).addToBackStack(null).commit();

                    return true;
                    //Unique Fragment Text
                case R.id.navigation_history:

                    textView.setText("History");

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    fragmentTransaction.replace(R.id.fragment_container, historyFragment).addToBackStack(null).commit();

                    return true;
                    //Unique Fragment Text
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

        //Initializations
            //Views
        textView = (TextView) findViewById(R.id.textView); //is casting to TextView really redundant?
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation); //syncs nav object w/ layout

        //View Behavior
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            //Start Fragment on Contacts
            fragmentTransaction.add(R.id.fragment_container, contactsFragment).commit();
        }

    }


}
