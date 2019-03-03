package com.tuprojects.hd.callalarm;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//Interface between the (Contacts) data source and the AdapterView (ListView->RecyclerView) layout
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    public static final String TAG = "ContactsAdapter";


    //State
    private List<AndroidContact> dataset;
    private Context context;

    //Inner Class, ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //State
        public TextView textView;
        public ConstraintLayout parentLayout;

        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.contacts_data_holder);
            this.parentLayout = itemView.findViewById(R.id.contacts_layout);
        }

        /*
        public ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
        */
    }

    //Constructor
    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(Context context, List<AndroidContact> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    //Behavior

    //ViewHolder Behavior // Create new views (invoked by the layout manager) //NECESSARY
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager) //NECESSARY
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final AndroidContact androidContact = dataset.get(position);

        //String tempDetails = dataset.get(position).getStrippedPhoneNum(0) + "||" + dataset.get(position).getName();
        holder.textView.setText(dataset.get(position).getName()); //dataset.get(position).getName()

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Successfully Contact from Contacts List.");
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("contactName", androidContact.getName());
                intent.putExtra("strippedContactNumber", androidContact.getStrippedPhoneNum(0));
                intent.putExtra("contactNumber", androidContact.getPhoneNum(0));
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
