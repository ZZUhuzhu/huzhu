package com.example.zzu.huzhucommunity.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.MainRequestAdapter;
import com.example.zzu.huzhucommunity.adapters.MainResourcesAdapter;
import com.example.zzu.huzhucommunity.adapters.MainViewPagerAdapter;
import com.example.zzu.huzhucommunity.commonclass.Constants;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.NewRequestItem;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    public static final String REQUEST_DETAIL_REQUEST_ITEM_POSITION="REQUEST_POSITION";
    public static final String RESOURCE_DETAIL_RESOURCE_ITEM_POSITION = "RESOURCE_POSITION";
    public static final int PUBLISH_NEW_RESOURCE = 0;
    public static final int PUBLISH_NEW_REQUEST = 1;
    public static final int RESOURCE_DETAIL_REQUEST_CODE = 2;
    public static final int REQUEST_DETAIL_REQUEST_CODE = 2;
    private ImageButton resourceButton;
    private ImageButton requestButton;
    private ImageButton userHeadImageButton;

    private RecyclerView newResourceRecyclerView;
    private ArrayList<NewResourceItem> resourceItems = new ArrayList<>();
    private MainResourcesAdapter resourceAdapter;

    private RecyclerView newRequestRecyclerView;
    private ArrayList<NewRequestItem> requestItems = new ArrayList<>();
    private MainRequestAdapter requestAdapter;

    private ViewPager mainViewPager;
    private ArrayList<View> pagerViews = new ArrayList<>();

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

        newResourceRecyclerView = new RecyclerView(this);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        newResourceRecyclerView.setLayoutManager(manager);
        resourceAdapter = new MainResourcesAdapter(resourceItems, this);
        newResourceRecyclerView.setAdapter(resourceAdapter);
        pagerViews.add(newResourceRecyclerView);

        newRequestRecyclerView = new RecyclerView(this);
        manager = new LinearLayoutManager(this);
        newRequestRecyclerView.setLayoutManager(manager);
        requestAdapter = new MainRequestAdapter(requestItems, this);
        newRequestRecyclerView.setAdapter(requestAdapter);
        pagerViews.add(newRequestRecyclerView);

        mainViewPager = findViewById(R.id.MainActivity_view_pager);
        mainViewPager.setAdapter(new MainViewPagerAdapter(pagerViews));
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                changePagerButton(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        addListener(R.id.MainActivity_resource_button);
        addListener(R.id.MainActivity_resource_text_view);
        addListener(R.id.MainActivity_request_button);
        addListener(R.id.MainActivity_request_text_view);
        addListener(R.id.MainActivity_head_button);
        addListener(R.id.MainActivity_publish_button);
        addListener(R.id.MainActivity_message_button);
        addListener(R.id.MainActivity_search_text_view);

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
                    ArrayList<Bitmap> bitmaps = new ArrayList<>();
                    bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.default_image));
                    NewResourceItem item = new NewResourceItem(title, detail, time, price, bitmaps);
                    resourceItems.add(item);

                    title = "给我个" + title;
                    detail = getString(R.string.virtualRequestDetail);
                    NewRequestItem requestItem = new NewRequestItem(title, detail, time, price, bitmaps);
                    requestItems.add(requestItem);
                }
                resourceAdapter.notifyDataSetChanged();
                requestAdapter.notifyDataSetChanged();
            }
        }).start();
    }

    /**
     * ViewPager切换即MainActivity中页面改变时调用
     * @param position 切换到的页面对应的下标
     */
    public void changePagerButton(int position){
        if(position == 0){
            resourceButton.setImageResource(R.drawable.resource_yellow);
            requestButton.setImageResource(R.drawable.request_gray);
            newResourceRecyclerView.setVisibility(View.VISIBLE);
            newRequestRecyclerView.setVisibility(View.GONE);
        }
        else if(position == 1){
            resourceButton.setImageResource(R.drawable.resource_gray);
            requestButton.setImageResource(R.drawable.request_yellow);
            newResourceRecyclerView.setVisibility(View.GONE);
            newRequestRecyclerView.setVisibility(View.VISIBLE);
        }
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
                        mainViewPager.setCurrentItem(0);
                        break;
                    case R.id.MainActivity_request_button:
                    case R.id.MainActivity_request_text_view:
                        mainViewPager.setCurrentItem(1);
                        break;
                    case R.id.MainActivity_head_button:
                        intent = new Intent(MainActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.MainActivity_publish_button:
                        ImageButton publishButton = findViewById(R.id.MainActivity_publish_button);
                        Animation animation = new RotateAnimation(0.0f, 90,
                                publishButton.getWidth() / 2, publishButton.getHeight() / 2);
                        animation.setDuration(150);
                        animation.setFillAfter(true);
                        publishButton.startAnimation(animation);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setItems(R.array.PublishType, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, PublishNewActivity.class);
                                intent.putExtra("publishType", which);
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                        break;
                    case R.id.MainActivity_message_button:
                        intent = new Intent(MainActivity.this, MessagesActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.MainActivity_search_text_view:
                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESOURCE_DETAIL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                int pos = data.getIntExtra(RESOURCE_DETAIL_RESOURCE_ITEM_POSITION, -1);
                if(pos != -1) {
                    resourceItems.remove(pos);
                    resourceAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
