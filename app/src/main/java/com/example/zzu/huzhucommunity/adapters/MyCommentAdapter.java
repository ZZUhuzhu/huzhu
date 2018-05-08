package com.example.zzu.huzhucommunity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.CommentItem;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.customlayout.CommentItemLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by FEI on 2018/2/8.
 * 我的评论活动中的RecyclerView适配器
 */

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommentItem> list;

    public MyCommentAdapter(Context context, ArrayList<CommentItem> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_comment_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentItem item = list.get(position);
        holder.userNameTextView.setText(item.getUserName());
        holder.timeTextView.setText(item.getTimeString());
        holder.headImageView.setImageBitmap(item.getUserHeadBitmap());
        holder.contentTextView.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView headImageView;
        TextView userNameTextView, timeTextView, contentTextView, deleteTextView;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setFocusable(true);
            headImageView = itemView.findViewById(R.id.CommentItem_user_head_image);
            userNameTextView = itemView.findViewById(R.id.CommentItem_user_name_text_view);
            timeTextView = itemView.findViewById(R.id.CommentItem_time_text_view);
            contentTextView = itemView.findViewById(R.id.CommentItem_content_text_view);
            deleteTextView = itemView.findViewById(R.id.CommentItem_delete_text_view);
            deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    list.remove(pos);
                    notifyItemRemoved(pos);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
