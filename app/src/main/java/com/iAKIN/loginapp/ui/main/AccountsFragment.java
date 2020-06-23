package com.iAKIN.loginapp.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.iAKIN.loginapp.MainActivity;
import com.iAKIN.loginapp.R;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountsFragment extends Fragment implements MyAdapter.OnItemListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private AccountsViewModel pageViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] myDataset= {"abadv","safdads","fdasfda","adfasfd"};

    public static AccountsFragment newInstance(int index) {
        AccountsFragment fragment = new AccountsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(AccountsViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.accountList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(myDataset,this);
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onItemClick(int position) {
        MainActivity.viewPager.setCurrentItem(1);
        Log.d(TAG, "Clicked " + position);
    }
}