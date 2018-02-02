package com.example.zzu.huzhucommunity.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.ChatRoomMessageAdapter;
import com.example.zzu.huzhucommunity.commonclass.ChatRoomMessageItem;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * 聊天界面活动
 */
public class ChatRoomActivity extends BaseActivity {
    private static final int PICK_IMAGE = 1;
    private ImageButton sendImageButton;
    private Button sendButton;
    private ChatRoomMessageAdapter adapter;
    private EditText inputEditText;
    private ArrayList<ChatRoomMessageItem> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(s.length() == 0){
                sendImageButton.setVisibility(View.GONE);
                sendButton.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() == 0){
                sendImageButton.setVisibility(View.VISIBLE);
                sendButton.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_layout);
        Toolbar toolbar = findViewById(R.id.ChatRoomActivity_toolbar);
        toolbar.setTitle(R.string.solider);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        sendImageButton = findViewById(R.id.ChatRoomActivity_send_image_button);
        sendButton = findViewById(R.id.ChatRoomActivity_send_button);
        inputEditText = findViewById(R.id.ChatRoomActivity_input_edit_text);
        inputEditText.addTextChangedListener(textWatcher);

        recyclerView = findViewById(R.id.ChatRoomActivity_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatRoomMessageAdapter(list, null);
        recyclerView.setAdapter(adapter);

        addListener(R.id.ChatRoomActivity_send_button);
        addListener(R.id.ChatRoomActivity_send_image_button);
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
                    case R.id.ChatRoomActivity_send_button:
                        list.add(new ChatRoomMessageItem(false, inputEditText.getText().toString()));
                        list.add(new ChatRoomMessageItem(true, getString(R.string.veryGood)));
                        recyclerView.smoothScrollToPosition(list.size() - 1);
                        adapter.notifyDataSetChanged();
                        inputEditText.setText("");
                        break;
                    case R.id.ChatRoomActivity_send_image_button:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, PICK_IMAGE);
                        break;
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.ChatRoom_sender_profile_menu_item:
                Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_room_sender_profile_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                if(uri == null) return;
                ContentResolver contentResolver = getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                    list.add(new ChatRoomMessageItem(false, bitmap));
                    list.add(new ChatRoomMessageItem(true, getString(R.string.veryGood)));
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(list.size() - 1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void startMe(Context context){
        context.startActivity(new Intent(context, ChatRoomActivity.class));
    }
}
