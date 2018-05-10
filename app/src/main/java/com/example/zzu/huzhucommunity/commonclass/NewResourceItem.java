package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.zzu.huzhucommunity.dataclass.Request;
import com.example.zzu.huzhucommunity.dataclass.Resource;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/24.
 * 新资源类
 * 保存主界面显示最新资源信息的RecyclerView的列表中每一项的信息
 */

public class NewResourceItem implements Parcelable {
    private String itemID;
    private String itemDetail;
    private String itemTitle;
    private long itemPublishTime;
    private double itemPrice;
    private int itemThumbnailAmount;
    private ArrayList<Bitmap> itemThumbnails;
    private boolean received;
    //todo 添加到 Parcelable
    private String itemPublishTimeStr;
    private String itemImageUrl;

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

    private NewResourceItem(String itemID, String itemTitle, String itemDetail,
                            String itemPrice, String itemImageUrl, String itemPublishTimeStr){
        this.itemID = itemID;
        this.itemTitle = itemTitle;
        this.itemDetail = itemDetail;
        this.itemPrice = Double.parseDouble(itemPrice);
        this.itemPublishTimeStr = itemPublishTimeStr;
        this.itemImageUrl = itemImageUrl;
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

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemPublishTimeStr() {
        return itemPublishTimeStr;
    }

    public void setItemPublishTimeStr(String itemPublishTimeStr) {
        this.itemPublishTimeStr = itemPublishTimeStr;
    }

    @NonNull
    public static NewResourceItem TransferToMe(Resource resource){
        return new NewResourceItem(resource.getResourceID(), resource.getResourceTitle(), resource.getResourceDetail(),
                resource.getResourcePrice(), resource.getImageUrl(), resource.getPublishDate().substring(5, 16));
    }
}
