package com.example.zzu.huzhucommunity.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.customlayout.CommentItemLayout;

public class ResourceDetailActivity extends AppCompatActivity {

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

        initComment();
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
