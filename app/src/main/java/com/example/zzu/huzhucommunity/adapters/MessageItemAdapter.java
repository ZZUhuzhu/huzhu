package com.example.zzu.huzhucommunity.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.NewMessagesItem;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/29.
 * 消息列表的RecyclerView的适配器
 */

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemAdapter.ViewHolder> {
    private ArrayList<NewMessagesItem> messagesItems;

    public MessageItemAdapter(ArrayList<NewMessagesItem> messagesItems){
        this.messagesItems = messagesItems;
    }
    @Override
    public MessageItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_message_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageItemAdapter.ViewHolder holder, int position) {
        NewMessagesItem messagesItem = messagesItems.get(position);
        holder.sendNameTextView.setText(messagesItem.getSenderName());
        holder.firstNewMessageTextView.setText(messagesItem.getFirstNewMessage());
        holder.messageTimeTextView.setText(messagesItem.getMessageTime());
        int amount = messagesItem.getNewMessageAmount();
        if(amount > 99){
            holder.messageAmountButton.setText(MyApplication.getContext().getString(R.string.ninetyMore));
        }
        else {
            String text = "" + amount;
            holder.messageAmountButton.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        return messagesItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton headButton;
        TextView sendNameTextView;
        TextView firstNewMessageTextView;
        TextView messageTimeTextView;
        Button messageAmountButton;
        ViewHolder(View itemView) {
            super(itemView);
            headButton = itemView.findViewById(R.id.new_message_item_head_button);
            sendNameTextView = itemView.findViewById(R.id.new_message_item_sender_name_text_view);
            messageTimeTextView = itemView.findViewById(R.id.new_message_item_time_text_view);
            firstNewMessageTextView = itemView.findViewById(R.id.new_message_item_first_message_text_view);
            messageAmountButton = itemView.findViewById(R.id.new_message_item_message_amount_button);
        }
    }
}
