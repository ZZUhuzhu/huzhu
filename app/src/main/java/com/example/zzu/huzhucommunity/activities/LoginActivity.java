package com.example.zzu.huzhucommunity.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class LoginActivity extends AppCompatActivity {
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
                switch (res){
                    case R.id.LoginActivity_login_button:
                        login(accountEditText.getText().toString(), passwordEditText.getText().toString());
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
     * 获取用户输入的账号和密码后登录
     * @param account 用户输入的账号
     * @param password 用户输入的密码
     */
    public void login(String account, String password){
        Toast.makeText(this, "正在登陆...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        //Toast.makeText(this, "Now Login...", Toast.LENGTH_SHORT).show();
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.setTimeout(3000);
//        RequestParams params = new RequestParams();
//        params.put("account", account);
//        params.put("password", password);
//        String path = "http://www.idooooo.tk/huzhu/php/login.php";
//        client.post(path, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                if(bytes != null) {
//                    Log.d("LoginActivity", "onSuccess: " + (new String(bytes)));
//                }
//            }
//
//            @Override
//            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
//                Toast.makeText(LoginActivity.this, "网络错误，请稍后重试……", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
