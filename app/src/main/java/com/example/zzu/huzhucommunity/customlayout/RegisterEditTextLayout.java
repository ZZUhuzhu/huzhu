package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;

/**
 * Created by FEI on 2018/1/23.
 * 自定义账号注册界面的输入框及指示文字
 */

public class RegisterEditTextLayout extends LinearLayout {
    private EditText editText;
    public RegisterEditTextLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.register_input_custom_layout, this);
        TextView textView = findViewById(R.id.register_input_custom_text_view);
        editText = findViewById(R.id.register_input_custom_edit_text);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RegisterEditTextLayout);
        String temp = typedArray.getString(R.styleable.RegisterEditTextLayout_text);
        textView.setText(String.format("%s:", temp));
        boolean inputPassword = typedArray.getBoolean(R.styleable.RegisterEditTextLayout_input_password, false);
        if(inputPassword) editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        typedArray.recycle();
    }

    /**
     * 获取输入框中用户输入的文字
     * @return 用户输入的文字
     */
    public String getText(){
        return editText.getText().toString();
    }
}
