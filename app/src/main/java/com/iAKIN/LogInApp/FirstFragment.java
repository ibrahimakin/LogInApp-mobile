package com.iAKIN.LogInApp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iAKIN.LogInApp.Data.Record;
import com.iAKIN.LogInApp.Data.Source;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirstFragment extends Fragment implements MyAdapter.OnItemListener  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //Record[] rs = {new Record("deneme1.com", "deneme1@mail.com"), new Record("deneme2.com", "deneme2@mail.com"), new Record("deneme3.com", "deneme3@mail.com"), new Record("deneme4.com", "deneme4@mail.com")}
    private List<Record> myDataset = new ArrayList<Record>();

    Source ds;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_first, container, false);

        ds = new Source(root.getContext());
        ds.open();
        myDataset = ds.getRecords();
        // Inflate the layout for this fragment
        return root;
    }

    static public FirstFragment getFragment(){
        return new FirstFragment();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.accountList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(myDataset,this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
        //Log.d(TAG, "Clicked " + position);
    }

}