package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.zzu.huzhucommunity.dataclass.Request;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/24.
 * 新资源类
 * 保存主界面显示最新资源信息的RecyclerView的列表中每一项的信息
 */

public class NewResourceItem implements Parcelable {
    protected String itemID;
    protected String itemDetail;
    protected String itemTitle;
    protected long itemPublishTime;
    protected double itemPrice;
    protected int itemThumbnailAmount;
    protected ArrayList<Bitmap> itemThumbnails;
    protected boolean received;

    public NewResourceItem(String itemID, String itemTitle, String itemDetail,
                           long itemPublishTime,double itemPrice, ArrayList<Bitmap> itemThumbnails){
        this.itemID = itemID;
        this.received = Math.random() * 100 > 50;
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

    public long getItemPublishTime() {
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

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemID() {
        return itemID;
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

    private NewResourceItem(Parcel in) {
        itemID = in.readString();
        itemDetail = in.readString();
        itemTitle = in.readString();
        itemPublishTime = in.readLong();
        itemPrice = in.readDouble();
        itemThumbnailAmount = in.readInt();
        itemThumbnails = in.createTypedArrayList(Bitmap.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemID);
        dest.writeString(itemDetail);
        dest.writeString(itemTitle);
        dest.writeLong(itemPublishTime);
        dest.writeDouble(itemPrice);
        dest.writeInt(itemThumbnailAmount);
        dest.writeTypedList(itemThumbnails);
    }

}
