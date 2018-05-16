package com.example.zzu.huzhucommunity.dataclass;

/**
 * 用于 Received_Resource 和 Published
 */
public class Resource {
    private String resourceAbbr,
            imageURL,
            resourceTitle,
            resourceDetail,
            resourcePrice,
            resourceID,
            resourceStatus,
            publishDate,
            resourcePublishTime;
    private String resourceImageNumber;
    private String deadline;

    public String getResourceAbbr() {
        return resourceAbbr;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public String getResourceDetail() {
        return resourceDetail;
    }

    public String getResourcePrice() {
        return resourcePrice;
    }

    public String getResourceStatus() {
        return resourceStatus;
    }

    public String getResourcePublishTime() {
        return resourcePublishTime;
    }

    public void setResourceAbbr(String resourceAbbr) {
        this.resourceAbbr = resourceAbbr;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public void setResourceDetail(String resourceDetail) {
        this.resourceDetail = resourceDetail;
    }

    public void setResourcePrice(String resourcePrice) {
        this.resourcePrice = resourcePrice;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public void setResourcePublishTime(String resourcePublishTime) {
        this.resourcePublishTime = resourcePublishTime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getResourceImageNumber() {
        return resourceImageNumber;
    }

    public void setResourceImageNumber(String resourceImageNumber) {
        this.resourceImageNumber = resourceImageNumber;
    }
}
