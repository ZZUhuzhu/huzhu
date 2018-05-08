package com.example.zzu.huzhucommunity.customlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.CommonRequestAdapter;
import com.example.zzu.huzhucommunity.adapters.CommonResourcesAdapter;
import com.example.zzu.huzhucommunity.adapters.CommonViewPagerAdapter;
import com.example.zzu.huzhucommunity.commonclass.NewRequestItem;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;

import java.util.ArrayList;

/**
 * Created by FEI on 2018/2/6.
 * 自定义布局，由一个TabLayout和一个ViewPager内嵌RecyclerView组成
 * 用来展示资源，请求信息
 */

public class ResReqViewPagerLayout extends LinearLayout {
    private RecyclerView resRecyclerView;
    private RecyclerView reqRecyclerView;
    private Context context;

    public ResReqViewPagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.custom_res_req_pager_view, this, true);
    }

    public void setView(ArrayList<NewResourceItem> resourceItems, ArrayList<NewRequestItem> requestItems, boolean showStatus){
        TabLayout tabLayout = findViewById(R.id.res_req_pager_view_tab_layout);
        ViewPager viewPager = findViewById(R.id.res_req_pager_view_view_pager);
        ArrayList<View> views = new ArrayList<>();
        viewPager.setAdapter(new CommonViewPagerAdapter(views));
        tabLayout.setupWithViewPager(viewPager);

        resRecyclerView = new RecyclerView(context);
        resRecyclerView.setItemAnimator(new DefaultItemAnimator());
        resRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        resRecyclerView.setAdapter(new CommonResourcesAdapter(resourceItems, context, showStatus));
        views.add(resRecyclerView);

        reqRecyclerView = new RecyclerView(context);
        reqRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reqRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        reqRecyclerView.setAdapter(new CommonRequestAdapter(requestItems, context, showStatus));
        views.add(reqRecyclerView);

        viewPager.getAdapter().notifyDataSetChanged();

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        if (tab != null)
            tab.setText(R.string.resource);
        tab = tabLayout.getTabAt(1);
        if (tab != null)
            tab.setText(R.string.request);
    }
    public void resNotifyDataSetChanged(){
        resRecyclerView.getAdapter().notifyDataSetChanged();
    }
    public void reqNotifyDataSetChanged(){
        reqRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
