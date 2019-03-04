package com.tuprojects.hd.callalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/*
    From this screen, users will have the ability to:
        - add and remove contacts to and from the call list
        - view and update call frequency settings
        - view call history (along with duration)

 */

//Activity which will contain a Fragment for each individual Contact
public class ActivityDetails extends AppCompatActivity {

    FragmentDetailsDescription fragmentDetailsDescription = new FragmentDetailsDescription();
    FragmentDetailsFrequency fragmentDetailsFrequency = new FragmentDetailsFrequency();
    FragmentDetailsHistory fragmentDetailsHistory = new FragmentDetailsHistory();

    //Manager Set Up
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default Methods
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Initializations
        //Views
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.focus_navigation); //syncs nav object w/ layout
        //Intent
        final Intent intent = new Intent(this, ActivityMain.class);
        //Listener - instantiated in onCreate in order to pass intent extras
        BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {//listens for clicks

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {//anonymous method called when a button (item) is pressed

                switch (item.getItemId()) {
                    case R.id.navigation_contacts: // (item.getItemId() == R.id.navigation_contacts)

                        intent.putExtra("navScreen", 0);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_call_list:

                        intent.putExtra("navScreen", 1);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_history:

                        intent.putExtra("navScreen", 2);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        };

        //View Behavior
        navigation.getMenu().setGroupCheckable(0, false, true); //no menu color
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        getIncomingIntent();

    }

    private void getIncomingIntent() {

        if (getIntent().hasExtra("contactName")&&getIntent().hasExtra("strippedContactNumber")&&getIntent().hasExtra("contactNumber")) { //No need to check for these in the fragments

            fragmentTransaction.add(R.id.fragment_description_container, fragmentDetailsDescription);
            fragmentTransaction.add(R.id.fragment_frequency_container, fragmentDetailsFrequency);
            //the details history fragment is instantiated from within the details frequency fragment

            fragmentTransaction.commit();


        }

    }

}
