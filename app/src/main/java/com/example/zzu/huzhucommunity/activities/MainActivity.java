package com.example.zzu.huzhucommunity.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.NewResourceAdapter;
import com.example.zzu.huzhucommunity.commonclass.Constants;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton resourceButton;
    private ImageButton requestButton;
    private ImageButton userHeadImageButton;
    private ArrayList<NewResourceItem> list = new ArrayList<>();
    private NewResourceAdapter adapter;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case Constants.UserProfileUserHeadImageGot:
                    userHeadImageButton.setImageDrawable(MyApplication.userHeadImage);
                    return true;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        userHeadImageButton = findViewById(R.id.MainActivity_head_button);
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
        addListener(R.id.MainActivity_head_button);
        addListener(R.id.MainActivity_publish_button);

        initList();
        initUserHead();
    }

    /**
     * 获取用户头像信息
     * 如果当前已经保存用户头像信息，则直接使用
     * 否则调用MyApplication的downloadUserHeadImage方法下载
     */
    public void initUserHead(){
        if(MyApplication.userId != -1){
            if(MyApplication.userHeadImage == null)
                MyApplication.downloadUserHeadImage(handler);
            else
                userHeadImageButton.setImageDrawable(MyApplication.userHeadImage);
        }
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
                    String detail = getString(R.string.virtualResourceDetail);
                    int time = i + (int) (Math.random() * 100);
                    double price = ((int) (Math.random() * 1000)) / 10;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_image);
                    NewResourceItem item = new NewResourceItem(detail, title, time, price, bitmap);
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
                final Intent intent;
                switch (res){
                    case R.id.MainActivity_resource_button:
                    case R.id.MainActivity_resource_text_view:
                        resourceButton.setImageResource(R.drawable.resource_yellow);
                        requestButton.setImageResource(R.drawable.request_gray);
                        break;
                    case R.id.MainActivity_request_button:
                    case R.id.MainActivity_request_text_view:
                        resourceButton.setImageResource(R.drawable.resource_gray);
                        requestButton.setImageResource(R.drawable.request_yellow);
                        break;
                    case R.id.MainActivity_head_button:
                        intent = new Intent(MainActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.MainActivity_publish_button:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setItems(R.array.PublishType, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, PublishNewResActivity.class);
                                intent.putExtra("publishType", which);
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                        break;
                }
            }
        });
    }
}
