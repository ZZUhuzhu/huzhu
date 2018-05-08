package com.example.zzu.huzhucommunity.commonclass;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by FEI on 2018/2/3.
 * 搜索历史的每一项
 * 包括最后一次搜索时间，搜索的内容
 */

public class SearchHistoryItem implements Comparable<SearchHistoryItem>, Cloneable{
    private String itemText;
    private long itemSearchTime;
    private boolean clearHistoryItem;

    public SearchHistoryItem(String itemText, long itemSearchTime){
        this.clearHistoryItem = false;
        this.itemText = itemText;
        this.itemSearchTime = itemSearchTime;
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
        return obj instanceof SearchHistoryItem && itemSearchTime == ((SearchHistoryItem) obj).itemSearchTime
                && itemText.equals(((SearchHistoryItem) obj).itemText);
    }

    @Override
    public String toString() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(itemSearchTime);
        return itemText + "," + calendar.toString();
    }

    @Override
    public int compareTo(@NonNull SearchHistoryItem o) {
        return itemSearchTime < o.itemSearchTime ? -1 : itemSearchTime == o.itemSearchTime ? 0 : 1;
    }

    @Override
    public Object clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clearHistoryItem ? new SearchHistoryItem(): new SearchHistoryItem(itemText, itemSearchTime);
    }
}
