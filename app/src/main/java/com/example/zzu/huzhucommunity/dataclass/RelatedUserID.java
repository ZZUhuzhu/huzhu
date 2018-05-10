package com.example.zzu.huzhucommunity.dataclass;

public class RelatedUserID {
    private String
            mContent,
            lastChatTime,
            oppositeID,
            oppositeURL,
            oppositeName;

    public String getmContent() {
        return mContent;
    }

    public String getLastChatTime() {
        return lastChatTime;
    }

    public String getOppsiteID() {
        return oppositeID;
    }

    public String getOppositeURL() {
        return oppositeURL;
    }

    public String getOppositeName() {
        return oppositeName;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setLastChatTime(String lastChatTime) {
        this.lastChatTime = lastChatTime;
    }

    public void setOppsiteID(String oppsiteID) {
        this.oppositeID = oppsiteID;
    }

    public void setOppositeURL(String oppositeURL) {
        this.oppositeURL = oppositeURL;
    }

    public void setOppositeName(String oppositeName) {
        this.oppositeName = oppositeName;
    }
}
