package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.customlayout.CommentItemLayout;
import com.example.zzu.huzhucommunity.customlayout.ResReqDetailBottomButtonLayout;

public class RequestDetailActivity extends BaseActivity {
    private boolean requestStarred = false;
    private ResReqDetailBottomButtonLayout receiveButton;
    private LinearLayout commentHolder;

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

        receiveButton = findViewById(R.id.RequestDetail_receive_it_button);

        addListener(R.id.RequestDetail_star_button);
        addListener(R.id.RequestDetail_comment_it_button);
        addListener(R.id.RequestDetail_receive_it_button);
        addListener(R.id.RequestDetail_chat_button);
        addListener(R.id.RequestDetail_request_user_image_view);
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
                        new AlertDialog.Builder(RequestDetailActivity.this)
                                .setCancelable(true)
                                .setTitle("确认接单?")
                                .setMessage("本平台暂不提供线上接单功能，确认接单后请您主动联系对方")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        receiveButton.setText(getString(R.string.received));
                                        receiveButton.setClickable(false);
                                        dialog.dismiss();
                                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                        break;
                    case R.id.RequestDetail_comment_it_button:
                        final EditText tmpEditText = new EditText(RequestDetailActivity.this);
                        tmpEditText.setBackground(null);
                        tmpEditText.setSingleLine(false);
                        int tmpPaddingPx = (int)MyApplication.convertDpToPixel(10);
                        tmpEditText.setPadding(tmpPaddingPx, tmpPaddingPx, tmpPaddingPx, tmpPaddingPx);
                        new AlertDialog.Builder(RequestDetailActivity.this)
                                .setView(tmpEditText)
                                .setTitle("评论")
                                .setCancelable(true)
                                .setPositiveButton("写完了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        addComment(tmpEditText.getText().toString());
                                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                        break;
                    case R.id.RequestDetail_chat_button:
                        ChatRoomActivity.startMe(RequestDetailActivity.this);
                        break;
                    case R.id.RequestDetail_request_user_image_view:
                        OthersProfileActivity.startMe(RequestDetailActivity.this);
                        break;
                }
            }
        });
    }

    /**
     * 添加一些评论
     */
    public void initComment(){
        commentHolder = findViewById(R.id.RequestDetail_comment_holder);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_head);
        for(int i = 1; i < 12; i++){
            CommentItemLayout commentItemLayout = new CommentItemLayout(this);
            String time = "2018-" + i + "-25 21:29";
            commentItemLayout.setCommentItemDetail(bitmap, getString(R.string.solider),time , getString(R.string.virtualComment));
            commentHolder.addView(commentItemLayout);
        }
    }
    /**
     * 添加一项评论（临时工）
     * @param text 评论内容
     */
    public void addComment(String text){
        if (TextUtils.isEmpty(text))
            return;
        commentHolder = findViewById(R.id.RequestDetail_comment_holder);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_head);
        String time = "2018-5-25 21:29";
        CommentItemLayout commentItemLayout = new CommentItemLayout(this, bitmap, getString(R.string.solider), time, text);
        commentHolder.addView(commentItemLayout, 0);
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
        context.startActivity(new Intent(context, RequestDetailActivity.class));
    }
}
