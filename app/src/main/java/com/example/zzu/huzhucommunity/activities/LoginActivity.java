package com.example.zzu.huzhucommunity.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.asyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.loginRegister;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class LoginActivity extends AppCompatActivity implements asyncHttpCallback {
    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();

        accountEditText = findViewById(R.id.LoginActivity_account_edit_text);
        passwordEditText = findViewById(R.id.LoginActivity_password_edit_text);

        addListener(R.id.LoginActivity_login_button);
        addListener(R.id.LoginActivity_register_text_view);
        addListener(R.id.LoginActivity_password_cancel_button);
    }

    /**
     * 添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.LoginActivity_login_button:
                        loginRegister.getOurInstance().login(accountEditText.getText().toString(), passwordEditText.getText().toString(),LoginActivity.this);
                        break;
                    case R.id.LoginActivity_register_text_view:
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.LoginActivity_password_cancel_button:
                        passwordEditText.setText("");
                        break;
                }
            }
        });
    }

    /**
     * 回调处理HTTP请求成功
     *
     * @param code 返回状态
     */
    @Override
    public void onSuccess(int code) {
        Toast.makeText(this, "Success login", Toast.LENGTH_SHORT).show();
    }

    /**
     * 回调处理HTTP请求异常
     *
     * @param code 返回状态
     */
    @Override
    public void onError(int code) {
        Toast.makeText(this, "Error on login", Toast.LENGTH_SHORT).show();
    }
}
