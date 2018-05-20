package com.example.zzu.huzhucommunity.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.activities.ChatRoomActivity;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_new_message_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageItemAdapter.ViewHolder holder, int position) {
        NewMessagesItem messagesItem = messagesItems.get(position);
        holder.sendNameTextView.setText(messagesItem.getSenderName());
        holder.firstNewMessageTextView.setText(messagesItem.getFirstNewMessage());
        holder.messageTimeTextView.setText(messagesItem.getMessageTimeString());
        if(messagesItem.isRead()) {
            holder.messageAmountButton.setVisibility(View.GONE);
            holder.markReadTextView.setText(R.string.markUnread);
            return;
        }
        holder.messageAmountButton.setVisibility(View.VISIBLE);
        holder.markReadTextView.setText(R.string.markRead);
        int amount = messagesItem.getNewMessageAmount();
        if(amount > 99){
            holder.messageAmountButton.setText(MyApplication.getContext().getString(R.string.ninetyMore));
        }
        else {
            String text = "" + amount;
            holder.messageAmountButton.setText(text);
        }
        if (messagesItem.isScrolled())
            holder.scrollView.smoothScrollTo(holder.markReadTextView.getWidth() + holder.deleteTextView.getWidth(), 0);
        else
            holder.scrollView.smoothScrollTo(0, 0);
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
        TextView markReadTextView;
        TextView deleteTextView;
        Button messageAmountButton;
        HorizontalScrollView scrollView;
        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(View itemView) {
            super(itemView);
            headButton = itemView.findViewById(R.id.new_message_item_head_button);
            sendNameTextView = itemView.findViewById(R.id.new_message_item_sender_name_text_view);
            messageTimeTextView = itemView.findViewById(R.id.new_message_item_time_text_view);
            firstNewMessageTextView = itemView.findViewById(R.id.new_message_item_first_message_text_view);
            messageAmountButton = itemView.findViewById(R.id.new_message_item_message_amount_button);
            markReadTextView = itemView.findViewById(R.id.new_message_item_mark_read_text_view);
            scrollView = itemView.findViewById(R.id.new_message_item_scroll_view);
            deleteTextView = itemView.findViewById(R.id.new_message_item_delete_text_view);
            RelativeLayout messageHolder =itemView.findViewById(R.id.new_message_item_message_holder);

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int scrollLimitX = markReadTextView.getWidth() + deleteTextView.getWidth();
                    int adapterPos = getAdapterPosition();
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        if (scrollView.getScrollX() >= scrollLimitX / 2){
                            scrollView.smoothScrollTo(scrollLimitX, 0);
                            messagesItems.get(adapterPos).setScrolled(true);
                            return true;
                        }
                        else {
                            scrollView.smoothScrollTo(0, 0);
                            messagesItems.get(adapterPos).setScrolled(false);
                            return true;
                        }
                    }

                    return false;
                }
            });
            messageHolder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NewMessagesItem item = messagesItems.get(getAdapterPosition());
                            ChatRoomActivity.startMe(context, item.getSenderID() + "", item.getSenderName());
                            messagesItems.get(getAdapterPosition()).setRead(true);
                        }
                    });
            markReadTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = getAdapterPosition();
                            NewMessagesItem item = messagesItems.get(pos);
                            item.setRead(!item.isRead());
                            item.setScrolled(false);
                            scrollView.smoothScrollTo(0, 0);
                            notifyItemChanged(pos);
                        }
                    });
            deleteTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = getAdapterPosition();
                            messagesItems.remove(pos);
                            notifyItemRemoved(pos);
                        }
                    });
        }
    }
}
