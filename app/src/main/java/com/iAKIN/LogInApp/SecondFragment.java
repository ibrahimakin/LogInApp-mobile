package com.iAKIN.LogInApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.iAKIN.LogInApp.Data.Record;
import com.iAKIN.LogInApp.Data.RecordList;
import com.iAKIN.LogInApp.Data.Source;

public class SecondFragment extends Fragment {

    Source ds;

    String Site, EMail, Username, Hint, Labels, RegistrationDate, ChangingDate, Hash;
    TextView tvSite, tvEMail, tvUsername, tvHint, tvLabels;

    Record r;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_second, container, false);

        tvSite = root.findViewById(R.id.Site);
        tvEMail = root.findViewById(R.id.EMail);
        tvUsername = root.findViewById(R.id.Username);
        tvHint = root.findViewById(R.id.Hint);
        tvLabels = root.findViewById(R.id.Labels);

        r = RecordList.getLastClickedElement();

        tvSite.setText(r.getSite());
        tvEMail.setText(r.getEMail());
        tvUsername.setText(r.getUsername());
        tvHint.setText(r.getHint());
        tvLabels.setText(r.getLabels());

        //ds = new Source(root.getContext());
        //ds.open();
        // Inflate the layout for this fragment
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button delete = (Button) view.findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Silindi.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //Record r = null;
                //ds.deleteRecord(r);
            }
        });
    }

    public void getDetails(int position) {
        r = RecordList.getListElement(position);
        if(r!=null){
            tvSite.setText(r.getSite());
            tvEMail.setText(r.getEMail());
            tvUsername.setText(r.getUsername());
            tvHint.setText(r.getHint());
            tvLabels.setText(r.getLabels());
        }
    }
}