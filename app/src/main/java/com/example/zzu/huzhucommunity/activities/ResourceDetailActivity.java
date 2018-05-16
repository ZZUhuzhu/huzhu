package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.ResourceDesc;
import com.example.zzu.huzhucommunity.commonclass.CommentItem;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.CommentItemLayout;
import com.example.zzu.huzhucommunity.customlayout.ResReqDetailBottomButtonLayout;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ResourceDetailActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String RES_ID_EXTRA = "resID";
    private static final int LOAD_USER_HEAD = 1;

    private LinearLayout commentHolder, imageHolder;
    private boolean resStarred = false;
    private ResReqDetailBottomButtonLayout receiveButton;

    private TextView userNameTextView, userLastLoginTextView;
    private ImageView userHeadView;
    private TextView priceTextView, resDetail, deadlineTextView;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case LOAD_USER_HEAD:
                    userHeadView.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail_layout);
        Toolbar toolbar = findViewById(R.id.ResourceDetail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String resID = getIntent().getStringExtra(RES_ID_EXTRA);

        imageHolder = findViewById(R.id.ResourceDetail_res_images_holder);
        receiveButton = findViewById(R.id.ResourceDetail_receive_it_button);
        userNameTextView = findViewById(R.id.ResourceDetail_res_user_name_text_view);
        userLastLoginTextView = findViewById(R.id.ResourceDetail_user_active_time_text_view);
        priceTextView = findViewById(R.id.ResourceDetail_res_price_text_view);
        resDetail = findViewById(R.id.ResourceDetail_res_desc_text_view);
        deadlineTextView = findViewById(R.id.ResourceDetail_deadline_text_view);
        userHeadView = findViewById(R.id.ResourceDetail_res_user_image_view);

        addListener(R.id.ResourceDetail_star_button);
        addListener(R.id.ResourceDetail_comment_it_button);
        addListener(R.id.ResourceDetail_receive_it_button);
        addListener(R.id.ResourceDetail_chat_button);
        addListener(R.id.ResourceDetail_res_user_image_view);
        initComment();

        if (Utilities.GetSettingOption(Utilities.RECORD_TRACK_KEY))
            ResourceDesc.getOurInstance().addToTrack(Utilities.GetStringLoginUserId(), resID, this);
        ResourceDesc.getOurInstance().getResPublisherInfo(resID, this);
        ResourceDesc.getOurInstance().getResourceDesc(resID, this);
        ResourceDesc.getOurInstance().getResourceComment(resID, this);
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
                    case R.id.ResourceDetail_star_button:
                        ResReqDetailBottomButtonLayout temp = findViewById(R.id.ResourceDetail_star_button);
                        if(resStarred) {
                            Toast.makeText(MyApplication.getContext(), "已取消收藏", Toast.LENGTH_SHORT).show();
                            temp.setText("收藏");
                            temp.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star));
                        }
                        else{
                            Toast.makeText(MyApplication.getContext(), "已收藏", Toast.LENGTH_SHORT).show();
                            temp.setText("已收藏");
                            temp.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star_yellow));
                        }
                        resStarred = !resStarred;
                        break;
                    case R.id.ResourceDetail_receive_it_button:
                        new AlertDialog.Builder(ResourceDetailActivity.this)
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
                    case R.id.ResourceDetail_comment_it_button:
                        final EditText tmpEditText = new EditText(ResourceDetailActivity.this);
                        tmpEditText.setBackground(null);
                        tmpEditText.setSingleLine(false);
                        int tmpPaddingPx = (int) Utilities.convertDpToPixel(10);
                        tmpEditText.setPadding(tmpPaddingPx, tmpPaddingPx, tmpPaddingPx, tmpPaddingPx);
                        new AlertDialog.Builder(ResourceDetailActivity.this)
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
                    case R.id.ResourceDetail_chat_button:
                        ChatRoomActivity.startMe(ResourceDetailActivity.this);
                        break;
                    case R.id.ResourceDetail_res_user_name_text_view:
                    case R.id.ResourceDetail_res_user_image_view:
                        OthersProfileActivity.startMe(ResourceDetailActivity.this, -1);
                        break;
                }
            }
        });
    }

    /**
     * 添加一些评论
     */
    public void initComment(){
        commentHolder = findViewById(R.id.ResourceDetail_comment_holder);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_head_over_watch);
        String time;
        CommentItemLayout commentItemLayout;
        for(int i = 1; i < 12; i++){
            commentItemLayout = new CommentItemLayout(this, View.GONE);
            time = "2018-" + i + "-25 21:29";
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
        commentHolder = findViewById(R.id.ResourceDetail_comment_holder);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_head_over_watch);
        long time = GregorianCalendar.getInstance().getTimeInMillis();
        CommentItemLayout commentItemLayout =
                new CommentItemLayout(this, View.GONE, new CommentItem(1, getString(R.string.solider), text, time, bitmap));
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

    public static void startMe(Context context, String resID){
        Intent intent = new Intent(context, ResourceDetailActivity.class);
        intent.putExtra(RES_ID_EXTRA, resID);
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        if (statusCode != 200)
            return;
        switch (requestCode){
            case ResourceDesc.GET_RESOURCE_DESC:
                resDetail.setText(mp.get(ResourceDesc.RESOURCE_DETAIL_JSON_KEY));
                priceTextView.setText(mp.get(ResourceDesc.RESOURCE_PRICE_JSON_KEY));
                deadlineTextView.setText(mp.get(ResourceDesc.DEADLINE));
                int num = Integer.parseInt(mp.get(ResourceDesc.IMAGE_NUMBERS));
                for (int i = 0; i < num; i++){
                    ImageView tmp = new ImageView(this);
                    tmp.setScaleType(ImageView.ScaleType.FIT_XY);
                    tmp.setImageResource(R.drawable.profile_head_over_watch);
                    imageHolder.addView(tmp);
                }
                break;
            case ResourceDesc.GET_RES_PUBLISHER_INFO:
                String userName = mp.get(ResourceDesc.RESOURCE_USERNAME_JSON_KEY);
                userNameTextView.setText(userName);
                String userLastLogin = mp.get(ResourceDesc.RESOURCE_USER_LAST_LOGIN_JSON_KEY);
                userLastLoginTextView.setText(String.format("上次登录:%s", userLastLogin.substring(0, userLastLogin.length() - 3)));
                final String userHead = mp.get(ResourceDesc.RESOURCE_USER_HEAD_JSON_KEY);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try {
                            URL url = new URL(userHead);
                            con = (HttpURLConnection) url.openConnection();
                            Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
                            Message message = new Message();
                            message.what = LOAD_USER_HEAD;
                            message.obj = bitmap;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            if (con != null) {
                                con.disconnect();
                            }
                        }
                    }
                }).start();
                break;
            case ResourceDesc.GET_RESOURCE_COMMENT:
                break;
            case ResourceDesc.PUBLISH_COMMENT:
                break;
            case ResourceDesc.UPDATE_STAR:
                    break;
            case ResourceDesc.RECEIVE_RESOURCE:
                break;
            case ResourceDesc.ADD_TO_TRACK:
                break;
        }
    }

    @Override
    public void onError(int statusCode) {

    }
}
