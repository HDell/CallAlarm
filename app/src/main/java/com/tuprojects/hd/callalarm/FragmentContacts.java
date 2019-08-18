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

public class FragmentContacts extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.contacts_fragment, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.contacts_recycler_view);

        // 2. set layoutManger
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // 3. create an adapter
        AdapterContacts adapter = new AdapterContacts(getContext(), getAndroidContacts());

        // 4. set adapter
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }

    private List<AndroidContact> getAndroidContacts() {
        List<AndroidContact> androidContactList = new ArrayList<>();

        //Initialize Cursor w/ connection to Contacts (Content) Provider
        Cursor cursorAndroidContacts = null; //learn CursorLoader; confused on difference between Cursor & CursorLoader
        ContentResolver contentResolver = getContext().getContentResolver(); //ContestResolver, used to comm w/ Content Provider as a client
        try {

            //content://contacts_contract/contacts -> ContactsContracts=authority, Contacts=table path, CONTENT_URI contains URI to contacts table
            //Cursor will now hold the data from the Contacts table
            cursorAndroidContacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");

        } catch (Exception ex) {
            Log.e("Error on contact ", ex.getMessage());
        }

        if (cursorAndroidContacts.getCount() > 0) {

            while (cursorAndroidContacts.moveToNext()) { //looks at each row of android contacts in cursor at a time

                //Note: this is how you use the cursor to read data from a content provider
                    //cursor.getString(index) <- (w/ respect to the current line [see loop])
                AndroidContact androidContact = new AndroidContact(cursorAndroidContacts.getString(cursorAndroidContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))); //name from 1 row
                String contactID = cursorAndroidContacts.getString(cursorAndroidContacts.getColumnIndex(ContactsContract.Contacts._ID)); //ID from 1 row
                androidContact.setContactID(Integer.parseInt(contactID));

                //Handle phone number here
                int hasPhoneNumber = Integer.parseInt(cursorAndroidContacts.getString(cursorAndroidContacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))); //0 or less if no phone #s

                if (hasPhoneNumber > 0) {

                    //CommonDataKinds.Phone = table path
                    Cursor cursorPhoneNumbers = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactID}, null);

                    while (cursorPhoneNumbers.moveToNext()) { //looks at each row of contact's phone numbers in cursor at a time

                        String phoneNumber = cursorPhoneNumbers.getString(cursorPhoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        androidContact.addPhoneNumber(phoneNumber);

                    }

                    cursorPhoneNumbers.close();

                }

                //Build AndroidContacts ArrayList
                androidContactList.add(androidContact);
            }

        }

        cursorAndroidContacts.close();
        return androidContactList;

    }

}
