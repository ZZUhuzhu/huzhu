package com.example.zzu.huzhucommunity.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.MessageItemAdapter;
import com.example.zzu.huzhucommunity.commonclass.NewMessagesItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MessagesActivity extends AppCompatActivity {
    public static final String CHAT_ROOM_INTENT_EXTRA_NAME = "MESSAGE";
    private ArrayList<NewMessagesItem> messagesItems = new ArrayList<>();
    private MessageItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_layout);
        Toolbar toolbar = findViewById(R.id.MessagesActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        final RecyclerView recyclerView = findViewById(R.id.MessagesActivity_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MessagesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageItemAdapter(messagesItems, this);
        recyclerView.setAdapter(adapter);

        initList();
    }
    public void initList(){
        for(int i = 0; i < 33; i++){
            int senderID = (int) (Math.random() * 30);
            String sendName = getString(R.string.solider);
            String firstNewMessage = getString(R.string.virtualResourceDetail);
            Calendar calendar = GregorianCalendar.getInstance();
            String curTime = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) +
                    " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            NewMessagesItem messagesItem = new NewMessagesItem(senderID, sendName, firstNewMessage, (int) (Math.random() * 120),curTime, null);
            messagesItems.add(messagesItem);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.message_mark_all_read_menu_item:
                for(NewMessagesItem messagesItem: messagesItems)
                    messagesItem.setRead();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
