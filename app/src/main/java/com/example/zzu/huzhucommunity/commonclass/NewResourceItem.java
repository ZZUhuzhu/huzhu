package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;

/**
 * Created by FEI on 2018/1/24.
 * 新资源类
 */

public class NewResourceItem {
    private String itemDetail;
    private String itemTitle;
    private int itemPublishTime;
    private double itemPrice;
    private Bitmap itemThumbnail;

    public NewResourceItem(String itemDetail, String itemTitle, int itemPublishTime, double itemPrice, Bitmap itemThumbnail){
        this.itemDetail = itemDetail;
        this.itemTitle = itemTitle;
        this.itemPrice = itemPrice;
        this.itemPublishTime = itemPublishTime;
        this.itemThumbnail = itemThumbnail;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemPublishTime() {
        return itemPublishTime;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public Bitmap getItemThumbnail() {
        return itemThumbnail;
    }
}
