package com.example.zzu.huzhucommunity.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.activities.MainActivity;
import com.example.zzu.huzhucommunity.activities.ResourceDetailActivity;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/24.
 * 新资源列表适配器
 */

public class MainResourcesAdapter extends RecyclerView.Adapter<MainResourcesAdapter.ViewHolder> {
    private ArrayList<NewResourceItem> list;
    private Context context;
    @Override
    public MainResourcesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_resource_item_view, parent, false);
        return new ViewHolder(view);
    }
    public MainResourcesAdapter(ArrayList<NewResourceItem> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(MainResourcesAdapter.ViewHolder holder, int position) {
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

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView, detailTextView, timeTextView, priceTextView;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ResourceDetailActivity.class);
                    intent.putExtra(MainActivity.RESOURCE_DETAIL_RESOURCE_ITEM_POSITION, getAdapterPosition());
                    ((Activity)context).startActivityForResult(intent, MainActivity.RESOURCE_DETAIL_REQUEST_CODE);
                }
            });
            imageView = itemView.findViewById(R.id.NewResourceItem_thumbnail);
            titleTextView = itemView.findViewById(R.id.NewResourceItem_title);
            detailTextView = itemView.findViewById(R.id.NewResourceItem_detail);
            timeTextView = itemView.findViewById(R.id.NewResourceItem_publish_time);
            priceTextView = itemView.findViewById(R.id.NewResourceItem_price);
        }
    }
}
