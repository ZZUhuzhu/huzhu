package com.example.zzu.huzhucommunity.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PublishNewResActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Calendar calendar = GregorianCalendar.getInstance();
    private TextView dateTextView;
    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_new_res_layout);
        Toolbar toolbar = findViewById(R.id.PublishNewRes_toolbar);
        int which = getIntent().getIntExtra("publishType", 0);
        if(which == MainActivity.PUBLISH_NEW_REQUEST)
            toolbar.setTitle(R.string.publishNewRequest);
        else if(which == MainActivity.PUBLISH_NEW_RESOURCE)
            toolbar.setTitle(R.string.publishNewResource);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String curDate = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        String curTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        dateTextView = findViewById(R.id.PublishNewRes_date_text_view);
        timeTextView = findViewById(R.id.PublishNewRes_time_text_view);
        dateTextView.setText(curDate);
        timeTextView.setText(curTime);

        Spinner typeSpinner = findViewById(R.id.PublishNewRes_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Types, R.layout.type_spinner_item_view);
        arrayAdapter.setDropDownViewResource(R.layout.grade_spinner_item_drop_down_view);
        typeSpinner.setAdapter(arrayAdapter);

        addListener(R.id.PublishNewRes_add_image_button);
        addListener(R.id.PublishNewRes_time_text_view);
        addListener(R.id.PublishNewRes_date_text_view);
        addListener(R.id.PublishNewRes_price_edit_text);
        addListener(R.id.PublishNewRes_publish_button);
    }
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.PublishNewRes_add_image_button:
                        intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, PICK_IMAGE);
                        break;
                    case R.id.PublishNewRes_time_text_view:
                        TimePickerDialog timePickerDialog = new TimePickerDialog(PublishNewResActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String hour = "" + hourOfDay, min = "" + minute;
                                        if(hour.length() == 1) hour = "0" + hour;
                                        if(min.length() == 1) min = "0" + min;
                                        String pickedTime = hour + ":" + min;
                                        timeTextView.setText(pickedTime);
                                    }
                                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        timePickerDialog.show();
                        break;
                    case R.id.PublishNewRes_date_text_view:
                        DatePickerDialog datePickerDialog = new DatePickerDialog(PublishNewResActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        String setDate = year + "/" + (month + 1) + "/" + dayOfMonth;
                                        dateTextView.setText(setDate);
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                        break;
                    case R.id.PublishNewRes_price_edit_text:
                        ScrollView scrollView = findViewById(R.id.PublishNewRes_scroll_view);
                        scrollView.smoothScrollTo(0, 320);
                        break;
                    case R.id.PublishNewRes_publish_button:
                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE){
            if(resultCode == RESULT_OK){
                ImageView imageView = new ImageView(PublishNewResActivity.this);
                imageView.setLayoutParams(( findViewById(R.id.PublishNewRes_add_image_button)).getLayoutParams());
//                imageView.setBackground(null);
                Uri uri = data.getData();
                if(uri == null) return;
                ContentResolver contentResolver = getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                    imageView.setImageBitmap(bitmap);
                    LinearLayout layout = findViewById(R.id.PublishNewRes_image_holder);
                    layout.addView(imageView, 0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
