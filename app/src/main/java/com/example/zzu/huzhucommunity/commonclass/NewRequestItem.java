package com.example.zzu.huzhucommunity.commonclass;

import android.graphics.Bitmap;

import com.example.zzu.huzhucommunity.dataclass.Request;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/31.
 * 新请求类
 * 保存主界面显示最新请求的RecyclerView的列表中每一项的信息
 */

public class NewRequestItem extends NewResourceItem{
    public NewRequestItem(String itemID, String itemDetail, String itemTitle, int itemPublishTime,
                          double itemPrice, ArrayList<Bitmap> itemThumbnails) {
        super(itemID, itemDetail, itemTitle, itemPublishTime, itemPrice, itemThumbnails);
    }

}
