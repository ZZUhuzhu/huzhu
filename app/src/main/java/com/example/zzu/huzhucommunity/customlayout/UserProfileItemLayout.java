package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;

/**
 * Created by FEI on 2018/1/25.
 * 用户信息中每一个设置项的布局
 */

public class UserProfileItemLayout extends LinearLayout {
    TextView settingAmountTextView;

    public UserProfileItemLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_user_profile_item_view, this, true);
        ImageView settingIconImageView = findViewById(R.id.UserProfileItem_setting_icon_image_view);
        TextView settingTextView = findViewById(R.id.UserProfileItem_setting_text_view);
        settingAmountTextView = findViewById(R.id.UserProfileItem_setting_amount_text_view);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserProfileItemLayout);
        settingIconImageView.setImageResource(typedArray.getResourceId(R.styleable.UserProfileItemLayout_image_src, R.drawable.default_image));
        settingTextView.setText(typedArray.getResourceId(R.styleable.UserProfileItemLayout_item_text, R.string.app_name));
        if (typedArray.getBoolean(R.styleable.UserProfileItemLayout_amount_visibility, true))
            settingAmountTextView.setVisibility(VISIBLE);
        else
            settingAmountTextView.setVisibility(GONE);
        typedArray.recycle();
    }

    /**
     * 设置个人信息中每一项显示的数量
     * @param amount 需要显示的数字
     */
    public void setAmount(int amount){
        if (amount == 0)
            settingAmountTextView.setVisibility(GONE);
        String tmp = "" + amount;
        settingAmountTextView.setVisibility(VISIBLE);
        settingAmountTextView.setText(tmp);
    }
}
