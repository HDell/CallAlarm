package com.tuprojects.hd.callalarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

/*
VIEW
 */


public class ActivityMain extends AppCompatActivity {

    //STATE

    //Necessary to ensure that the screen loads contacts once permission is granted
    Boolean restart = true;

    //Solution to direct UI to Call List (0) or Navigation History (1)
    int navScreen;

        //Fragments (classes I created)
    FragmentContacts fragmentContacts = new FragmentContacts();
    FragmentCallList fragmentCallList = new FragmentCallList();
    FragmentHistory fragmentHistory = new FragmentHistory();

            //Manager Set Up (imported)
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Views (Widgets)
    //anonymous class used to switch screens as user clicks navigation buttons
    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {//listens for clicks

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {//anonymous method called when a button (item) is pressed
            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_contacts: // (item.getItemId() == R.id.navigation_contacts)

                    navScreen = 0;
                    restart = false;

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    fragmentTransaction.replace(R.id.fragment_container, fragmentContacts).commit();

                    return true;
                case R.id.navigation_call_list:

                    navScreen = 1;
                    restart = false;

                    if (selfPermissionFalseCheck(Manifest.permission.READ_CALL_LOG)) { //Call List requires call log permission
                        ActivityCompat.requestPermissions(ActivityMain.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1); //request permission
                    } else {
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        fragmentTransaction.replace(R.id.fragment_container, fragmentCallList).commit();
                    }

                    return true;
                case R.id.navigation_history:

                    navScreen = 2;
                    restart = false;

                    if (selfPermissionFalseCheck(Manifest.permission.READ_CALL_LOG)) { //History requires call log permission
                        ActivityCompat.requestPermissions(ActivityMain.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1); //request permission
                    } else {
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        fragmentTransaction.replace(R.id.fragment_container, fragmentHistory).commit();
                    }

                    return true;
            }
            return false;
        }
    };

    //BEHAVIOR
    @Override
    protected void onCreate(Bundle savedInstanceState) { //When Activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (selfPermissionFalseCheck(Manifest.permission.READ_CONTACTS)) { //Check if Read Contacts permission has NOT been granted
            ActivityCompat.requestPermissions(ActivityMain.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else { //If permission has been granted, move on with the rest of the onCreate() implementation
            //Initializations
            //Views (base class for widgets) - see activity_main.xml
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation); //findViewById syncs nav object w/ layout
                //findViewById doesn't necessarily return a BottomNavigationView object (which is why it must be type casted)

            //View Behavior
            navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener); //sets listener for the bottom nav

            if (getIntent().getExtras() != null) { //Based on if ActivityMain is navigated to via another (Details) Activity
                navScreen = getIntent().getExtras().getInt("navScreen"); //0, 1, or 2 determines which Fragment is selected on ActivityMain
                if (navScreen == 0) {
                    navigation.setSelectedItemId(R.id.navigation_contacts);
                } else if (navScreen == 1) {
                    navigation.setSelectedItemId(R.id.navigation_call_list);
                } else { //navScreen == 2
                    navigation.setSelectedItemId(R.id.navigation_history);
                }
            } else {
                //Start Fragment on Contacts Screen (as default)
                fragmentTransaction.add(R.id.fragment_container, fragmentContacts).commit();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: { //only one case; curly braces used to visually separate the return statement below
                //General permission check
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Specifically checks for READ_CONTACTS permission
                    if (ContextCompat.checkSelfPermission(ActivityMain.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(this, "Contacts permission granted!", Toast.LENGTH_SHORT).show(); //To Be Removed, replace w/ log

                        //Restarts Activity if permission has been granted for the first time
                        if(restart) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }

                    }

                    //Specifically checks for READ_CALL_LOG permission
                    if (ContextCompat.checkSelfPermission(ActivityMain.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(this, "Call Log permission granted!", Toast.LENGTH_SHORT).show(); //To Be Removed, replace w/ log

                        //Directs UI to appropriate screen
                        if (navScreen == 0) {
                            fragmentTransaction.replace(R.id.fragment_container, fragmentContacts).addToBackStack(null).commit();
                        } else if (navScreen == 1) {
                            fragmentTransaction.replace(R.id.fragment_container, fragmentCallList).addToBackStack(null).commit();
                        } else { //navScreen == 2
                            fragmentTransaction.replace(R.id.fragment_container, fragmentHistory).addToBackStack(null).commit();
                        }
                    }

                } else {

                    Toast.makeText(this, "Permission denied !", Toast.LENGTH_SHORT).show();

                }
            }
            return;
        }
    }

    public boolean selfPermissionFalseCheck(String manifest) {
        return (ContextCompat.checkSelfPermission(ActivityMain.this, manifest) != PackageManager.PERMISSION_GRANTED);
    }

}
