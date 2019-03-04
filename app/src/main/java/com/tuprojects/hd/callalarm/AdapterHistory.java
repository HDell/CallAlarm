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

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {

    public static final String TAG = "AdapterHistory";

    //State
    private List<CallLogData> dataset;
    private Context context;

    //Inner Class, ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //State
        public TextView nameTextView;
        public TextView detailsTextView;
        public ConstraintLayout parentLayout;

        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.history_name_holder);
            this.detailsTextView = itemView.findViewById(R.id.history_details_holder);
            this.parentLayout = itemView.findViewById(R.id.history_layout);
        }
    }

    //Constructor
    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterHistory(Context context, List<CallLogData> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    //Behavior

    //ViewHolder Behavior // Create new views (invoked by the layout manager) //NECESSARY
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recyclerview_row, parent, false);
        AdapterHistory.ViewHolder viewHolder = new AdapterHistory.ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager) //NECESSARY
    @Override
    public void onBindViewHolder(AdapterHistory.ViewHolder holder, int position) {

        final CallLogData callLogData = dataset.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
            //String numName = dataset.get(position).getStrippedNumber()+"||"+dataset.get(position).getName();
        holder.nameTextView.setText(dataset.get(position).getName());

        String details = dataset.get(position).getType()+", "+dataset.get(position).getDate();
        holder.detailsTextView.setText(details);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Successfully added Contact from Call Log List.");
                Intent intent = new Intent(context, ActivityDetails.class);
                intent.putExtra("contactName", callLogData.getName());
                intent.putExtra("strippedContactNumber", callLogData.getStrippedNumber());
                intent.putExtra("contactNumber", callLogData.getNumber()); //think about adding an extra to signal that this is coming the history to update the number to the android contact number style
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
