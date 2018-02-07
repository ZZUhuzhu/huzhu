package com.example.zzu.huzhucommunity.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.activities.SearchResultActivity;
import com.example.zzu.huzhucommunity.commonclass.SearchHistoryItem;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by FEI on 2018/2/3.
 * 搜索历史记录RecyclerView的适配器
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {
    private ArrayList<SearchHistoryItem> searchHistoryList;
    private Context context;

    public SearchHistoryAdapter(Context context, ArrayList<SearchHistoryItem> searchHistoryList){
        this.context = context;
        this.searchHistoryList = searchHistoryList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_search_history_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchHistoryItem item = searchHistoryList.get(position);
        if(item.isClearHistoryItem()){
            holder.historyItemImageView.setVisibility(View.GONE);
            holder.historyItemTextView.setGravity(Gravity.CENTER);
        }
        else {
            holder.historyItemImageView.setVisibility(View.VISIBLE);
            holder.historyItemTextView.setGravity(Gravity.START);
        }
        holder.historyItemTextView.setText(item.getItemText());
    }

    @Override
    public int getItemCount() {
        return searchHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView historyItemTextView;
        ImageView historyItemImageView;
        ViewHolder(View itemView) {
            super(itemView);
            historyItemTextView = itemView.findViewById(R.id.search_history_item_text_view);
            historyItemImageView= itemView.findViewById(R.id.search_history_item_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    SearchHistoryItem item = searchHistoryList.get(pos);
                    if(item.isClearHistoryItem()){
                        searchHistoryList.clear();
                        notifyDataSetChanged();
                        return;
                    }
                    String text = item.getItemText();
                    searchHistoryList.remove(pos);
                    searchHistoryList.add(0, new SearchHistoryItem(text, GregorianCalendar.getInstance().getTime()));
                    SearchResultActivity.startMe(context, text);
                    ((Activity) context).finish();
                }
            });
        }
    }
}
