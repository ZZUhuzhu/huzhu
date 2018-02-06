package com.example.zzu.huzhucommunity.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.ChatRoomMessageItem;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/30.
 * 发消息界面的消息RecyclerView的Adapter
 */

public class ChatRoomMessageAdapter extends RecyclerView.Adapter<ChatRoomMessageAdapter.ViewHolder>{
    private ArrayList<ChatRoomMessageItem> list;
    private Bitmap userHeadBitmap;
    private Bitmap senderHeadBitmap;

    public ChatRoomMessageAdapter(ArrayList<ChatRoomMessageItem> list, Bitmap userHeadBitmap, Bitmap senderHeadBitmap){
        this.list = list;
        this.userHeadBitmap = userHeadBitmap;
        this.senderHeadBitmap = senderHeadBitmap;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.recycler_chat_room_message_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatRoomMessageItem item = list.get(position);
        if(item.isPositionStart()){
            if(item.isTypeImage()){
                holder.messageTextViewStart.setText("");
                holder.messageImageViewStart.setImageBitmap(item.getImageBitmap());
            }
            else{
                holder.messageImageViewStart.setImageBitmap(null);
                holder.messageTextViewStart.setText(item.getMessageText());
            }
            holder.headImageViewStart.setImageBitmap(senderHeadBitmap);
            holder.holderViewStart.setVisibility(View.VISIBLE);
            holder.holderViewEnd.setVisibility(View.GONE);
        }
        else{
            if(item.isTypeImage()){
                holder.messageTextViewEnd.setText("");
                holder.messageImageViewEnd.setImageBitmap(item.getImageBitmap());
            }
            else{
                holder.messageImageViewEnd.setImageBitmap(null);
                holder.messageTextViewEnd.setText(item.getMessageText());
            }
            holder.headImageViewEnd.setImageBitmap(userHeadBitmap);
            holder.holderViewStart.setVisibility(View.GONE);
            holder.holderViewEnd.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headImageViewStart, headImageViewEnd, messageImageViewStart, messageImageViewEnd;
        RelativeLayout holderViewStart, holderViewEnd;
        TextView messageTextViewStart, messageTextViewEnd;
        ViewHolder(View itemView) {
            super(itemView);
            messageImageViewStart = itemView.findViewById(R.id.chat_room_image_message_image_view_start);
            messageImageViewEnd = itemView.findViewById(R.id.chat_room_image_message_image_view_end);
            headImageViewStart = itemView.findViewById(R.id.chat_room_message_head_image_start);
            headImageViewEnd = itemView.findViewById(R.id.chat_room_message_head_image_end);
            messageTextViewStart = itemView.findViewById(R.id.chat_room_message_text_view_start);
            messageTextViewEnd= itemView.findViewById(R.id.chat_room_message_text_view_end);
            holderViewStart = itemView.findViewById(R.id.chat_room_message_start_holder);
            holderViewEnd = itemView.findViewById(R.id.chat_room_message_end_holder);
        }
    }
}
