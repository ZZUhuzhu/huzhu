package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.LoginRegister;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.RegisterEditTextLayout;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String TAG = "RegisterActivity";
    private Spinner gradeSpinner;
    private Spinner depSpinner;
    private RegisterEditTextLayout accountTextView;
    private RegisterEditTextLayout passwordTextView;
    private RegisterEditTextLayout phoneTextView;
    private RegisterEditTextLayout confirmPasswordTextView;
    private RadioGroup sexRadioGroup;

    private String account, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        Toolbar toolbar = findViewById(R.id.RegisterActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        accountTextView = findViewById(R.id.RegisterActivity_account_custom_holder);
        passwordTextView = findViewById(R.id.RegisterActivity_password_custom_holder);
        phoneTextView = findViewById(R.id.RegisterActivity_phone_custom_holder);
        confirmPasswordTextView = findViewById(R.id.RegisterActivity_confirm_password_custom_holder);
        sexRadioGroup = findViewById(R.id.RegisterActivity_sex_custom_radio_group);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Grades, R.layout.spinner_grade_item_view);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_grade_item_drop_down_view);
        gradeSpinner = findViewById(R.id.RegisterActivity_grade_spinner);
        gradeSpinner.setAdapter(arrayAdapter);

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Departments, R.layout.spinner_grade_item_view);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_grade_item_drop_down_view);
        depSpinner = findViewById(R.id.RegisterActivity_department_spinner);
        depSpinner.setAdapter(arrayAdapter);

        addListener(R.id.RegisterActivity_register_button);
    }

    /**
     * 重写方法，设置标题栏返回键点击事件
     * @param item 菜单项，标题栏ToolBar的菜单
     * @return 返回是否成功执行对应事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 为控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.RegisterActivity_register_button:
                        String account = accountTextView.getText(), password = passwordTextView.getText(), confirmPassword = confirmPasswordTextView.getText();
                        String phone = phoneTextView.getText();
                        if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phone)) {
                            Toast.makeText(MyApplication.getContext(), "请完善信息", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if(!TextUtils.equals(password, confirmPassword)){
                            Toast.makeText(MyApplication.getContext(), "请保持两次输入密码一致", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        String sex = Utilities.MALE;
                        if(sexRadioGroup.getCheckedRadioButtonId() == R.id.RegisterActivity_sex_custom_radio_female_button)
                           sex = Utilities.FEMALE;
                        register(account, password, phone, sex, (String) gradeSpinner.getSelectedItem(), (String) depSpinner.getSelectedItem());
                        break;
                }
            }
        });
    }

    /**
     * 注册
     * @param account 用户输入的账户
     * @param password 用户输入的密码
     * @param phone 用户输入的手机号码
     * @param sex 用户选择的性别
     * @param grade  用户选择的年级
     * @param department 用户选择的院系
     */
    public void register(String account, String password, String phone, String sex, String grade, String department){
        this.account = account;
        this.password = password;
        LoginRegister.getOurInstance().register(account, password, phone, sex, grade, department, this);
    }
    public static void startMe(Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    /**
     * 注册成功回调
     * @param statusCode 返回状态
     * @param mp 存储需要传递数据的哈希表，若无数据则 mp 为 null
     * @param requestCode 请求码，标识同一个activity的不同网络请求
     */
    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        switch (requestCode){
            case LoginRegister.REGISTER:
                if (statusCode == 200){
                    Log.e(TAG, "onSuccess: register success");
                    LoginRegister.getOurInstance().login(account, password, this);
                }
                break;
            case LoginRegister.LOGIN:
                Log.e(TAG, "onSuccess: log in success");
                MainActivity.startMe(this);
                finish();
                break;
        }
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
    }
}
