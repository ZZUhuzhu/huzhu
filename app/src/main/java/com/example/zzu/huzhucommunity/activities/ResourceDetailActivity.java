package com.example.zzu.huzhucommunity.activities;

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
import com.example.zzu.huzhucommunity.customlayout.CommentItemLayout;
import com.example.zzu.huzhucommunity.customlayout.ResourceDetailBottomButtonLayout;

public class ResourceDetailActivity extends AppCompatActivity {
    private static final String TAG = "ResourceDetailActivity";
    private boolean resStarred = false;

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
        addListener(R.id.ResourceDetail_star_button);
        addListener(R.id.ResourceDetail_comment_it_button);
        addListener(R.id.ResourceDetail_receive_it_button);
        addListener(R.id.ResourceDetail_chat_button);
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
                    case R.id.ResourceDetail_star_button:
                        ResourceDetailBottomButtonLayout temp = findViewById(R.id.ResourceDetail_star_button);
                        if(resStarred) {
                            Toast.makeText(ResourceDetailActivity.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                            temp.setText("收藏");
                            temp.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star));
                        }
                        else{
                            Toast.makeText(ResourceDetailActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                            temp.setText("已收藏");
                            temp.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star_yellow));
                        }
                        resStarred = !resStarred;
                        break;
                    case R.id.ResourceDetail_comment_it_button:
                    case R.id.ResourceDetail_receive_it_button:
                    case R.id.ResourceDetail_chat_button:
                        Toast.makeText(ResourceDetailActivity.this, "正在全力开发中...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    /**
     * 添加一些评论
     */
    public void initComment(){
        LinearLayout commentHolder = findViewById(R.id.ResourceDetail_comment_holder);
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
