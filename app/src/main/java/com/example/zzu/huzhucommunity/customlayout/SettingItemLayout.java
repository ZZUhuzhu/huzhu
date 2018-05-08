package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;

/**
 * Created by FEI on 2018/2/1.
 * 设置界面每一项的布局
 */

public class SettingItemLayout extends LinearLayout {
    private Switch tmpSwitch;
    public SettingItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_setting_item_view, this);
        TextView textView = findViewById(R.id.setting_item_text_view);
        tmpSwitch = findViewById(R.id.setting_item_switch);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemLayout);
        textView.setText(typedArray.getString(R.styleable.SettingItemLayout_android_text));
        boolean switch_on = typedArray.getBoolean(R.styleable.SettingItemLayout_switch_on, false);
        if(switch_on)
            tmpSwitch.setVisibility(VISIBLE);
        else
            tmpSwitch.setVisibility(GONE);
        typedArray.recycle();
    }
    public void setOnCheckStatusListener(CompoundButton.OnCheckedChangeListener checkStatusListener){
        tmpSwitch.setOnCheckedChangeListener(checkStatusListener);
    }
    public void changeCheckStatus(){
        if(tmpSwitch.isChecked())
            tmpSwitch.setChecked(false);
        else
            tmpSwitch.setChecked(true);
    }
}
