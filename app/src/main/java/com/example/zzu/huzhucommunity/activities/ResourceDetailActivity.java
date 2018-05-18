package com.example.zzu.huzhucommunity.activities;

import android.app.Activity;
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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.HTTPConstant;
import com.example.zzu.huzhucommunity.asynchttp.ResourceDesc;
import com.example.zzu.huzhucommunity.commonclass.CommentItem;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.CommentItemLayout;
import com.example.zzu.huzhucommunity.customlayout.ResReqDetailBottomButtonLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ResourceDetailActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String RES_ID_EXTRA = "resID";
    private static final String RES_TITLE_EXTRA = "resTitle";

    private static final int LOAD_USER_HEAD = 1;
    private static final int LOAD_RES_IMAGE = 2;
    private static final int LOAD_COMMENT_USER_HEAD = 3;

    private boolean resStarred = false;
    private String resID, userID;

    private ResReqDetailBottomButtonLayout receiveButton;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout commentHolder, imageHolder;
    private TextView userNameTextView, userLastLoginTextView;
    private ImageView userHeadView;
    private TextView priceTextView, resDetail, deadlineTextView;

    private ArrayList<CommentItem> commentItems;
    private ArrayList<CommentItemLayout> commentItemLayoutList;

    private String tmpComment = null;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case LOAD_USER_HEAD:
                    userHeadView.setImageBitmap((Bitmap) msg.obj);
                    break;
                case LOAD_RES_IMAGE:
                    if (imageViewList != null && msg.arg1 < imageViewList.size()){
                        imageViewList.get(msg.arg1).setImageBitmap((Bitmap) msg.obj);
                    }
                    break;
                case LOAD_COMMENT_USER_HEAD:
                    int i = msg.arg1;
                    commentItems.get(i).setUserHeadBitmap((Bitmap) msg.obj);
                    commentItemLayoutList.get(i).setCommentUserHead((Bitmap) msg.obj);
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
            actionBar.setTitle(getIntent().getStringExtra(RES_TITLE_EXTRA));
        }
        resID = getIntent().getStringExtra(RES_ID_EXTRA);
        userID = Utilities.GetStringLoginUserId();

        commentItems = new ArrayList<>();
        commentItemLayoutList = new ArrayList<>();
        imageViewList = new ArrayList<>();
        imageHolder = findViewById(R.id.ResourceDetail_res_images_holder);
        receiveButton = findViewById(R.id.ResourceDetail_receive_it_button);
        userNameTextView = findViewById(R.id.ResourceDetail_res_user_name_text_view);
        userLastLoginTextView = findViewById(R.id.ResourceDetail_user_active_time_text_view);
        priceTextView = findViewById(R.id.ResourceDetail_res_price_text_view);
        resDetail = findViewById(R.id.ResourceDetail_res_desc_text_view);
        deadlineTextView = findViewById(R.id.ResourceDetail_deadline_text_view);
        userHeadView = findViewById(R.id.ResourceDetail_res_user_image_view);
        commentHolder = findViewById(R.id.ResourceDetail_comment_holder);

        addListener(R.id.ResourceDetail_star_button);
        addListener(R.id.ResourceDetail_comment_it_button);
        addListener(R.id.ResourceDetail_receive_it_button);
        addListener(R.id.ResourceDetail_chat_button);
        addListener(R.id.ResourceDetail_res_user_image_view);

        if (Utilities.GetSettingOption(Utilities.RECORD_TRACK_KEY))
            ResourceDesc.getOurInstance().addToTrack(userID, resID, this);
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
                        ResourceDesc.getOurInstance().updateStar(userID, resID,
                                String.valueOf(!resStarred), ResourceDetailActivity.this);
                        break;
                    case R.id.ResourceDetail_receive_it_button:
                        new AlertDialog.Builder(ResourceDetailActivity.this)
                                .setCancelable(true)
                                .setTitle("确认接单?")
                                .setMessage("本平台暂不提供线上接单功能，确认接单后请您主动联系对方")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ResourceDesc.getOurInstance().receiveResource(userID, resID, ResourceDetailActivity.this);
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
                                        tmpComment = tmpEditText.getText().toString();
                                        ResourceDesc.getOurInstance().publishComment(userID,
                                                "1", tmpComment, "0", resID, ResourceDetailActivity.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void startMe(Activity context, String resID, String resTitle, int reqCode){
        Intent intent = new Intent(context, ResourceDetailActivity.class);
        intent.putExtra(RES_ID_EXTRA, resID);
        intent.putExtra(RES_TITLE_EXTRA, resTitle);
        context.startActivityForResult(intent, reqCode);
    }

    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        if (statusCode != 200)
            return;
        switch (requestCode){
            case ResourceDesc.GET_RESOURCE_DESC:
                resDetail.setText(mp.get(ResourceDesc.RESOURCE_DETAIL_JSON_KEY));
                priceTextView.setText(String.format("%s￥", mp.get(ResourceDesc.RESOURCE_PRICE_JSON_KEY)));
                String deadline = mp.get(ResourceDesc.DEADLINE);
                if (Utilities.CheckTimeExceed(deadline)){
                    receiveButton.setText(getString(R.string.received));
                    receiveButton.setEnabled(false);
                }
                deadlineTextView.setText(deadline.substring(0, deadline.length() - 3));
                final int num = Integer.parseInt(mp.get(ResourceDesc.IMAGE_NUMBERS));
                for (int i = 0; i < num; i++){
                    ImageView tmp = new ImageView(this);
                    tmp.setImageDrawable(getDrawable(R.drawable.default_image));
                    tmp.setMinimumHeight(600);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 20, 0, 0);
                    tmp.setLayoutParams(params);
                    tmp.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageViewList.add(tmp);
                    imageHolder.addView(tmp);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i  = 1; i <= num; i++){
                            HttpURLConnection con = null;
                            try {
                                URL url = new URL(HTTPConstant.IMAGE_URL_PREFIX + resID + '_' + i + HTTPConstant.IMAGE_URL_SUFFIX);
                                con = (HttpURLConnection) url.openConnection();
                                Bitmap tmp = BitmapFactory.decodeStream(con.getInputStream());
                                Message message = new Message();
                                message.what = LOAD_RES_IMAGE;
                                message.obj = tmp;
                                message.arg1 = i - 1;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            finally {
                                if (con != null)
                                    con.disconnect();
                            }
                        }
                    }
                }).start();
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
                final int n = Integer.parseInt(mp.get(ResourceDesc.RESOURCE_NUMBER_JSON_KEY));
                for (int i = 0; i < n; i++){
                    String tmp = mp.get(i + "");
                    try {
                        JSONObject object = new JSONObject(tmp);
                        String detail = (String) object.get(ResourceDesc.COMMENTDetail),
                                date = (String) object.get(ResourceDesc.COMMENTDate),
                                fa = (String) object.get(ResourceDesc.COMMENTFather),
                                uName = (String) object.get(ResourceDesc.USERName),
                                uHead = (String) object.get(ResourceDesc.USERHead),
                                uID = (String) object.get(ResourceDesc.USERID);
                        CommentItem item = new CommentItem(Integer.parseInt(uID), uName, detail, fa, uHead, date);
                        CommentItemLayout itemLayout = new CommentItemLayout(this, View.GONE, item);
                        commentItems.add(item);
                        commentItemLayoutList.add(itemLayout);
                        commentHolder.addView(itemLayout);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < n; i++){
                            HttpURLConnection con = null;
                            try {
                                URL url = new URL(commentItems.get(i).getUserHeadUrl());
                                con = (HttpURLConnection) url.openConnection();
                                Bitmap tmp = BitmapFactory.decodeStream(con.getInputStream());
                                Message message = new Message();
                                message.what = LOAD_COMMENT_USER_HEAD;
                                message.arg1 = i;
                                message.obj = tmp;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                if (con != null) {
                                    con.disconnect();
                                }
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case ResourceDesc.PUBLISH_COMMENT:
                if (TextUtils.isEmpty(tmpComment))
                    break;
                commentHolder = findViewById(R.id.ResourceDetail_comment_holder);
                CommentItemLayout commentItemLayout =
                        new CommentItemLayout(this, View.GONE,
                                new CommentItem(Integer.parseInt(userID), Utilities.GetLoginUserUserName(),
                                        tmpComment, Utilities.GetCurFormatTime(), Utilities.GetLoginUserHeadBitmapFromSP()));
                commentHolder.addView(commentItemLayout, 0);
                break;
            case ResourceDesc.UPDATE_STAR:
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
            case ResourceDesc.RECEIVE_RESOURCE:
                receiveButton.setText(getString(R.string.received));
                receiveButton.setClickable(false);
                Toast.makeText(MyApplication.getContext(), "接单成功，请您尽快联系对方", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                break;
        }
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
    }
}
