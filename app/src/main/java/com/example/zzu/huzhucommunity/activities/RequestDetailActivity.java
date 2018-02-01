package com.example.zzu.huzhucommunity.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.customlayout.CommentItemLayout;
import com.example.zzu.huzhucommunity.customlayout.ResReqDetailBottomButtonLayout;

public class RequestDetailActivity extends BaseActivity {
    private boolean requestStarred = false;
    private int requestItemPosition;
    private ResReqDetailBottomButtonLayout receiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail_layout);
        Toolbar toolbar = findViewById(R.id.RequestDetail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        requestItemPosition = intent.getIntExtra(MainActivity.RESOURCE_DETAIL_RESOURCE_ITEM_POSITION, -1);
        setResult(RESULT_CANCELED);

        receiveButton = findViewById(R.id.RequestDetail_receive_it_button);

        addListener(R.id.RequestDetail_star_button);
        addListener(R.id.RequestDetail_comment_it_button);
        addListener(R.id.RequestDetail_receive_it_button);
        addListener(R.id.RequestDetail_chat_button);
        initComment();
    }

    /**
     * 为控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (res){
                    case R.id.RequestDetail_star_button:
                        ResReqDetailBottomButtonLayout temp = findViewById(R.id.RequestDetail_star_button);
                        if(requestStarred) {
                            Toast.makeText(MyApplication.getContext(), "已取消收藏", Toast.LENGTH_SHORT).show();
                            temp.setText("收藏");
                            temp.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star));
                        }
                        else{
                            Toast.makeText(MyApplication.getContext(), "已收藏", Toast.LENGTH_SHORT).show();
                            temp.setText("已收藏");
                            temp.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star_yellow));
                        }
                        requestStarred = !requestStarred;
                        break;
                    case R.id.RequestDetail_receive_it_button:
                        receiveButton.setText(getString(R.string.received));
                        receiveButton.setClickable(false);
                        intent = new Intent();
                        intent.putExtra(MainActivity.REQUEST_DETAIL_REQUEST_ITEM_POSITION, requestItemPosition);
                        setResult(RESULT_OK, intent);
                        Toast.makeText(MyApplication.getContext(), "您已接单，请主动联系对方", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RequestDetail_comment_it_button:
                    case R.id.RequestDetail_chat_button:
                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    /**
     * 添加一些评论
     */
    public void initComment(){
        LinearLayout commentHolder = findViewById(R.id.RequestDetail_comment_holder);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_head);
        for(int i = 1; i < 12; i++){
            CommentItemLayout commentItemLayout = new CommentItemLayout(this);
            String time = "2018-" + i + "-25 21:29";
            commentItemLayout.setCommentItemDetail(bitmap, getString(R.string.solider),time , getString(R.string.virtualComment));
            commentHolder.addView(commentItemLayout);
        }
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
}
