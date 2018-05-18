package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.zzu.huzhucommunity.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by FEI on 2018/2/8.
 * 保存评论的内容
 */

public class CommentItem {
    private int userID;
    private String  userName;
    private long timeInMills;
    private String content;
    private String userHeadUrl;
    private String commentDate;
    private String commentFather;
    private Bitmap userHeadBitmap;

    public CommentItem(String content, long timeInMills){
        this.userID = 0;
        this.userName = MyApplication.getContext().getString(R.string.solider);
        this.userHeadBitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.profile_head_over_watch);
        this.content = content;
        this.timeInMills = timeInMills;
    }

    public CommentItem(int userID, String userName, String content, long timeInMills, Bitmap userHeadBitmap){
        this.userID = userID;
        this.userName = userName;
        this.content = content;
        this.timeInMills = timeInMills;
        this.userHeadBitmap = userHeadBitmap;
    }
    public CommentItem(int userID, String userName, String content, String commentDate, Bitmap userHeadBitmap){
        this.userID = userID;
        this.userName = userName;
        this.content = content;
        this.commentDate = commentDate;
        this.userHeadBitmap = userHeadBitmap;
    }
    public CommentItem(int userID, String userName, String content, String commentFa, String userHeadUrl, String commentDate){
        this.userID = userID;
        this.userName = userName;
        this.content = content;
        this.commentDate = commentDate;
        this.userHeadUrl = userHeadUrl;
        this.commentFather = commentFa;
        this.userHeadBitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.profile_head);
    }

    public void setUserHeadBitmap(Bitmap userHeadBitmap) {
        this.userHeadBitmap = userHeadBitmap;
    }

    public String getUserName() {
        return userName;
    }

    public Bitmap getUserHeadBitmap() {
        return userHeadBitmap;
    }

    public String getTimeString(){
        return Utilities.convertTimeInMillToString(timeInMills);
    }

    public int getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getCommentFather() {
        return commentFather;
    }

    public void setCommentFather(String commentFather) {
        this.commentFather = commentFather;
    }
}
