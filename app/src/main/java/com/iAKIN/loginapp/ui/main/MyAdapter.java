package com.iAKIN.loginapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iAKIN.loginapp.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    private OnItemListener mOnItemListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        public TextView siteTextView;
        public Button emailButton;
        OnItemListener onItemListener;

        public MyViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);
            siteTextView = (TextView) itemView.findViewById(R.id.site_name);
            emailButton = (Button) itemView.findViewById(R.id.email_button);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnItemListener{
        void onItemClick(int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset, OnItemListener onItemListener) {
        mDataset = myDataset;
        this.mOnItemListener = onItemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.record_view, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(contactView, mOnItemListener);
        return viewHolder;
        /*TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;*/
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView textView = holder.siteTextView;
        Button button = holder.emailButton;
        textView.setText(mDataset[position]);
        button.setText("email");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
