package com.example.zzu.huzhucommunity.dataclass;

/**
 * 用于 Received_Resource 和 Published
 */
public class Resource {
    private String resourceAbbr,
            resourceTitle,
            resourceDetail,
            resourcePrice,
            resourceStatus,
            resourcePublishTime,
            res_price;

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

    public String getRes_price() {
        return res_price;
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

    public void setRes_price(String res_price) {
        this.res_price = res_price;
    }
}
