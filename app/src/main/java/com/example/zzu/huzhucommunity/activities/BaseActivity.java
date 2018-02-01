package com.example.zzu.huzhucommunity.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesCollector.removeActivity(this);
    }
}
