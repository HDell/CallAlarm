package com.tuprojects.hd.callalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // 1) Get the Intent that started this activity 2) and extract the string
        Intent intent = getIntent(); // 1)Activity method that gets the intent object that started this activity
        String message = intent.getStringExtra(TestActivity.EXTRA_MESSAGE); // 2)returns the (String) value from the key (argument)
            //returns null if no String value was found
            //plenty get[DataType]Extra(key) functions exist for the Intent class
        //in this case, the message is whatever is inputted into the editText from test_layout.xml

        // 1) Capture the layout's TextView and 2) set the string as its text
        TextView textView = findViewById(R.id.textView); // 1)
        textView.setText(message); // 2)
    }
}
