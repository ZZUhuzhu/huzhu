package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.MessageItemAdapter;
import com.example.zzu.huzhucommunity.commonclass.Constants;
import com.example.zzu.huzhucommunity.commonclass.NewMessagesItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MessagesActivity extends BaseActivity {
    private ArrayList<NewMessagesItem> messagesItems = new ArrayList<>();
    private MessageItemAdapter adapter;
    private static final String TAG = "MessagesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_layout);
        Toolbar toolbar = findViewById(R.id.MessagesActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        setSwipeToFinishOff();

        final RecyclerView recyclerView = findViewById(R.id.MessagesActivity_recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(MessagesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageItemAdapter(messagesItems, this);
        recyclerView.setAdapter(adapter);

        initList();
    }

    /**
     * 初始化消息列表
     */
    public void initList() {
        for (int i = 0; i < 33; i++) {
            int senderID = (int) (Math.random() * 30);
            String sendName = getString(R.string.solider);
            String firstNewMessage = getString(R.string.virtualResourceDetail);
            Calendar calendar = GregorianCalendar.getInstance();
            NewMessagesItem messagesItem =
                    new NewMessagesItem(senderID, sendName, firstNewMessage, (int) (Math.random() * 120), calendar.getTimeInMillis(), null);
            messagesItems.add(messagesItem);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addListener(int res) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.message_mark_all_read_menu_item:
                for (NewMessagesItem messagesItem : messagesItems)
                    messagesItem.setRead(true);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void startMe(Context context) {
        context.startActivity(new Intent(context, MessagesActivity.class));
    }
}
