package com.tuprojects.hd.callalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //variables
    private TextView mTextMessage;

        //variable as anonymous class
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {//listens for clicks

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {//click handler method
            switch (item.getItemId()) {
                case R.id.navigation_contacts:
                    mTextMessage.setText(R.string.title_contacts);
                    return true;
                case R.id.navigation_call_list:
                    mTextMessage.setText(R.string.title_call_list);
                    //Intent callListIntent = new Intent(MainActivity.this, CallListActivity.class);
                    //startActivity(callListIntent);
                    return true;
                case R.id.navigation_history:
                    mTextMessage.setText(R.string.title_history);
                    //Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                    //startActivity(historyIntent);
                    return true;
            }
            return false;
        }
    };

    //Main function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message); //creates "Contacts" text view (sync w/ layout)
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation); //syncs nav object w/ layout
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
