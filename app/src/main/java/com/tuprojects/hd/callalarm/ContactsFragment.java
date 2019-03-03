package com.tuprojects.hd.callalarm;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.contacts_fragment, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.contacts_recycler_view);
        //private RecyclerView recyclerView;
        //recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // 2. set layoutManger
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // 3. create an adapter
        ContactsAdapter adapter = new ContactsAdapter(getAndroidContacts());

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }

    //Should this be in another file? (Should the coupling be loosened?)
    public class AndroidContact {
        private String contactName = "";
        private String contactPhoneNum = "";
        private int contactID = 0;

        public AndroidContact(String contactName) {
            this.contactName = contactName;
        }

        public String getName() {
            return contactName;
        }
    }

    private List<AndroidContact> getAndroidContacts() {
        List<AndroidContact> androidContactList = new ArrayList<AndroidContact>();

        //Initialize Cursor w/ connection to Contacts (Content) Provider
        Cursor cursorAndroidContacts = null; //learn CursorLoader; confused on difference between Cursor & CursorLoader
        ContentResolver contentResolver = getContext().getContentResolver();
        try {

            //content://contacts_contract/contacts -> ContactsContracts=authority, Contacts=table path, CONTENT_URI contains URI to contacts table
            //Cursor will now hold the data from the Contacts table
            cursorAndroidContacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");

        } catch (Exception ex) {
            Log.e("Error on contact ", ex.getMessage());
        }

        if (cursorAndroidContacts.getCount()>0) {

            while (cursorAndroidContacts.moveToNext()) { //looks at each row of cursor

                //Note: this is how you use the cursor to read data from a content provider
                    //cursor.getString(index) <- (w/ respect to the current line [see loop])
                AndroidContact androidContact = new AndroidContact(cursorAndroidContacts.getString(cursorAndroidContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                //String contactID = cursorAndroidContacts.getString(cursorAndroidContacts.getColumnIndex(ContactsContract.Contacts._ID));

                //Handle phone number here (to-do LATER)

                //Build AndroidContacts ArrayList
                androidContactList.add(androidContact);
            }

        }

        cursorAndroidContacts.close();
        return androidContactList;

    }

}
