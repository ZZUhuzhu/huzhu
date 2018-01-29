package com.example.zzu.huzhucommunity.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.customlayout.RegisterEditTextLayout;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    public static final boolean MALE = false;
    public static final boolean FEMALE = true;
    private Spinner gradeSpinner;
    private Spinner depSpinner;
    private RegisterEditTextLayout accountTextView;
    private RegisterEditTextLayout passwordTextView;
    private RegisterEditTextLayout phoneTextView;
    private RegisterEditTextLayout confirmPasswordTextView;
    private RadioGroup sexRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        Toolbar toolbar = findViewById(R.id.RegisterActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("注册");

        accountTextView = findViewById(R.id.RegisterActivity_account_custom_holder);
        passwordTextView = findViewById(R.id.RegisterActivity_password_custom_holder);
        phoneTextView = findViewById(R.id.RegisterActivity_phone_custom_holder);
        confirmPasswordTextView = findViewById(R.id.RegisterActivity_confirm_password_custom_holder);
        sexRadioGroup = findViewById(R.id.RegisterActivity_sex_custom_radio_group);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Grades, R.layout.grade_spinner_item_view);
        arrayAdapter.setDropDownViewResource(R.layout.grade_spinner_item_drop_down_view);
        gradeSpinner = findViewById(R.id.RegisterActivity_grade_spinner);
        gradeSpinner.setAdapter(arrayAdapter);

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Departments, R.layout.grade_spinner_item_view);
        arrayAdapter.setDropDownViewResource(R.layout.grade_spinner_item_drop_down_view);
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
                        boolean sex = MALE;
                        if(sexRadioGroup.getCheckedRadioButtonId() == R.id.RegisterActivity_sex_custom_radio_female_button)
                            sex = FEMALE;
                        register(account, password, phone, sex, (String) gradeSpinner.getSelectedItem(), (String) depSpinner.getSelectedItem());
                        break;
                }
            }
        });
    }

    /**
     * 注册用户
     * @param account 用户输入的账户
     * @param password 用户输入的密码
     * @param phone 用户输入的手机号码
     * @param sex 用户选择的性别
     * @param grade  用户选择的年级
     * @param department 用户选择的院系
     */
    public void register(String account, String password, String phone, boolean sex, String grade, String department){
        Log.e(TAG, "register: " + account + password + phone + sex + grade + department);
    }
}
