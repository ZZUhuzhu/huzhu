package com.example.zzu.huzhucommunity.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.activities.ChatRoomActivity;
import com.example.zzu.huzhucommunity.activities.MessagesActivity;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.NewMessagesItem;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/29.
 * 消息列表的RecyclerView的适配器
 */

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemAdapter.ViewHolder> {
    private ArrayList<NewMessagesItem> messagesItems;
    private Context context;

    public MessageItemAdapter(ArrayList<NewMessagesItem> messagesItems, Context context){
        this.messagesItems = messagesItems;
        this.context = context;
    }
    @Override
    public MessageItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_message_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageItemAdapter.ViewHolder holder, int position) {
        NewMessagesItem messagesItem = messagesItems.get(position);
        holder.sendNameTextView.setText(messagesItem.getSenderName());
        holder.firstNewMessageTextView.setText(messagesItem.getFirstNewMessage());
        holder.messageTimeTextView.setText(messagesItem.getMessageTime());
        if(messagesItem.isRead()) {
            holder.messageAmountButton.setVisibility(View.GONE);
            return;
        }
        holder.messageAmountButton.setVisibility(View.VISIBLE);
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

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton headButton;
        TextView sendNameTextView;
        TextView firstNewMessageTextView;
        TextView messageTimeTextView;
        Button messageAmountButton;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewMessagesItem messagesItem1 = messagesItems.get(getLayoutPosition());
                    messagesItem1.setRead();
                    Intent intent = new Intent(context, ChatRoomActivity.class);
                    intent.putExtra(MessagesActivity.CHAT_ROOM_INTENT_EXTRA_NAME, messagesItem1);
                    context.startActivity(intent);
                }
            });
            headButton = itemView.findViewById(R.id.new_message_item_head_button);
            sendNameTextView = itemView.findViewById(R.id.new_message_item_sender_name_text_view);
            messageTimeTextView = itemView.findViewById(R.id.new_message_item_time_text_view);
            firstNewMessageTextView = itemView.findViewById(R.id.new_message_item_first_message_text_view);
            messageAmountButton = itemView.findViewById(R.id.new_message_item_message_amount_button);
        }
    }
}
