package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.zzu.huzhucommunity.dataclass.Request;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/31.
 * 新请求类
 * 保存主界面显示最新请求的RecyclerView的列表中每一项的信息
 */

public class NewRequestItem implements Parcelable {
    private String itemID;
    private String itemDetail;
    private String itemTitle;
    private long itemPublishTime;
    private double itemPrice;
    private int itemThumbnailAmount;
    private ArrayList<Bitmap> itemThumbnails;
    private boolean received;
    private String itemPublishTimeStr;
    private String itemImageUrl;

    public NewRequestItem(String itemID, String itemTitle, String itemDetail,
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
        this.itemPublishTimeStr = Utilities.convertTimeInMillToString(itemPublishTime);
    }

    private NewRequestItem(String itemID, String itemTitle, String itemDetail, String itemDate, String imageUrl){
        this.itemDetail = itemDetail;
        this.itemID = itemID;
        this.itemPublishTimeStr = itemDate;
        this.itemTitle = itemTitle;
        this.itemImageUrl = imageUrl;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived() {
        this.received = true;
    }

    public static final Parcelable.Creator<NewRequestItem> CREATOR = new Parcelable.Creator<NewRequestItem>() {
        @Override
        public NewRequestItem createFromParcel(Parcel in) {
            return new NewRequestItem(in);
        }

        @Override
        public NewRequestItem[] newArray(int size) {
            return new NewRequestItem[size];
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

    private NewRequestItem(Parcel in) {
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

    public static NewRequestItem TransferToMe(Request request){
        return new NewRequestItem(request.getResourceID(), request.getResourceTitle(), request.getResourceDetail(),
                request.getPublishDate(), request.getImageURL());
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
}
