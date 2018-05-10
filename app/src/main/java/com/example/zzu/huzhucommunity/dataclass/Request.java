package com.example.zzu.huzhucommunity.dataclass;

public class Request {
    private String imageURL;
    private String resourceTitle;
    private String resourceDetail;
    private String resourceID;
    private String publishDate,
    res_price;

    public String getImageURL() {
        return imageURL;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public String getResourceDetail() {
        return resourceDetail;
    }

    public String getRes_price() {
        return res_price;
    }

    public String getResourceID() {
        return resourceID;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public void setResourceDetail(String resourceDetail) {
        this.resourceDetail = resourceDetail;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setRes_price(String res_price) {
        this.res_price = res_price;
    }
}
