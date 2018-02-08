package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.CommonViewPagerAdapter;
import com.example.zzu.huzhucommunity.adapters.MyCommentAdapter;
import com.example.zzu.huzhucommunity.commonclass.CommentItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyCommentActivity extends BaseActivity {
    private static final int TASK_OVER = 0;
    private ArrayList<CommentItem> myCommentList = new ArrayList<>();
    private ArrayList<CommentItem> mentionedCommentList = new ArrayList<>();
    private RecyclerView myCommentRecyclerView;
    private RecyclerView mentionRecyclerView;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == TASK_OVER){
                myCommentRecyclerView.getAdapter().notifyDataSetChanged();
                mentionRecyclerView.getAdapter().notifyDataSetChanged();
                findViewById(R.id.MyCommentActivity_progress_bar).setVisibility(View.GONE);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment_layout);
        Toolbar toolbar = findViewById(R.id.MyCommentActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setSwipeToFinishOff();

        ViewPager viewPager = findViewById(R.id.MyCommentActivity_view_pager);
        TabLayout tabLayout = findViewById(R.id.MyCommentActivity_tab_layout);
        ArrayList<View> views = new ArrayList<>();
        viewPager.setAdapter(new CommonViewPagerAdapter(views));

        myCommentRecyclerView = new RecyclerView(this);
        myCommentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myCommentRecyclerView.setAdapter(new MyCommentAdapter(this, myCommentList));
        views.add(myCommentRecyclerView);

        mentionRecyclerView = new RecyclerView(this);
        mentionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mentionRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mentionRecyclerView.setAdapter(new MyCommentAdapter(this, mentionedCommentList));
        views.add(mentionRecyclerView);

        viewPager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        if (tab != null)
            tab.setText(getString(R.string.commentByMe));
        tab = tabLayout.getTabAt(1);
        if (tab != null)
            tab.setText(getString(R.string.meMentioned));

        initList();
    }

    @Override
    public void addListener(int res) {
    }
    public void initList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Calendar calendar = GregorianCalendar.getInstance();
                for (int i = 0; i < 22; i++){
                    CommentItem item = new CommentItem(2, getString(R.string.solider), getString(R.string.virtualComment),
                            calendar.getTimeInMillis(), BitmapFactory.decodeResource(getResources(), R.drawable.profile_head));
                    mentionedCommentList.add(item);

                    item = new CommentItem(getString(R.string.virtualComment), calendar.getTimeInMillis());
                    myCommentList.add(item);
                }
                Message message = new Message();
                message.what = TASK_OVER;
                handler.sendMessage(message);
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

    public static void startMe(Context context){
        context.startActivity(new Intent(context, MyCommentActivity.class));
    }
}
