package com.iAKIN.LogInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.iAKIN.LogInApp.Data.Record;
import com.iAKIN.LogInApp.Data.Source;

public class AddingActivity extends AppCompatActivity {

    Source ds;

    String Site, EMail, Username, Hint, Labels, RegistrationDate, ChangingDate, Hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        ds = new Source(this);
        ds.open();

        final EditText etSite, etEMail, etUsername, etHint, etLabels;
        etSite = findViewById(R.id.Site);
        etEMail = findViewById(R.id.EMail);
        etUsername = findViewById(R.id.Username);
        etHint = findViewById(R.id.Hint);
        etLabels = findViewById(R.id.Labels);

        final int Sync = 2;

        Button add = (Button) findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Site = etSite.getText().toString();
                EMail = etEMail.getText().toString();
                Username = etUsername.getText().toString();
                Hint = etHint.getText().toString();
                Labels = etLabels.getText().toString();
                RegistrationDate = "01.05.1998";
                ChangingDate = "01.05.1998";
                Hash = "affdafdafdsvfaqf";


                int id = (int) ds.CreateRecord(Site, EMail, Username, Hint, Labels, RegistrationDate, ChangingDate, Sync, Hash);


                if(id < 0){
                    Snackbar.make(v, "Kayıt Mevcut.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else {
                    Snackbar.make(v, "Eklendi.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Record r = new Record(id, Site, EMail, Username, Hint, Labels, RegistrationDate, ChangingDate, Sync, Hash);
                    MyAdapter.mDataset.add(r);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        ds.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        ds.close();
        super.onPause();
    }
}