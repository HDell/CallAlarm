package com.tuprojects.hd.callalarm;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.ViewHolder> {

    //State
    private List<List<String>> dataset;

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
    public CallListAdapter(List<List<String>> dataset) {
        this.dataset = dataset;
    }

    //Behavior

    //ViewHolder Behavior // Create new views (invoked by the layout manager) //NECESSARY
    @Override
    public CallListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.call_list_recyclerview_row, parent, false);
        CallListAdapter.ViewHolder viewHolder = new CallListAdapter.ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager) //NECESSARY
    @Override
    public void onBindViewHolder(CallListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView.setText(dataset.get(0).get(position));
        holder.frequencyTextView.setText(dataset.get(1).get(position));
        holder.historyTextView.setText(dataset.get(2).get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
