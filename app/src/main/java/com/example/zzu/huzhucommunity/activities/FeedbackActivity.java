package com.example.zzu.huzhucommunity.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.Setting;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;

import java.util.HashMap;

public class FeedbackActivity extends BaseActivity implements AsyncHttpCallback {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_layout);
        Toolbar toolbar = findViewById(R.id.FeedbackActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.FeedbackActivity_text_view);
        findViewById(R.id.FeedbackActivity_commit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() <= 0){
                    finish();
                    return;
                }
                Setting.getOurInstance().recordUserFeedback(Utilities.GetStringLoginUserId(),
                        editText.getText().toString(), FeedbackActivity.this);
            }
        });
    }

    @Override
    public void addListener(final int res) {}

    public static void StartMe(Activity activity){
        activity.startActivity(new Intent(activity, FeedbackActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        Toast.makeText(MyApplication.getContext(), "感谢您的反馈", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), "感谢您的反馈", Toast.LENGTH_SHORT).show();
        finish();
    }
}
