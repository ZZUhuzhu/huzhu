package com.example.zzu.huzhucommunity.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.LoginRegister;


public class LoginActivity extends AppCompatActivity implements AsyncHttpCallback {
    private EditText accountEditText;
    private EditText passwordEditText;

    private ImageButton cancelButton;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(s)) cancelButton.setVisibility(View.GONE);
            else cancelButton.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();

        cancelButton = findViewById(R.id.LoginActivity_password_cancel_button);
        accountEditText = findViewById(R.id.LoginActivity_account_edit_text);
        passwordEditText = findViewById(R.id.LoginActivity_password_edit_text);
        passwordEditText.addTextChangedListener(textWatcher);

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
                Intent intent;
                switch (res){
                    case R.id.LoginActivity_login_button:
                        //onSuccess(1);
                        String account = accountEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                            Toast.makeText(LoginActivity.this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //finish();
                        LoginRegister.getOurInstance().login(account, password,LoginActivity.this);
                        break;
                    case R.id.LoginActivity_register_text_view:
                        intent = new Intent(LoginActivity.this, RegisterActivity.class);
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
     * @param code 返回状态
     */
    @Override
    public void onSuccess(int code) {
        Toast.makeText(this, "Success login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 回调处理HTTP请求异常
     * @param code 返回状态
     */
    @Override
    public void onError(int code) {
        Toast.makeText(this, "Error on login", Toast.LENGTH_SHORT).show();
    }
}
