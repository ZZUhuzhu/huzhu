package com.example.zzu.huzhucommunity.commonclass;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by FEI on 2018/1/29.
 * 存储用户收到的新消息的信息
 * 包括发送方ID，用户名，用户头像，第一条信息，消息数，最后发送时间
 * 最后发送时间表示为字符串
 * 尽管字符串可能造成不妥，但可以使用自定义或者其他方法进行解析
 * 实际上这个类还可以承担更多的设计服务端交互的任务，此处忽略
 */

public class NewMessagesItem implements Parcelable {
    private boolean scrolled;
    private boolean read;
    private int senderID;
    private String senderName;
    private String firstNewMessage;
    private long messageTimeInMills;
    private String senderHead;
    private int newMessageAmount;

    public NewMessagesItem(int senderID, String senderName, String firstNewMessage, int newMessageAmount, long messageTimeInMills, String senderHead){
        this.senderID = senderID;
        this.senderName = senderName;
        this.firstNewMessage = firstNewMessage;
        this.newMessageAmount = newMessageAmount;
        this.messageTimeInMills = messageTimeInMills;
        this.senderHead = senderHead;
        this.read = false;
    }

    private NewMessagesItem(Parcel in) {
        scrolled = in.readByte() != 0;
        read = in.readByte() != 0;
        senderID = in.readInt();
        senderName = in.readString();
        firstNewMessage = in.readString();
        messageTimeInMills = in.readLong();
        senderHead = in.readString();
        newMessageAmount = in.readInt();
    }

    public static final Creator<NewMessagesItem> CREATOR = new Creator<NewMessagesItem>() {
        @Override
        public NewMessagesItem createFromParcel(Parcel in) {
            return new NewMessagesItem(in);
        }

        @Override
        public NewMessagesItem[] newArray(int size) {
            return new NewMessagesItem[size];
        }
    };

    public void setScrolled(boolean scrolled) {
        this.scrolled = scrolled;
    }

    public boolean isScrolled() {
        return scrolled;
    }

    public int getSenderID() {
        return senderID;
    }

    public boolean isRead() {return read;}
    public String getSenderName() {
        return senderName;
    }
    public String getFirstNewMessage() {
        return firstNewMessage;
    }
    public int getNewMessageAmount() {return newMessageAmount;}
    public String getMessageTimeString(){
        return Utilities.convertTimeInMillToString(messageTimeInMills);
    }
    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (scrolled ? 1 : 0));
        dest.writeByte((byte) (read ? 1 : 0));
        dest.writeInt(senderID);
        dest.writeString(senderName);
        dest.writeString(firstNewMessage);
        dest.writeLong(messageTimeInMills);
        dest.writeString(senderHead);
        dest.writeInt(newMessageAmount);
    }
}
