package com.tuprojects.hd.callalarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/*
VIEW
 */

public class MainActivity extends AppCompatActivity {

    //STATE

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {//listens for clicks

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {//click handler method
            switch (item.getItemId()) {
                case R.id.navigation_contacts:

                    return true;
                case R.id.navigation_call_list:

                    return true;
                case R.id.navigation_history:

                    return true;
            }
            return false;
        }
    };

    //BEHAVIOR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation); //syncs nav object w/ layout
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
