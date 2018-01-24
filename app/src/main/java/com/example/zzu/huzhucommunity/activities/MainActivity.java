package com.example.zzu.huzhucommunity.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.NewResourceAdapter;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageButton resourceButton;
    private ImageButton requestButton;
    private ArrayList<NewResourceItem> list = new ArrayList<>();
    private NewResourceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();

        resourceButton = findViewById(R.id.MainActivity_resource_button);
        requestButton = findViewById(R.id.MainActivity_request_button);
        RecyclerView newResourceRecyclerView = findViewById(R.id.MainActivity_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        newResourceRecyclerView.setLayoutManager(manager);
        adapter = new NewResourceAdapter(list);
        newResourceRecyclerView.setAdapter(adapter);

        addListener(R.id.MainActivity_resource_button);
        addListener(R.id.MainActivity_resource_text_view);
        addListener(R.id.MainActivity_request_button);
        addListener(R.id.MainActivity_request_text_view);

        initList();
    }

    /**
     * 初始化列表项
     */
    public void initList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i < 22; i++){
                    String title = "生命" + i + "号";
                    String detail = "这是一个每个人多需要的富含咖啡因，内啡肽，以及各种有助于健康的神经递质的只要998的含片";
                    int time = i + (int) (Math.random() * 100);
                    double price = ((int) (Math.random() * 1000)) / 10;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_image);
                    NewResourceItem item = new NewResourceItem(title, detail, time, price, bitmap);
                    list.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        }).start();
    }
    /**
     * 为每个控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.MainActivity_resource_button:
                    case R.id.MainActivity_resource_text_view:
                        resourceButton.setImageResource(R.drawable.resource_yellow);
                        requestButton.setImageResource(R.drawable.request_gray);
                        break;
                    case R.id.MainActivity_request_button:
                    case R.id.MainActivity_request_text_view:
                        resourceButton.setImageResource(R.drawable.resource_gray);
                        requestButton.setImageResource(R.drawable.request_gray_fill_yellow);
                        break;
                }
            }
        });
    }
}
