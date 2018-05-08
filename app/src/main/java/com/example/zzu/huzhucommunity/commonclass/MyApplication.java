package com.example.zzu.huzhucommunity.commonclass;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by FEI on 2018/1/26.
 * 全局Context
 */

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }

//    /**
//     * 选择照片对话框
//     * @param context1 上下文
//     */
//    public static void startPickImageDialog(final Context context1){
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context1);
//        dialog.setItems(R.array.AddImageFrom, new DialogInterface.OnClickListener() {
//            Intent intent;
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(which == 0){
//                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    ((Activity)context1).startActivityForResult(intent, Constants.PICK_IMAGE_FROM_CAMERA);
//                }
//                else{
//                    intent = new Intent();
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    intent.setType("image/*");
//                    ((Activity)context1).startActivityForResult(intent, Constants.PICK_IMAGE_FROM_GALLERY);
//                }
//            }
//        });
//        dialog.show();
//    }
//    public static Bitmap getImageFromDialog(int requestCode, int resultCode, Intent data){
//        if (resultCode == Activity.RESULT_OK){
//            switch (requestCode) {
//                case Constants.PICK_IMAGE_FROM_CAMERA:
//                    Bundle bundle = data.getExtras();
//                    if (bundle != null) {
//                        return (Bitmap) bundle.get("data");
//                    }
//                    return null;
//                case Constants.PICK_IMAGE_FROM_GALLERY:
//                    Uri uri = data.getData();
//                    if (uri == null)
//                        return null;
//                    ContentResolver contentResolver = getContext().getContentResolver();
//                    try {
//                        return BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                        return null;
//                    }
//            }
//        }
//        return null;
//    }
//    /**
//     * This method converts dp unit to equivalent pixels, depending on device density.
//     *
//     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
//     * @return A float value to represent px equivalent to dp depending on device density
//     */
//    public static float convertDpToPixel(float dp){
//        Resources resources = context.getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
//    }
//
//    /**
//     * This method converts device specific pixels to density independent pixels.
//     *
//     * @param px A value in px (pixels) unit. Which we need to convert into db
//     * @return A float value to represent dp equivalent to px value
//     */
//    public static float convertPixelsToDp(float px){
//        Resources resources = context.getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        return px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
//    }
//
//    /**
//     * 将毫秒级的时间转换为格式为 year-month-day hour:minute:second 的时间字符串
//     * @param timeInMills 待转换的毫秒数
//     * @return 返回转换后的字符串
//     */
//    public static String convertTimeInMillToString(long timeInMills){
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTimeInMillis(timeInMills);
//        return calendar.get(Calendar.YEAR) + "-"
//                + calendar.get(Calendar.MONTH) + "-"
//                + calendar.get(Calendar.DAY_OF_MONTH) + " "
//                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
//                + calendar.get(Calendar.MINUTE) + ":"
//                + calendar.get(Calendar.SECOND);
//    }
}
