package com.example.zzu.huzhucommunity.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zzu.huzhucommunity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();
    }
}
