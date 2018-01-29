package com.example.zzu.huzhucommunity.commonclass;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by FEI on 2018/1/29.
 * 存储用户收到的新消息的信息
 * 包括发送方ID，用户名，第一条信息，消息数以及最后发送时间
 * 最后发送时间表示为字符串
 * 尽管字符串可能造成不妥，但可以使用自定义或者其他方法进行解析
 * 实际上这个类还可以承担更多的设计服务端交互的任务，此处忽略
 */

public class NewMessagesItem {
    private int senderID;
    private String senderName;
    private String firstNewMessage;
    private String messageTime;
    private int newMessageAmount;

    public NewMessagesItem(int senderID, String senderName, String firstNewMessage, int newMessageAmount, String messageTime){
        this.senderID = senderID;
        this.senderName = senderName;
        this.firstNewMessage = firstNewMessage;
        this.newMessageAmount = newMessageAmount;
        this.messageTime = messageTime;
    }
    public int getSenderID() {
        return senderID;
    }
    public String getSenderName() {
        return senderName;
    }
    public String getFirstNewMessage() {
        return firstNewMessage;
    }
    public int getNewMessageAmount() {
        return newMessageAmount;
    }
    public String getMessageTime() {
        return messageTime;
    }
}
