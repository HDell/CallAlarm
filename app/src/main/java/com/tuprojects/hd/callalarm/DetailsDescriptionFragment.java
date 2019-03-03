package com.tuprojects.hd.callalarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailsDescriptionFragment extends Fragment {

    String name;
    String number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.details_description_fragment, container, false);

        // 1. get a reference to recyclerView
        // RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.contacts_recycler_view);

        if(getActivity().getIntent().hasExtra("name")&&getActivity().getIntent().hasExtra("number")){
            name = getActivity().getIntent().getStringExtra("name");
            number = getActivity().getIntent().getStringExtra("number");
        }

        // 2. set layoutManger
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.setLayoutManager(layoutManager);

        // 3. create an adapter
        //ContactsAdapter adapter = new ContactsAdapter(getContext(), getAndroidContacts());

        // 4. set adapter
        //recyclerView.setAdapter(adapter);

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }

}
