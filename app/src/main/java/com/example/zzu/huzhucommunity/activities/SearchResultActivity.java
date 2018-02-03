package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zzu.huzhucommunity.R;

public class SearchResultActivity extends BaseActivity {
    private static final String INTENT_DATA_NAME = "SEARCH_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_layout);
        Toolbar toolbar = findViewById(R.id.SearchResultActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        TextView textView = findViewById(R.id.SearchResultActivity_input_text_view);
        textView.setText(getIntent().getStringExtra(INTENT_DATA_NAME));
    }

    @Override
    public void addListener(int res) {
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
