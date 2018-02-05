package com.example.zzu.huzhucommunity.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;

public class ProfileUpdateActivity extends BaseActivity {
    private static final String TAG = "ProfileUpdateActivity";
    public static final String RETURN_INFO = "RETURN_INFO";
    private static final String STRING_EXTRA = "STRING_EXTRA";
    private static final String TYPE_EXTRA = "TYPE";
    public static final int TYPE_USER_NAME = 1;
    public static final int TYPE_PHONE = 2;
    public static final int TYPE_GRADE = 3;
    public static final int TYPE_DEPARTMENT= 4;
    private EditText editText;
    private Menu menu;
    private String info = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update_layout);
        Toolbar toolbar = findViewById(R.id.ProfileUpdateActivity_toolbar);
        setSupportActionBar(toolbar);
        editText = findViewById(R.id.ProfileUpdateActivity_text_view);
        info = getIntent().getStringExtra(STRING_EXTRA);
        editText.setText(info);
        editText.setSelection(info.length());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            String title = getString(R.string.userName);
            switch (getIntent().getIntExtra(TYPE_EXTRA, 1)){
                case TYPE_USER_NAME:
                    title = getString(R.string.userName);
                    break;
                case TYPE_PHONE:
                    title = getString(R.string.phone);
                    editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    break;
                case TYPE_GRADE:
                    title = getString(R.string.grade);
                    editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    break;
                case TYPE_DEPARTMENT:
                    title = getString(R.string.department);
            }
            actionBar.setTitle(title);
        }
        addListener(R.id.ProfileUpdateActivity_text_view);
        addListener(R.id.ProfileUpdateActivity_cancel_button);
    }

    @Override
    public void addListener(final int res) {
        if (res == R.id.ProfileUpdateActivity_text_view){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0){
                        findViewById(R.id.ProfileUpdateActivity_cancel_button).setVisibility(View.GONE);
                        if (menu != null)
                            menu.getItem(0).setEnabled(false);
                    }
                    else{
                        findViewById(R.id.ProfileUpdateActivity_cancel_button).setVisibility(View.VISIBLE);
                        if (menu != null)
                            menu.getItem(0).setEnabled(true);
                    }
                }
            });
            return;
        }
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.ProfileUpdateActivity_cancel_button:
                        editText.setText("");
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.profile_update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile_update_menu_item:
                if (!TextUtils.equals(info, editText.getText()))
                    setResult(RESULT_OK, new Intent().putExtra(RETURN_INFO, editText.getText().toString()));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void startMe(Context context, int type, int requestCode, String data){
        Intent intent = new Intent(context, ProfileUpdateActivity.class);
        intent.putExtra(TYPE_EXTRA, type);
        intent.putExtra(STRING_EXTRA, data);
        ((Activity)context).startActivityForResult(intent, requestCode);
    }
}
