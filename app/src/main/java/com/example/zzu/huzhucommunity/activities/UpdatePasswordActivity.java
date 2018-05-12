package com.example.zzu.huzhucommunity.activities;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.Profile;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;

import java.util.HashMap;

public class UpdatePasswordActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String TAG = "UpdatePasswordActivity";
    private static final String UPDATE_PASSWORD_SUCCESS = "密码修改成功";
    private Menu menu;
    private EditText newPWEditText, confirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_layout);
        Toolbar toolbar = findViewById(R.id.UpdatePasswordActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        confirmEditText = findViewById(R.id.UpdatePasswordActivity_confirm_text_view);
        newPWEditText = findViewById(R.id.UpdatePasswordActivity_new_pw_text_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile_update_menu_item:
                String newPW = newPWEditText.getText().toString();
                if (TextUtils.equals(newPW, confirmEditText.getText()) && newPW.length() > 0){
                    Profile.getOurInstance().updatePassword(Utilities.GetStringLoginUserId(), newPW, this);
                }
                else {
                    Toast.makeText(MyApplication.getContext(), RegisterActivity.CONFIRM_PASSWORD_MATCH, Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.profile_update_menu, menu);
        return true;
    }

    @Override
    public void addListener(int res) {
        if (res == R.id.UpdatePasswordActivity_new_pw_text_view){
            newPWEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0){
                        findViewById(R.id.UpdatePasswordActivity_new_pw_cancel_button).setVisibility(View.GONE);
                        if (menu != null)
                            menu.getItem(0).setEnabled(false);
                    }
                    else{
                        findViewById(R.id.UpdatePasswordActivity_new_pw_cancel_button).setVisibility(View.VISIBLE);
                        if (menu != null)
                            menu.getItem(0).setEnabled(true);
                    }
                }
            });
        }
        if (res == R.id.UpdatePasswordActivity_confirm_text_view){
            confirmEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0){
                        findViewById(R.id.UpdatePasswordActivity_confirm_cancel_button).setVisibility(View.GONE);
                        if (menu != null)
                            menu.getItem(0).setEnabled(false);
                    }
                    else{
                        findViewById(R.id.UpdatePasswordActivity_confirm_cancel_button).setVisibility(View.VISIBLE);
                        if (menu != null)
                            menu.getItem(0).setEnabled(true);
                    }
                }
            });
        }
    }

    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        if (statusCode == 200){
            Toast.makeText(MyApplication.getContext(), UPDATE_PASSWORD_SUCCESS, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void StartMe(Activity activity){
        activity.startActivity(new Intent(activity, UpdatePasswordActivity.class));
    }
}
