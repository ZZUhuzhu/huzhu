package com.example.zzu.huzhucommunity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

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

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Grades, R.layout.grade_spinner_item_view);
        arrayAdapter.setDropDownViewResource(R.layout.grade_spinner_item_drop_down_view);
        Spinner spinner = findViewById(R.id.RegisterActivity_grade_spinner);
        spinner.setAdapter(arrayAdapter);
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
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.RegisterActivity_register_button:
                        break;
                }
            }
        });
    }
}
