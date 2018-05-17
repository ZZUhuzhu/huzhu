package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.CommentItem;

/**
 * Created by FEI on 2018/1/25.
 * 评论的每一项的布局
 */

public class CommentItemLayout extends RelativeLayout {
    private ImageView userHeadImageView;
    private TextView userNameTextView, commentTimeTextView, commentContentTextView, commentDeleteTextView;
    public CommentItemLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.custom_comment_item_layout, this);
        userHeadImageView = findViewById(R.id.CommentItem_user_head_image);
        userNameTextView = findViewById(R.id.CommentItem_user_name_text_view);
        commentTimeTextView = findViewById(R.id.CommentItem_time_text_view);
        commentContentTextView = findViewById(R.id.CommentItem_content_text_view);
        commentDeleteTextView = findViewById(R.id.CommentItem_delete_text_view);
        commentDeleteTextView.setVisibility(VISIBLE);
    }
    public CommentItemLayout(Context context, int deleteVisibility){
        this(context);
        commentDeleteTextView.setVisibility(deleteVisibility);
    }
    public CommentItemLayout(Context context, int deleteVisibility, CommentItem commentItem){
        this(context, deleteVisibility);
        userHeadImageView.setImageBitmap(commentItem.getUserHeadBitmap());
        userNameTextView.setText(commentItem.getUserName());
        commentContentTextView.setText(commentItem.getContent());
        commentTimeTextView.setText(commentItem.getTimeString());
    }
    /**
     * 设置评论的详细内容
     * 包括评论用户的头，用户名，评论时间以及评论内容
     * @param userHead 用户头像
     * @param userName 用户名
     * @param commentTime 评论时间
     * @param commentContent 评论内容
     */
    public void setCommentItemDetail(Bitmap userHead, String userName, String commentTime, String commentContent){
        userHeadImageView.setImageBitmap(userHead);
        userNameTextView.setText(userName);
        commentContentTextView.setText(commentContent);
        commentTimeTextView.setText(commentTime);
    }

    /**
     * 设置评论的用户头像
     * @param bitmap 用户头像
     */
    public void setCommentUserHead(Bitmap bitmap){
        userHeadImageView.setImageBitmap(bitmap);
    }
}
