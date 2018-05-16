package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.zzu.huzhucommunity.asynchttp.HTTPConstant;
import com.example.zzu.huzhucommunity.dataclass.Resource;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/24.
 * 新资源类
 * 保存主界面显示最新资源信息的RecyclerView的列表中每一项的信息
 */

public class NewResourceItem {
    private String itemID;
    private String itemDetail;
    private String itemTitle;
    private long itemPublishTime;
    private double itemPrice;
    private int itemImageNumber;
    private ArrayList<Bitmap> itemImages;
    private boolean received;
    private String itemPublishTimeStr;
    private String itemImageUrl;
    private String deadline;
    private ArrayList<String> itemImageUrls;

    public NewResourceItem(String itemID, String itemTitle, String itemDetail,
                           long itemPublishTime,double itemPrice, ArrayList<Bitmap> itemThumbnails){
        this.itemID = itemID;
        this.received = Math.random() * 100 > 50;
        this.itemTitle = itemTitle;
        this.itemDetail = itemDetail;
        this.itemPrice = itemPrice;
        this.itemPublishTime = itemPublishTime;
        if(itemThumbnails == null)
            itemImageNumber = 0;
        else
            itemImageNumber = itemThumbnails.size();
        this.itemImages = itemThumbnails;
    }

    private NewResourceItem(String itemID, String itemTitle, String itemDetail, String itemPrice, String itemImageUrl,
                            String itemPublishTimeStr, String itemImageNumber, String deadline){
        this.itemID = itemID;
        this.itemTitle = itemTitle;
        this.itemDetail = itemDetail;
        this.itemPrice = Double.parseDouble(itemPrice);
        this.itemPublishTimeStr = itemPublishTimeStr;
        this.itemImageUrl = itemImageUrl;
        this.itemImageNumber = Integer.parseInt(itemImageNumber);
        this.deadline = deadline;
        itemImageUrls = new ArrayList<>();
        itemImages = new ArrayList<>();
        for (int i = 1; i <= this.itemImageNumber; i++)
            itemImageUrls.add(HTTPConstant.IMAGE_URL_PREFIX + itemID + "_" + i + HTTPConstant.IMAGE_URL_SUFFIX);
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived() {
        this.received = true;
    }

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

    public void addItemThumbnail(Bitmap bitmap){
        itemImages.add(bitmap);
        itemImageNumber++;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemID() {
        return itemID;
    }

    public Bitmap getItemThumbnail() {
        if(itemImageNumber > 0 && itemImages != null && itemImages.size() > 0)
            return itemImages.get(0);
        return null;
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

    public ArrayList<String> getItemImageUrls() {
        return itemImageUrls;
    }

    public void setItemImageUrls(ArrayList<String> itemImageUrls) {
        this.itemImageUrls = itemImageUrls;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @NonNull
    public static NewResourceItem TransferToMe(Resource resource){
        return new NewResourceItem(resource.getResourceID(), resource.getResourceTitle(), resource.getResourceDetail(),
                resource.getResourcePrice(), resource.getImageURL(), resource.getPublishDate().substring(5, 16),
                resource.getResourceImageNumber(), resource.getDeadline());
    }
}
