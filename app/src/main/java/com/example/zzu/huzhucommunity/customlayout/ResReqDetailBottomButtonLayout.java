package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;

/**
 * Created by FEI on 2018/1/26.
 * 资源详情页面底部四个BUTTON的自定义布局
 */

public class ResReqDetailBottomButtonLayout extends LinearLayout {
    public ResReqDetailBottomButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.resource_detail_bottom_button_view, this);
        TextView buttonTextView = findViewById(R.id.ResourceDetailBottomButton_text_view);
        ImageView buttonImageView = findViewById(R.id.ResourceDetailBottomButton_image_view);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ResReqDetailBottomButtonLayout);
        buttonTextView.setText(typedArray.getResourceId(R.styleable.ResReqDetailBottomButtonLayout_android_text, R.string.app_name));
        buttonTextView.setTextColor(typedArray.getColor(R.styleable.ResReqDetailBottomButtonLayout_android_textColor, Color.GRAY));
        buttonImageView.setImageResource(typedArray.getResourceId(R.styleable.ResReqDetailBottomButtonLayout_android_src, R.drawable.star));
        typedArray.recycle();
        setClickable(true);
        setFocusable(true);
    }

    /**
     * 设置按钮的图标
     * @param bitmap 需要设置的图标
     */
    public void setImageBitmap(Bitmap bitmap){
        ImageView buttonImageView = findViewById(R.id.ResourceDetailBottomButton_image_view);
        buttonImageView.setImageBitmap(bitmap);
    }

    /**
     * 设置按钮的文字
     * @param text 需要设置的文字
     */
    public void setText(String text){
        TextView buttonTextView = findViewById(R.id.ResourceDetailBottomButton_text_view);
        buttonTextView.setText(text);
    }
}
