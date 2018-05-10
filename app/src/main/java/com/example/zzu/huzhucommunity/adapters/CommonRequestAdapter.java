package com.example.zzu.huzhucommunity.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.activities.RequestDetailActivity;
import com.example.zzu.huzhucommunity.commonclass.NewRequestItem;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/31.
 * 主界面新请求的RecyclerView的适配器
 */

public class CommonRequestAdapter extends RecyclerView.Adapter<CommonRequestAdapter.ViewHolder> {
    private ArrayList<NewRequestItem> list;
    private Context context;
    private boolean showStatus;
    public CommonRequestAdapter(ArrayList<NewRequestItem> list, Context context) {
        this.list = list;
        this.context = context;
        this.showStatus = false;
    }
    public CommonRequestAdapter(ArrayList<NewRequestItem> list, Context context, boolean showStatus){
        this.list = list;
        this.context = context;
        this.showStatus = showStatus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_new_resource_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewRequestItem item = list.get(position);
        holder.imageView.setImageBitmap(item.getItemThumbnail());
        holder.titleTextView.setText(item.getItemTitle());
        holder.detailTextView.setText(item.getItemDetail());
        String time = "" + item.getItemPublishTimeStr();
        holder.timeTextView.setText(time);
        String price = "" + item.getItemPrice() + "￥";
        holder.priceTextView.setText(price);
        if (showStatus){
            holder.statusHolder.setVisibility(View.VISIBLE);
            holder.deleteTextView.setVisibility(View.VISIBLE);
            if (item.isReceived()) {
                holder.statusTextView.setText(R.string.received);
                holder.statusTextView.setTextColor(Color.GRAY);
            }
            else {
                holder.statusTextView.setText(R.string.unreceived);
            }
        }
        else {
            holder.statusHolder.setVisibility(View.GONE);
            holder.deleteTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView, detailTextView, timeTextView, priceTextView;
        TextView statusTextView, deleteTextView;
        LinearLayout statusHolder;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestDetailActivity.startMe(context);
                }
            });
            imageView = itemView.findViewById(R.id.NewResourceItem_thumbnail);
            titleTextView = itemView.findViewById(R.id.NewResourceItem_title);
            detailTextView = itemView.findViewById(R.id.NewResourceItem_detail);
            timeTextView = itemView.findViewById(R.id.NewResourceItem_publish_time);
            priceTextView = itemView.findViewById(R.id.NewResourceItem_price);
            statusTextView = itemView.findViewById(R.id.NewResourceItem_status);
            statusHolder = itemView.findViewById(R.id.NewResourceItem_status_holder);
            deleteTextView = itemView.findViewById(R.id.NewResourceItem_delete);
            if (showStatus){
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                });
            }
        }
    }
}
