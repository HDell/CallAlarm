package com.tuprojects.hd.callalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class TestActivity extends AppCompatActivity { //the Activity class is a subclass of Context

    public static final String EXTRA_MESSAGE = "com.tuprojects.hd.callalarm.MESSAGE"; //unique constant
    //also a field, which can be accessed via dot notation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) { //can be called anything as long as it is linked to the View onclick attribute in the layout
        Intent intent = new Intent(this, DisplayMessageActivity.class); //binding process
        /*Parameters:
        A Context as its first parameter (this is used because the Activity class is a subclass of Context)
        The Class of the app component to which the system should deliver the Intent (in this case, the activity that should be started)*/
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString(); //getText() returns the text that TextView is displaying as Editable object
        intent.putExtra(EXTRA_MESSAGE, message); //This package's message (key) is message (editText String value)
        /*
        Adds the EditText's value to the intent. An Intent can carry data types as key-value pairs called extras.
        Your key is a public constant EXTRA_MESSAGE because the next activity uses the key to retrieve the text value.
        It's a good practice to define keys for intent extras using your app's package name as a prefix.
        This ensures the keys are unique, in case your app interacts with other apps.
         */
        startActivity(intent);
        /*
        Starts an instance of the DisplayMessageActivity specified by the Intent. Now you need to create that class.
         */
    }

}
