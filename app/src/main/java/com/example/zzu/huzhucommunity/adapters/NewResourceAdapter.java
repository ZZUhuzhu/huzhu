package com.example.zzu.huzhucommunity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/24.
 * 新资源列表适配器
 */

public class NewResourceAdapter extends RecyclerView.Adapter<NewResourceAdapter.ViewHolder> {
    private ArrayList<NewResourceItem> list;
    @Override
    public NewResourceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_resource_item_view, parent, false);
        return new ViewHolder(view);
    }
    public NewResourceAdapter( ArrayList<NewResourceItem> list){
        this.list = list;
    }
    @Override
    public void onBindViewHolder(NewResourceAdapter.ViewHolder holder, int position) {
        NewResourceItem item = list.get(position);
        holder.imageView.setImageBitmap(item.getItemThumbnail());
        holder.titleTextView.setText(item.getItemTitle());
        holder.detailTextView.setText(item.getItemDetail());
        String time = "" + item.getItemPublishTime() + "天前";
        holder.timeTextView.setText(time);
        String price = "" + item.getItemPrice() + "￥";
        holder.priceTextView.setText(price);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView, detailTextView, timeTextView, priceTextView;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.NewResourceItem_thumbnail);
            titleTextView = itemView.findViewById(R.id.NewResourceItem_title);
            detailTextView = itemView.findViewById(R.id.NewResourceItem_detail);
            timeTextView = itemView.findViewById(R.id.NewResourceItem_publish_time);
            priceTextView = itemView.findViewById(R.id.NewResourceItem_price);
        }
    }
}
