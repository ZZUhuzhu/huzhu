package com.example.zzu.huzhucommunity.commonclass;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by FEI on 2018/2/3.
 * 搜索历史的每一项
 * 包括最后一次搜索时间，搜索的内容
 */

public class SearchHistoryItem implements Comparable<SearchHistoryItem>, Cloneable{
    private String itemText;
    private Date itemSearchDate;
    private boolean clearHistoryItem;

    public SearchHistoryItem(String itemText, Date itemSearchDate){
        this.clearHistoryItem = false;
        this.itemText = itemText;
        this.itemSearchDate = itemSearchDate;
    }
    public SearchHistoryItem(){
        this.clearHistoryItem = true;
        itemText = "清空历史记录";
    }

    public boolean isClearHistoryItem() {
        return clearHistoryItem;
    }

    public String getItemText() {
        return itemText;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SearchHistoryItem && itemSearchDate.equals(((SearchHistoryItem) obj).itemSearchDate)
                && itemText.equals(((SearchHistoryItem) obj).itemText);
    }

    private Date getItemSearchDate() {
        return itemSearchDate;
    }

    @Override
    public String toString() {
        return itemText + "," + itemSearchDate.toString();
    }

    @Override
    public int compareTo(@NonNull SearchHistoryItem o) {
        return itemSearchDate.compareTo(o.getItemSearchDate());
    }

    @Override
    public Object clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clearHistoryItem? new SearchHistoryItem(): new SearchHistoryItem(itemText, itemSearchDate);
    }
}
