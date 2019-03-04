package com.tuprojects.hd.callalarm;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    
    //State
    private List<CallLogData> dataset;

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
    public AdapterHistory(List<CallLogData> dataset) {
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
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
            //String numName = dataset.get(position).getStrippedNumber()+"||"+dataset.get(position).getName();
        holder.nameTextView.setText(dataset.get(position).getName());

        String details = dataset.get(position).getType()+", "+dataset.get(position).getDate();
        holder.detailsTextView.setText(details);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
