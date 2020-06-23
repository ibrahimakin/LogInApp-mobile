package com.iAKIN.LogInApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.iAKIN.LogInApp.Data.Record;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Record[] mDataset;
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
            emailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClipboard(v.getContext(), emailButton.getText().toString());
                    Snackbar.make(v, "Panoya Kopyalandı.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
        private void setClipboard(Context context, String text) {
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("e-mail", text);
                clipboard.setPrimaryClip(clip);
            }
        }
    }
    public interface OnItemListener{
        void onItemClick(int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Record[] myDataset, OnItemListener onItemListener) {
        mDataset = myDataset;
        this.mOnItemListener = onItemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
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
        textView.setText(mDataset[position].getSite());
        button.setText(mDataset[position].getEMail());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
