package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.CommonRequestAdapter;
import com.example.zzu.huzhucommunity.adapters.CommonResourcesAdapter;
import com.example.zzu.huzhucommunity.adapters.CommonViewPagerAdapter;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.Main;
import com.example.zzu.huzhucommunity.asynchttp.UserProfile;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.NewRequestItem;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.PullUpLoadRecycler;
import com.example.zzu.huzhucommunity.dataclass.Request;
import com.example.zzu.huzhucommunity.dataclass.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * 登录成功后的主界面
 */
public class MainActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_GET_RES_DESC = 111;
    public static final int REQUEST_CODE_GET_REQ_DESC = 222;

    private static final String NO_MORE = "没有更多了";
    private static final String REFRESH_FINISH = "刷新完成";
    public static final String PUBLISH_TYPE = "PUBLISH_TYPE";
    private static long lastTimeBackPressed = 0;

    private static final int LOAD_RES_IMAGE = 11;
    private static final int LOAD_REQ_IMAGE = 12;

    //todo change background one thread multi image
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case LOAD_REQ_IMAGE:
                case LOAD_RES_IMAGE:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    int ind = msg.arg1;
                    if (msg.what == LOAD_REQ_IMAGE){
                        if (ind >= requestItems.size())
                            break;
                        requestItems.get(ind).addItemThumbnail(bitmap);
                        requestAdapter.notifyItemChanged(ind);
                    }
                    else {
                        if (ind >= resourceItems.size())
                            break;
                        resourceItems.get(ind).addItemThumbnail(bitmap);
                        resourceAdapter.notifyItemChanged(ind);
                    }
                    break;
            }
            return false;
        }
    });
    private ImageButton resourceButton;
    private ImageButton requestButton;

    private SwipeRefreshLayout resSwipeRefreshLayout, requestSwipeRefreshLayout;

    private PullUpLoadRecycler newResourceRecyclerView;
    private final ArrayList<NewResourceItem> resourceItems = new ArrayList<>();
    private CommonResourcesAdapter resourceAdapter;

    private PullUpLoadRecycler newRequestRecyclerView;
    private final ArrayList<NewRequestItem> requestItems = new ArrayList<>();
    private CommonRequestAdapter requestAdapter;

    private ViewPager mainViewPager;
    private ArrayList<View> pagerViews = new ArrayList<>();

    /**
     * 用来线程同步的序列号
     */
    private int curLoadImageSeq = 0x80000000;
    private int curResTimes = 0, curReqTimes = 0;
    private boolean loadingMoreRes = false, loadingMoreReq = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        setSwipeToFinishOff();

        resourceButton = findViewById(R.id.MainActivity_resource_button);
        requestButton = findViewById(R.id.MainActivity_request_button);

        newResourceRecyclerView = new PullUpLoadRecycler(this);
        newResourceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        resourceAdapter = new CommonResourcesAdapter(resourceItems, this);
        newResourceRecyclerView.setAdapter(resourceAdapter);
        newResourceRecyclerView.setOnBottomCallback(new PullUpLoadRecycler.OnBottomCallback() {
            @Override
            public void onBottom() {
                if (loadingMoreRes)
                    return;
                loadingMoreRes = true;
                Main.getOurInstance().getNewResource(++curResTimes + "", MainActivity.this);
            }
        });

        resSwipeRefreshLayout = new SwipeRefreshLayout(this);
        resSwipeRefreshLayout.setColorSchemeResources(R.color.colorDeepSkyBlue);
        resSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Main.getOurInstance().checkNewResUpdate(resourceItems, MainActivity.this);
            }
        });
        resSwipeRefreshLayout.addView(newResourceRecyclerView);
        pagerViews.add(resSwipeRefreshLayout);

        newRequestRecyclerView = new PullUpLoadRecycler(this);
        newRequestRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestAdapter = new CommonRequestAdapter(requestItems, this);
        newRequestRecyclerView.setAdapter(requestAdapter);
        newRequestRecyclerView.setOnBottomCallback(new PullUpLoadRecycler.OnBottomCallback() {
            @Override
            public void onBottom() {
                if (loadingMoreReq)
                    return;
                loadingMoreReq = true;
                Main.getOurInstance().getNewRequest(++curReqTimes + "", MainActivity.this);
            }
        });

        requestSwipeRefreshLayout = new SwipeRefreshLayout(this);
        requestSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSwipeRefreshLayout.setColorSchemeResources(R.color.colorDeepSkyBlue);
                Main.getOurInstance().checkNewReqUpdate(requestItems, MainActivity.this);
            }
        });
        requestSwipeRefreshLayout.addView(newRequestRecyclerView);
        pagerViews.add(requestSwipeRefreshLayout);

        mainViewPager = findViewById(R.id.MainActivity_view_pager);
        mainViewPager.setAdapter(new CommonViewPagerAdapter(pagerViews));
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                changePagerButton(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        addListener(R.id.MainActivity_resource_button);
        addListener(R.id.MainActivity_resource_text_view);
        addListener(R.id.MainActivity_request_button);
        addListener(R.id.MainActivity_request_text_view);
        addListener(R.id.MainActivity_head_button);
        addListener(R.id.MainActivity_publish_button);
        addListener(R.id.MainActivity_message_button);
        addListener(R.id.MainActivity_search_text_view);

        initUserHeadImage();
        Main.getOurInstance().getNewRequest("1", this);
        Main.getOurInstance().getNewResource("1", this);
    }

    /**
     * 初始化用户头像
     */
    public void initUserHeadImage(){
        Bitmap bitmap = Utilities.GetLoginUserHeadBitmapFromSP();
        if (bitmap == null){
            UserProfile.getOurInstance().getImageBitmapByUrl(Utilities.GetLoginUserHeadUrl(), new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == UserProfile.GET_IMAGE_BITMAP_BY_URL){
                        Bitmap bitmap = (Bitmap) msg.obj;
                        if (bitmap != null){
                            ((ImageView) findViewById(R.id.MainActivity_head_button)).setImageBitmap(bitmap);
                            Utilities.SaveLoginUserHeadBitmap(bitmap);
                        }
                    }
                    return false;
                }
            }));
        }
        else {
            ((ImageView) findViewById(R.id.MainActivity_head_button)).setImageBitmap(bitmap);
        }
    }

    /**
     * 加载资源，请求之后加载每一项的(缩略)图片
     * @param seq 序列号，用来同步
     * @param startInd 起始下标
     */
    public void loadResItemImage(final int seq, final int startInd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = startInd; i < resourceItems.size(); i++){
                    if (i >= resourceItems.size())
                        break;
                    final String tmpUrl = resourceItems.get(i).getItemImageUrl();
                    if (seq != curLoadImageSeq)
                        return;
                    Bitmap bitmap;
                    InputStream in = null;
                    try {
                        URL url = new URL(tmpUrl);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setConnectTimeout(2000);
                        in = con.getInputStream();
                        bitmap = BitmapFactory.decodeStream(in);
                        Message message = new Message();
                        message.obj = bitmap;
                        message.what = LOAD_RES_IMAGE;
                        message.arg1 = i;
                        message.arg2 = seq;
                        handler.sendMessage(message);
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        if (in != null){
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 加载请求的缩略图
     * @param seq 序列号，用来同步
     * @param startInd 起始下标
     */
    public void loadReqItemImage(final int seq, final int startInd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = startInd; i < requestItems.size(); i++){
                    if (i >= requestItems.size())
                        break;
                    final String tmpUrl = requestItems.get(i).getItemImageUrl();
                    if (seq != curLoadImageSeq)
                        return;
                    Bitmap bitmap;
                    InputStream in = null;
                    try {
                        URL url = new URL(tmpUrl);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setConnectTimeout(2000);
                        in = con.getInputStream();
                        bitmap = BitmapFactory.decodeStream(in);
                        Message message = new Message();
                        message.obj = bitmap;
                        message.what = LOAD_REQ_IMAGE;
                        message.arg1 = i;
                        message.arg2 = seq;
                        handler.sendMessage(message);
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        if (in != null){
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * ViewPager切换即MainActivity中页面改变时调用
     * @param position 切换到的页面对应的下标
     */
    public void changePagerButton(int position){
        if(position == 0){
            resourceButton.setImageResource(R.drawable.resource_yellow);
            requestButton.setImageResource(R.drawable.request_gray);
            newResourceRecyclerView.setVisibility(View.VISIBLE);
            newRequestRecyclerView.setVisibility(View.GONE);
        }
        else if(position == 1) {
            resourceButton.setImageResource(R.drawable.resource_gray);
            requestButton.setImageResource(R.drawable.request_yellow);
            newResourceRecyclerView.setVisibility(View.GONE);
            newRequestRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 为每个控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.MainActivity_resource_button:
                    case R.id.MainActivity_resource_text_view:
                        mainViewPager.setCurrentItem(0);
                        break;
                    case R.id.MainActivity_request_button:
                    case R.id.MainActivity_request_text_view:
                        mainViewPager.setCurrentItem(1);
                        break;
                    case R.id.MainActivity_head_button:
                        UserProfileActivity.startMe(MainActivity.this);
                        break;
                    case R.id.MainActivity_publish_button:
                        ImageButton publishButton = findViewById(R.id.MainActivity_publish_button);
                        Animation animation = new RotateAnimation(0.0f, 90,
                                publishButton.getWidth() / 2, publishButton.getHeight() / 2);
                        animation.setDuration(150);
                        animation.setFillAfter(true);
                        publishButton.startAnimation(animation);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setItems(R.array.PublishType, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0)
                                    PublishNewActivity.startMe(MainActivity.this, PublishNewActivity.PUBLISH_NEW_RESOURCE,
                                            PublishNewActivity.PUBLISH_NEW_RESOURCE);
                                else if (which == 1)
                                    PublishNewActivity.startMe(MainActivity.this, PublishNewActivity.PUBLISH_NEW_REQUEST,
                                            PublishNewActivity.PUBLISH_NEW_REQUEST);
                            }
                        });
                        dialog.show();
                        break;
                    case R.id.MainActivity_message_button:
                        MessagesActivity.startMe(MainActivity.this);
                        break;
                    case  R.id.MainActivity_search_text_view:
                        SearchActivity.startMe(MainActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        long curTime = GregorianCalendar.getInstance().getTimeInMillis();
        long exitInterval = 2000;
        if (curTime - lastTimeBackPressed <= exitInterval){
            ActivitiesCollector.finishAll();
        }
        else {
            Toast.makeText(MyApplication.getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = GregorianCalendar.getInstance().getTimeInMillis();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = Utilities.GetLoginUserHeadBitmapFromSP();
        if (bitmap != null)
            ((ImageView) findViewById(R.id.MainActivity_head_button)).setImageBitmap(bitmap);
    }

    /**
     * 回调成功函数
     * @param statusCode 返回状态
     * @param mp 存储需要传递数据的哈希表，若无数据则 mp 为 null
     * @param requestCode 请求码，标识同一个activity的不同网络请求
     */
    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        List<Resource> resList;
        List<Request> reqList;
        switch (requestCode){
            case Main.GET_NEW_REQUEST:
                if (mp.get(Main.REQUEST_NUMBER_JSON_KEY).equals("0")){
                    Toast.makeText(MyApplication.getContext(), NO_MORE, Toast.LENGTH_SHORT).show();
                    loadingMoreReq = false;
                    break;
                }
                reqList = Utilities.getRequest(mp);
                if (reqList != null) {
                    for (Request request: reqList) {
                        requestItems.add(NewRequestItem.TransferToMe(request));
                    }
                    requestAdapter.notifyDataSetChanged();
                    findViewById(R.id.MainActivity_progress_bar).setVisibility(View.INVISIBLE);
                    if (requestSwipeRefreshLayout.isRefreshing())
                        requestSwipeRefreshLayout.setRefreshing(false);
                    loadingMoreReq = false;
                    loadReqItemImage(curLoadImageSeq, curReqTimes * 20);
                }
                break;
            case Main.GET_NEW_RESOURCE:
                if (mp.get(Main.REQUEST_NUMBER_JSON_KEY).equals("0")){
                    Toast.makeText(MyApplication.getContext(), NO_MORE, Toast.LENGTH_SHORT).show();
                    loadingMoreRes = false;
                    break;
                }
                resList = Utilities.getResource(mp);
                if (resList != null) {
                    for (Resource resource : resList){
                        resourceItems.add(NewResourceItem.TransferToMe(resource));
                    }
                    resourceAdapter.notifyDataSetChanged();
                    findViewById(R.id.MainActivity_progress_bar).setVisibility(View.INVISIBLE);
                    if (resSwipeRefreshLayout.isRefreshing())
                        resSwipeRefreshLayout.setRefreshing(false);
                    loadingMoreRes = false;
                    loadResItemImage(curLoadImageSeq, curResTimes * 20);
                }
                break;
            case Main.GET_RESOURCE_BY_TYPE:
                break;
            case Main.UPDATE_REQUEST:
                if (mp == null){
                    Toast.makeText(MyApplication.getContext(), REFRESH_FINISH, Toast.LENGTH_SHORT).show();
                    requestSwipeRefreshLayout.setRefreshing(false);
                    return;
                }
                curLoadImageSeq++;
                synchronized (requestItems){
                    requestItems.clear();
                }
                reqList = Utilities.getRequest(mp);
                if (reqList != null) {
                    for (Request request: reqList) {
                        requestItems.add(NewRequestItem.TransferToMe(request));
                    }
                    requestAdapter.notifyDataSetChanged();
                    findViewById(R.id.MainActivity_progress_bar).setVisibility(View.INVISIBLE);
                    if (requestSwipeRefreshLayout.isRefreshing())
                        requestSwipeRefreshLayout.setRefreshing(false);
                    loadReqItemImage(curLoadImageSeq, 0);
                }
                break;
            case Main.UPDATE_RESOURCE:
                if (mp == null){
                    Toast.makeText(MyApplication.getContext(), REFRESH_FINISH, Toast.LENGTH_SHORT).show();
                    resSwipeRefreshLayout.setRefreshing(false);
                    return;
                }
                curLoadImageSeq++;
                synchronized (resourceItems){
                    resourceItems.clear();
                }
                resList = Utilities.getResource(mp);
                if (resList != null) {
                    for (Resource resource : resList){
                        resourceItems.add(NewResourceItem.TransferToMe(resource));
                    }
                    resourceAdapter.notifyDataSetChanged();
                    findViewById(R.id.MainActivity_progress_bar).setVisibility(View.INVISIBLE);
                    if (resSwipeRefreshLayout.isRefreshing())
                        resSwipeRefreshLayout.setRefreshing(false);
                    loadResItemImage(curLoadImageSeq, 0);
                }
                break;
        }
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
        if (resSwipeRefreshLayout.isRefreshing()){
            loadingMoreRes = false;
            resSwipeRefreshLayout.setRefreshing(false);
        }
        if (requestSwipeRefreshLayout.isRefreshing()){
            loadingMoreReq = false;
            requestSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED)
            return;
        switch (requestCode){
            case REQUEST_CODE_GET_RES_DESC:
                int resPos = data.getIntExtra(ResourceDetailActivity.RES_ADAPTER_POS, -1);
                if (resPos != -1 && resPos < resourceItems.size()){
                    resourceItems.remove(resPos);
                    resourceAdapter.notifyItemChanged(resPos);
                }
                break;
            case REQUEST_CODE_GET_REQ_DESC:
                int reqPos = data.getIntExtra(ResourceDetailActivity.RES_ADAPTER_POS, -1);
                if (reqPos != -1 && reqPos < requestItems.size()){
                    requestItems.remove(reqPos);
                    requestAdapter.notifyItemChanged(reqPos);
                }
                break;
            case PublishNewActivity.PUBLISH_NEW_RESOURCE:
//                resSwipeRefreshLayout.setRefreshing(true);
//                loadingMoreRes = true;
//                newResourceRecyclerView.smoothScrollToPosition(0);
//                Main.getOurInstance().checkNewResUpdate(resourceItems, MainActivity.this);
                break;
            case PublishNewActivity.PUBLISH_NEW_REQUEST:
//                requestSwipeRefreshLayout.setRefreshing(true);
//                newRequestRecyclerView.smoothScrollToPosition(0);
//                loadingMoreReq = true;
//                Main.getOurInstance().checkNewReqUpdate(requestItems, MainActivity.this);
                break;
        }
    }

    public static void startMe(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

}
