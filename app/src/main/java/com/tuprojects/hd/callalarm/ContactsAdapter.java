package com.tuprojects.hd.callalarm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//Interface between the (Contacts) data source and the AdapterView (ListView->RecyclerView) layout
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    //State
    private List<String> dataset;

    //Inner Class, ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //State
        public TextView textView;

        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.testTextView);
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
    public ContactsAdapter(List<String> dataset) {
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
        holder.textView.setText(dataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
