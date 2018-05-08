package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;

/**
 * Created by FEI on 2018/2/2.
 * 账户信息中每一项的布局
 */

public class AccountProfileItemLayout extends RelativeLayout {
    private TextView contentTextView;
    private ImageView imageView;

    public AccountProfileItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_account_profile_item_view, this);

        TextView titleTextView = findViewById(R.id.account_profile_item_title_text_view);
        contentTextView = findViewById(R.id.account_profile_item_content_text_view);
        imageView = findViewById(R.id.account_profile_item_content_image_view);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AccountProfileItemLayout);
        titleTextView.setText(typedArray.getString(R.styleable.AccountProfileItemLayout_item_title));
        boolean show_icon = typedArray.getBoolean(R.styleable.AccountProfileItemLayout_show_icon, false);
        if(show_icon){
            imageView.setVisibility(VISIBLE);
            contentTextView.setVisibility(GONE);
        }
        else{
            contentTextView.setVisibility(VISIBLE);
            imageView.setVisibility(GONE);
        }
        typedArray.recycle();
    }
    public String getContentText(){
        return contentTextView.getText().toString();
    }
    public void setContent(String text){
        contentTextView.setText(text);
        contentTextView.setVisibility(VISIBLE);
        imageView.setVisibility(GONE);
    }
    public void setImageDrawable(Drawable drawable){
        imageView.setImageDrawable(drawable);
        imageView.setVisibility(VISIBLE);
        contentTextView.setVisibility(GONE);
    }
}
