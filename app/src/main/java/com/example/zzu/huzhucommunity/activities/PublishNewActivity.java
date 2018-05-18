package com.example.zzu.huzhucommunity.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.Publish;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class PublishNewActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String TAG = "PublishNewActivity";
    public static final int PUBLISH_NEW_RESOURCE = 1;
    public static final int PUBLISH_NEW_REQUEST = 2;

    private static final String PUBLISH_SUCCESS = "发布成功";
    private static final String COMPLETE_RES_REQ_INFO = "请完善信息";

    private int publishWhich;

    private Calendar calendar = GregorianCalendar.getInstance();
    private ArrayList<Bitmap> bitmaps;

    private TextView dateTextView, timeTextView;
    private EditText titleEditText, contentEditText, priceEditText;
    private Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_new_layout);
        Toolbar toolbar = findViewById(R.id.PublishNewRes_toolbar);
        int which = getIntent().getIntExtra(MainActivity.PUBLISH_TYPE, PUBLISH_NEW_RESOURCE);

        publishWhich = which;
        if(which == PUBLISH_NEW_REQUEST)
            toolbar.setTitle(R.string.publishNewRequest);
        else if(which == PUBLISH_NEW_RESOURCE)
            toolbar.setTitle(R.string.publishNewResource);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        bitmaps = new ArrayList<>();
        dateTextView = findViewById(R.id.PublishNewRes_date_text_view);
        timeTextView = findViewById(R.id.PublishNewRes_time_text_view);
        titleEditText = findViewById(R.id.PublishNewRes_title_edit_text);
        contentEditText = findViewById(R.id.PublishNewRes_content_edit_text);
        priceEditText = findViewById(R.id.PublishNewRes_price_edit_text);
        typeSpinner = findViewById(R.id.PublishNewRes_type_spinner);

        int year = calendar.get(Calendar.YEAR), mon = calendar.get(Calendar.MONTH) + 1,
                day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY), min = calendar.get(Calendar.MINUTE);
        String curDate = Utilities.formatTime(year, mon, day);
        String curTime = Utilities.formatTime(hour, min);
        dateTextView.setText(curDate);
        timeTextView.setText(curTime);

        Spinner typeSpinner = findViewById(R.id.PublishNewRes_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Types, R.layout.spinner_type_item_view);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_grade_item_drop_down_view);
        typeSpinner.setAdapter(arrayAdapter);

        addListener(R.id.PublishNewRes_add_image_button);
        addListener(R.id.PublishNewRes_time_text_view);
        addListener(R.id.PublishNewRes_date_text_view);
        addListener(R.id.PublishNewRes_price_edit_text);
        addListener(R.id.PublishNewRes_publish_button);

        setResult(RESULT_CANCELED);
    }

    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.PublishNewRes_add_image_button:
                        Utilities.StartPickImageDialog(PublishNewActivity.this);
                        break;
                    case R.id.PublishNewRes_time_text_view:
                        TimePickerDialog timePickerDialog = new TimePickerDialog(PublishNewActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        timeTextView.setText(Utilities.formatTime(hourOfDay, minute));
                                    }
                                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        timePickerDialog.show();
                        break;
                    case R.id.PublishNewRes_date_text_view:
                        DatePickerDialog datePickerDialog = new DatePickerDialog(PublishNewActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        dateTextView.setText(Utilities.formatTime(year, month + 1, dayOfMonth));
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                        break;
                    case R.id.PublishNewRes_price_edit_text:
                        ScrollView scrollView = findViewById(R.id.PublishNewRes_scroll_view);
                        scrollView.smoothScrollTo(0, 320);
                        break;
                    case R.id.PublishNewRes_publish_button:
                        publishNew();
                        break;
                }
            }
        });
    }

    /**
     * 发布新资源
     */
    public void publishNew(){
        String userID = Utilities.GetStringLoginUserId();
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString(),
                date = dateTextView.getText().toString(),
                time = timeTextView.getText().toString() + ":00",
                price = priceEditText.getText().toString();
        String type = (String) typeSpinner.getSelectedItem();
        if (TextUtils.isEmpty(time) || TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(price)){
            Toast.makeText(MyApplication.getContext(), COMPLETE_RES_REQ_INFO, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (publishWhich){
            case PUBLISH_NEW_REQUEST:
                Publish.getOurInstance().publishResource(userID, PUBLISH_NEW_REQUEST + "", title, content,
                        bitmaps.size() + "", price, date + " " + time, this);
                break;
            case PUBLISH_NEW_RESOURCE:
                Publish.getOurInstance().publishResource(userID, PUBLISH_NEW_RESOURCE + "", title, content,
                        bitmaps.size() + "", price, date + " " + time, this);
                break;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LinearLayout layout = findViewById(R.id.PublishNewRes_image_holder);
        ImageView imageView = new ImageView(PublishNewActivity.this);
        imageView.setLayoutParams((findViewById(R.id.PublishNewRes_add_image_button)).getLayoutParams());
        switch (requestCode){
            case Utilities.PICK_IMAGE_FROM_GALLERY:
            case Utilities.PICK_IMAGE_FROM_CAMERA:
                Bitmap bitmap = Utilities.GetImageFromDialog(requestCode, resultCode, data);
                if (bitmap != null){
                    imageView.setImageBitmap(bitmap);
                    layout.addView(imageView, 0);
                    bitmaps.add(bitmap);
                }
                break;
        }
    }

    public static void startMe(Activity context, int typeExtra, int reqCode){
        Intent intent = new Intent(context, PublishNewActivity.class);
        intent.putExtra(MainActivity.PUBLISH_TYPE, typeExtra);
        context.startActivityForResult(intent, reqCode);
    }

    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        if (statusCode != 200){
            Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case Publish.PUBLISH_RES_REQ:
                String resID = mp.get(Publish.PUBLISH_RET_RES_ID_KEY);
                int bitmapSz = bitmaps.size();

                if (bitmapSz == 0){
                    Toast.makeText(MyApplication.getContext(), PUBLISH_SUCCESS, Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                else if (bitmapSz == 1){
                    Publish.getOurInstance().uploadImage(bitmaps.get(0), resID + "_1", this);
                }
                else{
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 0; i < bitmapSz; i++)
                        names.add(resID + "_" + (i + 1));
                    Publish.getOurInstance().uploadImage(bitmaps, names, this);
                }
                break;
            case Publish.UPLOAD_IMAGE:
                Toast.makeText(MyApplication.getContext(), PUBLISH_SUCCESS, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
    }
}
