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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.ChatRoomMessageAdapter;
import com.example.zzu.huzhucommunity.commonclass.ChatRoomMessageItem;
import com.example.zzu.huzhucommunity.commonclass.Utilities;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * 聊天界面活动
 */
public class ChatRoomActivity extends BaseActivity {
    private ImageButton sendImageButton;
    private Button sendButton;
    private ChatRoomMessageAdapter adapter;
    private EditText inputEditText;
    private ArrayList<ChatRoomMessageItem> list = new ArrayList<>();
    private RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.ChatRoomActivity_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_head_over_watch);
        adapter = new ChatRoomMessageAdapter(list, bitmap, bitmap);
        recyclerView.setAdapter(adapter);

        addListener(R.id.ChatRoomActivity_send_button);
        addListener(R.id.ChatRoomActivity_send_image_button);
        addListener(R.id.ChatRoomActivity_input_edit_text);
    }

    /**
     * 为控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        if (res == R.id.ChatRoomActivity_input_edit_text){
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length() == 0){
                        sendImageButton.setVisibility(View.VISIBLE);
                        sendButton.setVisibility(View.GONE);
                    }
                    else{
                        sendImageButton.setVisibility(View.GONE);
                        sendButton.setVisibility(View.VISIBLE);
                    }
                }
            };
            inputEditText.addTextChangedListener(textWatcher);
            inputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND){
                        sendButton.performClick();
                        return true;
                    }
                    return false;
                }
            });
            return;
        }
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.ChatRoomActivity_send_button:
                        String tmpString = inputEditText.getText().toString();
                        if (TextUtils.isEmpty(tmpString))
                            break;
                        list.add(new ChatRoomMessageItem(false, tmpString));
                        list.add(new ChatRoomMessageItem(true, getString(R.string.veryGood)));
                        recyclerView.smoothScrollToPosition(list.size() - 1);
                        adapter.notifyDataSetChanged();
                        inputEditText.setText("");
                        break;
                    case R.id.ChatRoomActivity_send_image_button:
                        Utilities.startPickImageDialog(ChatRoomActivity.this);
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
                OthersProfileActivity.startMe(ChatRoomActivity.this, 0);
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
        switch (requestCode){
            case Utilities.PICK_IMAGE_FROM_CAMERA:
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
                break;
            case Utilities.PICK_IMAGE_FROM_GALLERY:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    if(bundle != null){
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        list.add(new ChatRoomMessageItem(false, bitmap));
                        list.add(new ChatRoomMessageItem(true, getString(R.string.veryGood)));
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(list.size() - 1);
                    }
                }
                break;
        }
    }

    public static void startMe(Context context){
        context.startActivity(new Intent(context, ChatRoomActivity.class));
    }
}
