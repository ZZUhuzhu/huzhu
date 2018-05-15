package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by FEI on 2018/5/15.
 * 用于实现上拉加载的类
 */

public class PullUpLoadRecycler extends RecyclerView {
    private OnBottomCallback mOnBottomCallback;

    public interface OnBottomCallback {
        void onBottom();
    }

    public void setOnBottomCallback(OnBottomCallback onBottomCallback) {
        this.mOnBottomCallback = onBottomCallback;
    }

    public PullUpLoadRecycler(Context context) {
        this(context, null);
    }

    public PullUpLoadRecycler(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullUpLoadRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (isSlideToBottom()) {
            mOnBottomCallback.onBottom();
        }
    }

    public boolean isSlideToBottom() {
        return this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset() >= this.computeVerticalScrollRange();
    }
}
