package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.adapters.SearchHistoryAdapter;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.SearchHistoryItem;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

/**
 * 搜索界面Activity
 * 显示搜索历史以及搜索时选项，点击搜索按钮或候选项后显示搜索结果
 * 候选项实现暂时不考虑
 */
public class SearchActivity extends BaseActivity {
    private static final String SEARCH_STRING_DATA = "SEARCH_STRING";
    private ArrayList<SearchHistoryItem> searchHistoryList = new ArrayList<>();
    private SearchHistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        Toolbar toolbar = findViewById(R.id.SearchActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EditText inputTextView = findViewById(R.id.SearchActivity_input_edit_text);
        inputTextView.setText(getIntent().getStringExtra(SEARCH_STRING_DATA));

        RecyclerView searchHistoryRecyclerView = findViewById(R.id.SearchActivity_search_history_recycler_view);
        searchHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        historyAdapter = new SearchHistoryAdapter(this, searchHistoryList);
        searchHistoryRecyclerView.setAdapter(historyAdapter);

        addListener(R.id.SearchActivity_input_edit_text);
        addListener(R.id.SearchActivity_search_button);
        addListener(R.id.SearchActivity_search_cancel_button);

        loadHistory();
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

    /**
     * 加载历史记录
     */
    public void loadHistory(){
        int ed = (int)(Math.random() * 12);
        for (int i = 0; i < ed; i++){
            searchHistoryList.add(new SearchHistoryItem("ss" + i, GregorianCalendar.getInstance().getTime()));
        }
        if(searchHistoryList.size() > 0){
            searchHistoryList.add(new SearchHistoryItem());
        }
        historyAdapter.notifyDataSetChanged();
    }
    @Override
    public void addListener(final int res) {
        if(res == R.id.SearchActivity_input_edit_text){
            EditText editText = findViewById(res);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length() == 0)
                        findViewById(R.id.SearchActivity_search_cancel_button).setVisibility(View.GONE);
                    else
                        findViewById(R.id.SearchActivity_search_cancel_button).setVisibility(View.VISIBLE);
                }
            });
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH){
                        findViewById(R.id.SearchActivity_search_button).performClick();
                        return true;
                    }
                    return false;
                }
            });
            return;
        }
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            EditText editText = findViewById(R.id.SearchActivity_input_edit_text);
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.SearchActivity_search_button:
                        String tmpString = editText.getText().toString();
                        if(TextUtils.isEmpty(tmpString))
                            break;
                        if(searchHistoryList.size() == 0)
                            searchHistoryList.add(new SearchHistoryItem());
                        for (int i = 0; i < searchHistoryList.size(); i++){
                            if(searchHistoryList.get(i).getItemText().equals(tmpString)){
                                searchHistoryList.remove(i);
                                break;
                            }
                        }
                        SearchHistoryItem item = new SearchHistoryItem(tmpString, GregorianCalendar.getInstance().getTime());
                        searchHistoryList.add(0, item);
                        SearchResultActivity.startMe(SearchActivity.this, tmpString);
                        finish();
                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.SearchActivity_search_cancel_button:
                        editText.setText("");
                        findViewById(R.id.SearchActivity_search_history_recycler_view).setVisibility(View.VISIBLE);
                        findViewById(R.id.SearchActivity_search_match_recycler_view).setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    public static void startMe(Context context){
        context.startActivity(new Intent(context, SearchActivity.class));
        ((BaseActivity)context).overridePendingTransition(Animation.ABSOLUTE, Animation.ABSOLUTE);
    }
    public static void startMe(Context context, String data){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(SEARCH_STRING_DATA, data);
        context.startActivity(intent);
    }
}
