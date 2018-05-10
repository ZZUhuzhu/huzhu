package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.UserProfile;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.UserProfileItemLayout;

import java.util.Objects;

public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_layout);
        Toolbar toolbar = findViewById(R.id.UserProfile_me_top_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView userNameTextView = findViewById(R.id.UserProfile_me_name_text_view);
        ImageView userHeadImageView = findViewById(R.id.UserProfile_me_image_view);
        userNameTextView.setText(R.string.solider);
        userHeadImageView.setImageDrawable(getDrawable(R.drawable.profile_head_over_watch));

        addListener(R.id.UserProfile_resource_published_item);
        addListener(R.id.UserProfile_resource_received_item);
        addListener(R.id.UserProfile_setting_item);
        addListener(R.id.UserProfile_star_item);
        addListener(R.id.UserProfile_track_item);
        addListener(R.id.UserProfile_comment_item);
        addListener(R.id.UserProfile_message_item);
        addListener(R.id.UserProfile_me_holder);
        addListener(R.id.UserProfile_top_background_image_view);

        initNumberAndUserHead();
    }
    /**
     * 为每个控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.UserProfile_setting_item:
                        SettingActivity.startMe(UserProfileActivity.this);
                        break;
                    case R.id.UserProfile_message_item:
                        MessagesActivity.startMe(UserProfileActivity.this);
                        break;
                    case R.id.UserProfile_resource_published_item:
                        PubRecStarTrackActivity.startMe(UserProfileActivity.this, PubRecStarTrackActivity.TYPE_PUBLISH);
                        break;
                    case R.id.UserProfile_resource_received_item:
                        PubRecStarTrackActivity.startMe(UserProfileActivity.this, PubRecStarTrackActivity.TYPE_RECEIVE);
                        break;
                    case R.id.UserProfile_star_item:
                        PubRecStarTrackActivity.startMe(UserProfileActivity.this, PubRecStarTrackActivity.TYPE_STAR);
                        break;
                    case R.id.UserProfile_track_item:
                        PubRecStarTrackActivity.startMe(UserProfileActivity.this, PubRecStarTrackActivity.TYPE_TRACK);
                        break;
                    case R.id.UserProfile_comment_item:
                        MyCommentActivity.startMe(UserProfileActivity.this);
                        break;
                    case R.id.UserProfile_me_holder:
                        AccountProfileActivity.startMe(UserProfileActivity.this);
                        break;
                    case R.id.UserProfile_top_background_image_view:
                        Utilities.startPickImageDialog(UserProfileActivity.this);
                        break;
                }
            }
        });
    }

    public void initNumberAndUserHead(){
        String userName = Utilities.GetLoginUserUserName();
        if (!Objects.equals(userName, Utilities.DEF_STRING_VALUE_SHARED_PREFERENCE))
            ((TextView)findViewById(R.id.UserProfile_me_name_text_view)).setText(userName);
        Bitmap bitmap = Utilities.GetLoginUserHeadBitmapFromSP();
        if (bitmap == null){
            UserProfile.getOurInstance().getImageBitmapByUrl(Utilities.GetLoginUserHeadUrl(), new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == UserProfile.GET_IMAGE_BITMAP_BY_URL){
                        Bitmap bitmap = (Bitmap) msg.obj;
                        if (bitmap != null){
                            ((ImageView) findViewById(R.id.UserProfile_me_image_view)).setImageBitmap(bitmap);
                            Utilities.SaveLoginUserHeadBitmap(bitmap);
                        }
                    }
                    return false;
                }
            }));
        }
        else {
            ((ImageView) findViewById(R.id.UserProfile_me_image_view)).setImageBitmap(bitmap);
        }

        UserProfileItemLayout itemLayout = findViewById(R.id.UserProfile_resource_published_item);
        itemLayout.setAmount(0);
        itemLayout = findViewById(R.id.UserProfile_resource_received_item);
        itemLayout.setAmount(1);
        itemLayout = findViewById(R.id.UserProfile_star_item);
        itemLayout.setAmount(2);
        itemLayout = findViewById(R.id.UserProfile_track_item);
        itemLayout.setAmount(3);
        itemLayout = findViewById(R.id.UserProfile_comment_item);
        itemLayout.setAmount(4);
        itemLayout = findViewById(R.id.UserProfile_message_item);
        itemLayout.setAmount(5);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.Setting_menu_item:
                findViewById(R.id.UserProfile_setting_item).performClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_setting_menu_item, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Utilities.PICK_IMAGE_FROM_CAMERA:
            case Utilities.PICK_IMAGE_FROM_GALLERY:
                Bitmap bitmap = Utilities.getImageFromDialog(requestCode, resultCode, data);
                if (bitmap != null){
                    ((ImageView) findViewById(R.id.UserProfile_top_background_image_view)).setImageBitmap(bitmap);
                }
                break;
        }
    }

    public static void startMe(Context context){
        context.startActivity(new Intent(context, UserProfileActivity.class));
    }
}
