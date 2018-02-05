package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.MainRequestAdapter;
import com.example.zzu.huzhucommunity.adapters.MainResourcesAdapter;
import com.example.zzu.huzhucommunity.adapters.MainViewPagerAdapter;
import com.example.zzu.huzhucommunity.commonclass.NewRequestItem;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity {
    private static final String TAG = "SearchResultActivity";
    private static final String INTENT_DATA_NAME = "SEARCH_DATA";
    private String title;
    private RecyclerView resourceRecyclerView;
    private ArrayList<NewResourceItem> resourceItemArrayList = new ArrayList<>();
    private RecyclerView requestRecyclerView;
    private ArrayList<NewRequestItem> requestItemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_layout);
        Toolbar toolbar = findViewById(R.id.SearchResultActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        TextView inputTextView = findViewById(R.id.SearchResultActivity_input_text_view);
        title = getIntent().getStringExtra(INTENT_DATA_NAME) + getString(R.string.searchResultOf);
        inputTextView.setText(title);

        resourceRecyclerView = new RecyclerView(this);
        resourceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        resourceRecyclerView.setAdapter(new MainResourcesAdapter(resourceItemArrayList, this));

        requestRecyclerView = new RecyclerView(this);
        requestRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestRecyclerView.setAdapter(new MainRequestAdapter(requestItemArrayList, this));

        ViewPager viewPager = findViewById(R.id.SearchResultActivity_view_pager);
        TabLayout tabLayout = findViewById(R.id.SearchResultActivity_tab_layout);
        ArrayList<View> pagerViews = new ArrayList<>();
        pagerViews.add(resourceRecyclerView);
        pagerViews.add(requestRecyclerView);
        viewPager.setAdapter(new MainViewPagerAdapter(pagerViews));
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab1 = tabLayout.getTabAt(0), tab2 = tabLayout.getTabAt(1);
        if (tab1 != null)
            tab1.setText(getString(R.string.resource));
        if (tab2 != null)
            tab2.setText(getString(R.string.request));

        resourceRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        addListener(R.id.SearchResultActivity_input_text_view);

        loadSearchResult();
    }

    /**
     * 加载搜索结果
     */
    public void loadSearchResult(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: now loading search Result");
                for(int i = 1; i < 22; i++){
                    String title = "生命" + i + "号";
                    String detail = getString(R.string.virtualResourceDetail);
                    int time = i + (int) (Math.random() * 100);
                    double price = ((int) (Math.random() * 1000)) / 10;
                    ArrayList<Bitmap> bitmaps = new ArrayList<>();
                    bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.default_image));
                    NewResourceItem item = new NewResourceItem(title, detail, time, price, bitmaps);
                    resourceItemArrayList.add(item);

                    title = "给我个" + title;
                    detail = getString(R.string.virtualRequestDetail);
                    NewRequestItem requestItem = new NewRequestItem(title, detail, time, price, bitmaps);
                    requestItemArrayList.add(requestItem);
                }
                resourceRecyclerView.getAdapter().notifyDataSetChanged();
                requestRecyclerView.getAdapter().notifyDataSetChanged();
                Log.e(TAG, "run: notified");
            }
        }).start();
    }
    @Override
    public void addListener(final int res) {
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.SearchResultActivity_input_text_view:
                        SearchActivity.startMe(SearchResultActivity.this, title);
                        finish();
                        break;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void startMe(Context context, String data){
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(INTENT_DATA_NAME, data);
        context.startActivity(intent);
    }

}
