package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.example.zzu.huzhucommunity.R;

/**
 * Created by FEI on 2018/1/30.
 * 保存聊天界面每个消息信息的类
 */

public class ChatRoomMessageItem {
    private boolean typeImage;
    private boolean positionStart;
    private String messageText;
    private Bitmap imageBitmap;
    public ChatRoomMessageItem(boolean start, String messageText){
        this.positionStart = start;
        this.messageText = messageText;
        this.typeImage = false;
        imageBitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.profile_head);
    }
    public ChatRoomMessageItem(boolean start, Bitmap imageBitmap){
        this.positionStart = start;
        this.typeImage = true;
        this.imageBitmap = imageBitmap;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public boolean isTypeImage() {
        return typeImage;
    }

    public String getMessageText() {
        return messageText;
    }

    public boolean isPositionStart() {
        return positionStart;
    }
}
