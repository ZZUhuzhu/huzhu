package com.example.zzu.huzhucommunity.dataclass;

public class ChatRecord {
    private String
            senderID,
            receiveID,
            type,
            mContent,
            mtime;

    public String getSenderID() {
        return senderID;
    }

    public String getReceiveID() {
        return receiveID;
    }

    public String getType() {
        return type;
    }

    public String getmContent() {
        return mContent;
    }

    public String getMtime() {
        return mtime;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setReceiveID(String receiveID) {
        this.receiveID = receiveID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }
}
