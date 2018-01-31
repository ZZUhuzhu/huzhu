package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/24.
 * 新资源类
 * 保存主界面显示最新资源信息的RecyclerView的列表中每一项的信息
 */

public class NewResourceItem implements Parcelable {
    private String itemDetail;
    private String itemTitle;
    private int itemPublishTime;
    private double itemPrice;
    private int itemThumbnailAmount;
    private ArrayList<Bitmap> itemThumbnails;
    private boolean received;

    public NewResourceItem(String itemTitle, String itemDetail, int itemPublishTime, double itemPrice, ArrayList<Bitmap> itemThumbnails){
        this.received = false;
        this.itemTitle = itemTitle;
        this.itemDetail = itemDetail;
        this.itemPrice = itemPrice;
        this.itemPublishTime = itemPublishTime;
        if(itemThumbnails == null)
            itemThumbnailAmount = 0;
        else
            itemThumbnailAmount = itemThumbnails.size();
        this.itemThumbnails = itemThumbnails;
    }

    private NewResourceItem(Parcel in) {
        itemDetail = in.readString();
        itemTitle = in.readString();
        itemPublishTime = in.readInt();
        itemPrice = in.readDouble();
        itemThumbnailAmount = in.readInt();
        itemThumbnails = in.createTypedArrayList(Bitmap.CREATOR);
    }
    public boolean isReceived() {
        return received;
    }
    public void setReceived() {
        this.received = true;
    }

    public static final Creator<NewResourceItem> CREATOR = new Creator<NewResourceItem>() {
        @Override
        public NewResourceItem createFromParcel(Parcel in) {
            return new NewResourceItem(in);
        }

        @Override
        public NewResourceItem[] newArray(int size) {
            return new NewResourceItem[size];
        }
    };

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

    public ArrayList<Bitmap> getItemThumbnails() {
        return itemThumbnails;
    }
    private boolean itemHasThumbnails(){
        return itemThumbnailAmount > 0;
    }
    public Bitmap getItemThumbnail() {
        if(itemHasThumbnails())
            return itemThumbnails.get(0);
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemDetail);
        dest.writeString(itemTitle);
        dest.writeInt(itemPublishTime);
        dest.writeDouble(itemPrice);
        dest.writeInt(itemThumbnailAmount);
        dest.writeTypedList(itemThumbnails);
    }
}
