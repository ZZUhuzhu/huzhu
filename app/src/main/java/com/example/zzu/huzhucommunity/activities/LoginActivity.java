package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.LoginRegister;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;

import java.util.HashMap;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements AsyncHttpCallback {
    private EditText passwordEditText;
    private EditText accountEditText;
    private ImageButton passwordCancelButton;
    private ImageButton accountCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();

        setSwipeToFinishOff();

        passwordCancelButton = findViewById(R.id.LoginActivity_password_cancel_button);
        passwordEditText = findViewById(R.id.LoginActivity_password_edit_text);
        accountCancelButton = findViewById(R.id.LoginActivity_account_cancel_button);
        accountEditText = findViewById(R.id.LoginActivity_account_edit_text);

        findViewById(R.id.LoginActivity_top_view).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ImageView tmp = findViewById(R.id.LoginActivity_profile_image_view);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_head);

        tmp.setImageBitmap(bitmap);

        addListener(R.id.LoginActivity_login_button);
        addListener(R.id.LoginActivity_register_text_view);
        addListener(R.id.LoginActivity_password_cancel_button);
        addListener(R.id.LoginActivity_account_edit_text);
        addListener(R.id.LoginActivity_password_edit_text);
        addListener(R.id.LoginActivity_account_cancel_button);
    }

    /**
     * 添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        //设置软键盘右下角登录按钮
        if (res == R.id.LoginActivity_password_edit_text){
            passwordEditText.setImeActionLabel(getString(R.string.login), EditorInfo.IME_ACTION_GO);
            passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO){
                        findViewById(R.id.LoginActivity_login_button).performClick();
                        return true;
                    }
                    return false;
                }
            });
            //设置密码框监听器
            passwordEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if(TextUtils.isEmpty(s)) passwordCancelButton.setVisibility(View.GONE);
                    else passwordCancelButton.setVisibility(View.VISIBLE);
                }
            });
            return;
        }
        if (res == R.id.LoginActivity_account_edit_text){
            accountEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) accountCancelButton.setVisibility(View.GONE);
                    else accountCancelButton.setVisibility(View.VISIBLE);
                }
            });
            return;
        }
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.LoginActivity_login_button:
                        //onSuccess(1);
                        String account = accountEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                            Toast.makeText(LoginActivity.this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        LoginRegister.getOurInstance().login(account, password,LoginActivity.this);
                        break;
                    case R.id.LoginActivity_register_text_view:
                        RegisterActivity.startMe(LoginActivity.this);
                        break;
                    case R.id.LoginActivity_password_cancel_button:
                        passwordEditText.setText("");
                        break;
                    case R.id.LoginActivity_account_cancel_button:
                        accountEditText.setText("");
                }
            }
        });
    }

    /**
     * 回调处理HTTP请求成功
     * @param statusCode 返回状态
     * @param requestCode 标识同一个activity的不同网络请求
     */
    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        Toast.makeText(MyApplication.getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        MainActivity.startMe(this);
        finish();
    }

    /**
     * 回调处理HTTP请求异常
     * @param statusCode 返回状态
     */
    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
    }
    public static void startMe(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
