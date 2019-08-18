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
import android.widget.Toast;

import java.util.List;

public class AdapterCallList extends RecyclerView.Adapter<AdapterCallList.ViewHolder> {

    public static final String TAG = "AdapterCallList";

    //State
    private List<CallListContact> dataset;
    private Context context;

    //Inner Class, ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //State
        public TextView nameTextView;
        public TextView frequencyTextView;
        public TextView historyTextView;
        public ConstraintLayout parentLayout;

        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.call_list_name_holder);
            this.frequencyTextView = itemView.findViewById(R.id.call_frequency_data_holder);
            this.historyTextView = itemView.findViewById(R.id.call_history_data_holder);
            this.parentLayout = itemView.findViewById(R.id.call_list_layout);
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
    public AdapterCallList(Context context, List<CallListContact> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    //Behavior

    //ViewHolder Behavior // Create new views (invoked by the layout manager) //NECESSARY
    @Override
    public AdapterCallList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.call_list_recyclerview_row, parent, false);
        AdapterCallList.ViewHolder viewHolder = new AdapterCallList.ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager) //NECESSARY
    @Override
    public void onBindViewHolder(AdapterCallList.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final AndroidContact androidContact = dataset.get(position).getAndroidContact();

        Log.d(TAG, "DB Rows: "+dataset.size());
        //Log.d(TAG, ""+dataset.get(position).getCallLogData());

        String name = androidContact.getName();
        DatabaseHelper db = new DatabaseHelper(context);
        String frequency = db.getFrequencyDisplay(androidContact.getStrippedPhoneNum(0));
        db.closeReadableDatabase();
        db.close(); //is this close necessary?
        String history = "";
        try {
            history = dataset.get(position).getCallLogData().get(0).getDate() + ", " + dataset.get(position).getCallLogData().get(0).getDuration();
        } catch (IndexOutOfBoundsException e) {
            history = "No call history available";
        }

        holder.nameTextView.setText(name);
        holder.frequencyTextView.setText(frequency);
        holder.historyTextView.setText(history);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityDetails.class);
                DatabaseHelper db = new DatabaseHelper(context);
                String strippedNumber = androidContact.getStrippedPhoneNum(0);
                String contactNumber  = db.getContactNumber(strippedNumber); //Use DB to get number on details screen aligned
                db.closeReadableDatabase();
                db.close();

                intent.putExtra("contactName", androidContact.getName());
                intent.putExtra("strippedContactNumber", strippedNumber);
                intent.putExtra("contactNumber", contactNumber);
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
