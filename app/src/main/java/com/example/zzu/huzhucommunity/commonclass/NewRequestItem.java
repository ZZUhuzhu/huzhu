package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.zzu.huzhucommunity.asynchttp.HTTPConstant;
import com.example.zzu.huzhucommunity.dataclass.Request;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/31.
 * 新请求类
 * 保存主界面显示最新请求的RecyclerView的列表中每一项的信息
 */

public class NewRequestItem{
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
    private ArrayList<String> itemImageUrls;
    private String deadline;

    public NewRequestItem(String itemID, String itemTitle, String itemDetail,
                           long itemPublishTime,double itemPrice, ArrayList<Bitmap> itemImages){
        this.itemID = itemID;
        this.received = Math.random() * 100 > 50;
        this.itemTitle = itemTitle;
        this.itemDetail = itemDetail;
        this.itemPrice = itemPrice;
        this.itemPublishTime = itemPublishTime;
        if(itemImages == null)
            itemImageNumber = 0;
        else
            itemImageNumber = itemImages.size();
        this.itemImages = itemImages;
        this.itemPublishTimeStr = Utilities.convertTimeInMillToString(itemPublishTime);
    }

    private NewRequestItem(String itemID, String itemTitle, String itemDetail, String itemDate,
                           String imageUrl, String itemPrice, String imageNumbers, String deadline){
        this.itemDetail = itemDetail;
        this.itemID = itemID;
        this.itemPublishTimeStr = itemDate;
        this.itemTitle = itemTitle;
        this.itemImageUrl = imageUrl;
        this.itemPrice = Double.parseDouble(itemPrice);
        this.itemImageNumber = Integer.parseInt(imageNumbers);
        this.deadline = deadline;
        this.itemImages = new ArrayList<>();
        this.itemImageUrls = new ArrayList<>();
        for (int i = 1; i <= itemImageNumber; i++)
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

    private boolean itemHasThumbnails(){
        return itemImageNumber > 0 && itemImages != null && itemImages.size() > 0;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemID() {
        return itemID;
    }

    public Bitmap getItemThumbnail() {
        if(itemHasThumbnails())
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
    }  public ArrayList<String> getItemImageUrls() {
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

    public static NewRequestItem TransferToMe(Request request){
        return new NewRequestItem(request.getResourceID(), request.getResourceTitle(), request.getResourceDetail(),
                request.getPublishDate().substring(5, 16), request.getImageURL(), request.getResourcePrice(),
                request.getResourceImageNumber(), request.getDeadline());
    }
}
