package com.example.zzu.huzhucommunity.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/1/31.
 * 主界面的ViewPager的适配器
 */

public class CommonViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewArrayList;

    public CommonViewPagerAdapter(ArrayList<View> viewArrayList){
        this.viewArrayList = viewArrayList;
    }

    @Override
    public int getCount() {
        return viewArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewArrayList.get(position));
        return viewArrayList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewArrayList.get(position));
    }
}
