package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.NewRequestItem;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;
import com.example.zzu.huzhucommunity.customlayout.ResReqViewPagerLayout;

import java.util.ArrayList;

public class PubRecStarTrackActivity extends AppCompatActivity {
    public static final int TYPE_PUBLISH = 0;
    public static final int TYPE_RECEIVE = 1;
    public static final int TYPE_STAR = 2;
    public static final int TYPE_TRACK = 3;
    private static final String TYPE_EXTRA = "TYPE_EXTRA";
    private ResReqViewPagerLayout pagerLayout;
    private ArrayList<NewRequestItem> requestItems = new ArrayList<>();
    private ArrayList<NewResourceItem> resourceItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_rec_star_track_layout);
        Toolbar toolbar = findViewById(R.id.PubRecStarTrack_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            String title = getString(R.string.publishedByMe);
            switch (getIntent().getIntExtra(TYPE_EXTRA, TYPE_PUBLISH)){
                case TYPE_TRACK:
                    title = getString(R.string.myTrack);
                    break;
                case TYPE_STAR:
                    title = getString(R.string.myStar);
                    break;
                case TYPE_RECEIVE:
                    title = getString(R.string.receivedByMe);
                    break;
                case TYPE_PUBLISH:
                    title = getString(R.string.publishedByMe);
                    break;
            }
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pagerLayout = findViewById(R.id.PubRecStarTrack_pager_view);
        pagerLayout.setView(resourceItems, requestItems);

        initList();
    }
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
                pagerLayout.resNotifyDataSetChanged();
                pagerLayout.reqNotifyDataSetChanged();
            }
        }).start();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void startMe(Context context, int type){
        Intent intent = new Intent(context, PubRecStarTrackActivity.class);
        intent.putExtra(TYPE_EXTRA, type);
        context.startActivity(intent);
    }
}
