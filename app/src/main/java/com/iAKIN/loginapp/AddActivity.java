package com.iAKIN.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.iAKIN.loginapp.ui.main.AddFragment;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddFragment.newInstance())
                    .commitNow();
        }
    }
}