package com.rongfeng.speedclient.common.utils;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.AmapLbsLocationManager;
import com.rongfeng.speedclient.common.BaseTransModel;
import com.rongfeng.speedclient.common.CameraNoMarkActivity;
import com.rongfeng.speedclient.common.CameraWaterMarkActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.GlideRoundTransform;
import com.rongfeng.speedclient.common.RoundedCornersTransformation;
import com.rongfeng.speedclient.components.SelectionDialog;
import com.rongfeng.speedclient.components.SelectionDialogListAdapter;
import com.rongfeng.speedclient.components.SelectionVoiceDialog;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.datanalysis.DBManager;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.login.Enterprise;
import com.rongfeng.speedclient.login.User;
import com.rongfeng.speedclient.permisson.PermissionsChecker;
import com.rongfeng.speedclient.utils.DensityUtil;
import com.rongfeng.speedclient.utils.FlowLayout;
import com.rongfeng.speedclient.voice.AreaDBManager;
import com.rongfeng.speedclient.voice.model.AreaModel;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * AUTHOR: Alex
 * DATE: 12/11/2015 09:47
 */
public class AppTools {


    /**
     * Bean Convert Map
     *
     * @param targetBean
     * @return
     */
    public static Map<String, String> toMap(Object targetBean) {

        Map<String, String> result = new IdentityHashMap<String, String>();
        result.putAll(AppTools.toMap());
        Method[] methods = targetBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(targetBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
            }
        }


        return result;
    }

    /**
     * Bean Convert Map
     *
     * @return
     */
    public static Map<String, String> toMap() {
        BaseTransModel commonBean = new BaseTransModel();
        Map<String, String> result = new IdentityHashMap<>();
        Method[] commonMethods = commonBean.getClass().getDeclaredMethods();

        for (Method method : commonMethods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(commonBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
            }
        }

        return result;
    }

    /**
     * 打开定位
     *
     * @param listener
     * @param isOnce   是否只定位一次
     */
    public static void startLbsLocation(AMapLocationListener listener, boolean isOnce) {
        AmapLbsLocationManager.getInstance().startLbsLocation(listener, isOnce);
    }

    /**
     * 停止定位
     */
    public static void stopLbsLocation() {
        AmapLbsLocationManager.getInstance().stopLbsLocation();

    }

    /**
     * 获取日期
     *
     * @param textView
     * @return
     */
    public static void obtainData(Activity activity, final TextView textView) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(inspectDate(year, monthOfYear, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    /**
     * 获取日期
     *
     * @param textView
     * @return
     */
    public static void obtainData(final Activity activity, final TextView textView, final String pattern) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.set(year, monthOfYear, dayOfMonth);
                Date date = new Date(cal.getTimeInMillis());
                textView.setText(DateUtil.dateToStr(date, pattern));
                textView.setTag(DateUtil.dateToStr(date));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static void obtainData(Context context, final Handler handler, final int what) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.set(year, monthOfYear, dayOfMonth);
                Date date = new Date(cal.getTimeInMillis());
                handler.sendMessage(handler.obtainMessage(what, date));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 获取日期时间
     *
     * @param textView
     */
    public static void obtainDataAndTime(final Activity activity, final TextView textView) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            boolean flag = true;

            @Override
            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {

                if (flag) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            textView.setText(inspectDate(year, monthOfYear, dayOfMonth) + "  " + inspectTime(hourOfDay, minute));

                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                    timePickerDialog.show();
                    flag = false;

                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

    /**
     * 获取时间
     *
     * @param textView
     */
    public static void obtainTime(final Activity activity, final TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(inspectTime(hourOfDay, minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    /**
     * 设置日期差
     *
     * @param activity
     * @param dateTextView  需要赋值
     * @param startTextView 开始日期
     * @param endTextView   结束日期
     * @param daysTextView  天数
     */
    public static void obtainDays(final Activity activity, final TextView dateTextView, final TextView startTextView, final TextView endTextView, final TextView daysTextView) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateTextView.setText(inspectDate(year, monthOfYear, dayOfMonth));
                String days = DateUtil.getDays(startTextView.getText().toString(), endTextView.getText().toString());
                if (!TextUtils.isEmpty(days)) {
                    daysTextView.setText(days);
                    daysTextView.setTag(days);
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 转换日期格式
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String inspectDate(int year, int monthOfYear, int dayOfMonth) {

        String tempMonth = String.valueOf((monthOfYear + 1));
        String tempDay = String.valueOf((dayOfMonth));

        if ((monthOfYear + 1) < 10) {
            tempMonth = "0" + String.valueOf((monthOfYear + 1));
        }
        if (dayOfMonth < 10) {
            tempDay = "0" + String.valueOf((dayOfMonth));
        }
        String dateString = year + "-" + tempMonth + "-" + tempDay;

        return dateString;

    }

    /**
     * 转换日期格式
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String inspectDateSlip(int year, int monthOfYear, int dayOfMonth) {

        String tempMonth = String.valueOf((monthOfYear + 1));
        String tempDay = String.valueOf((dayOfMonth));

        if ((monthOfYear + 1) < 10) {
            tempMonth = "0" + String.valueOf((monthOfYear + 1));
        }
        if (dayOfMonth < 10) {
            tempDay = "0" + String.valueOf((dayOfMonth));
        }
        String dateString = year + "-" + tempMonth + "-" + tempDay;

        if (dayOfMonth == 0) {
            dateString = year + "-" + tempMonth;
        }
        return dateString;

    }

    /**
     * 格式化事件
     *
     * @param hourOfDay
     * @param minute
     * @return
     */
    public static String inspectTime(int hourOfDay, int minute) {

        String tempHour = String.valueOf((hourOfDay));
        String tempMin = String.valueOf((minute));

        if ((hourOfDay) < 10) {
            tempHour = "0" + String.valueOf(hourOfDay);
        }
        if (minute < 10) {
            tempMin = "0" + String.valueOf((minute));
        }
        String timeStr = tempHour + ":" + tempMin;

        return timeStr;

    }

    /**
     * 调用拍照、相册
     *
     * @param activity
     * @param handler
     */

    public static void selectPhotoShow(Activity activity, Handler handler, int selectSign) {

        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_PICTURE)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(activity, ConstantPermission.PERMISSIONS_PICTURE);
        } else {
            List<BaseDataModel> data = new ArrayList<>();
            BaseDataModel model = new BaseDataModel();
            model.setDictionaryName(activity.getString(R.string.camera_picture));
            BaseDataModel modelT = new BaseDataModel();
            modelT.setDictionaryName(activity.getString(R.string.photo_picture));

            data.add(model);
            data.add(modelT);
            selectDialog("请选择", activity, data, handler, selectSign);
        }

    }


    /**
     * 调用系统相册
     *
     * @param activity
     */
    public static void getSystemImage(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, Constant.SELECT_PICTURE);

//        Intent intent = new Intent(activity, SelectPictureActivity.class);
//        intent.putExtra(SelectPictureActivity.IS_MULTI_SELECT, false);
//        activity.startActivityForResult(intent, Constant.SELECT_PICTURE);
    }

    /**
     * 调用系统相册 Fragment
     *
     * @param fragment
     */
    public static void getSystemImage(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, Constant.SELECT_PICTURE);
    }

    /**
     * 水印相机
     *
     * @param activity
     */
    public static void getCapturePath(Activity activity) {

        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_PICTURE)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(activity, ConstantPermission.PERMISSIONS_PICTURE);
        } else {
            AppTools.getCapturePath(activity, null);
        }
    }

    /**
     * 水印相机
     *
     * @param activity fragment
     */
    public static void getCapturePath(Activity activity, Fragment fragment) {
        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_PICTURE)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(activity, ConstantPermission.PERMISSIONS_PICTURE);
        } else {
            String path = getImageSavePath(activity) + "/"
                    + DateUtil.getTime(DateUtil.yyyyMMddHHmmss) + ".jpg";
            String permission = "android.permission.CAMERA";
            if (!AppTools.isOpenPermissions(activity, permission)) {
                AppTools.showNoSetDlg(activity, "您好，摄像头权限未开启！");
                return;
            }
            AppConfig.setStringConfig("cameraPath", path);
            Intent cameraIntent = new Intent(activity, CameraWaterMarkActivity.class);
            cameraIntent.putExtra("path", path);
            if (fragment != null) {
                fragment.startActivityForResult(cameraIntent,
                        Constant.CAMERA_REQUEST_CODE);
            } else {
                activity.startActivityForResult(cameraIntent,
                        Constant.CAMERA_REQUEST_CODE);

            }
        }


    }

    /**
     * 无水印相机
     *
     * @param activity fragment
     */
    public static void getCapturePathNoWater(Activity activity, Fragment fragment) {

        String path = getImageSavePath(activity) + "/"
                + DateUtil.getTime(DateUtil.yyyyMMddHHmmss) + ".jpg";
        String permission = "android.permission.CAMERA";
        if (!AppTools.isOpenPermissions(activity, permission)) {
            AppTools.showNoSetDlg(activity, "您好，摄像头权限未开启！");
            return;
        }
        AppConfig.setStringConfig("cameraPath", path);
        Intent cameraIntent = new Intent(activity, CameraNoMarkActivity.class);
        cameraIntent.putExtra("path", path);
        if (fragment != null) {
            fragment.startActivityForResult(cameraIntent,
                    Constant.CAMERA_REQUEST_CODE);
        } else {
            activity.startActivityForResult(cameraIntent,
                    Constant.CAMERA_REQUEST_CODE);

        }

    }


    /**
     * 方法名:获取图片文件存取路径
     * <p>
     * 功能说明：获取图片文件存取路径
     * </p>
     *
     * @return
     */
    public static String getImageSavePath(Activity activity) {
        if (AppTools.getSDPath().equals("")) {
            return activity.getFilesDir().getPath();
        }
        File file = new File(AppTools.getSDPath() + "/XXB/profession/dcim");
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file.getPath();
            }
            return "";
        }
        return file.getPath();
    }

    /**
     * 方法名:获取文件存取路径
     * <p>
     * 功能说明：获取文件存取路径
     * </p>
     *
     * @return
     */
    public static String getFileSavePath(Context context) {
        if (AppTools.getSDPath().equals("")) {
            return context.getFilesDir().getPath();
        }
        File file = new File(AppTools.getSDPath() + "/XXB/downloads");
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file.getPath();
            }
            return "";
        }
        return file.getPath();
    }

    /**
     * 方法名: 判断SD卡是否存在
     * <p>
     * 功能说明： 判断SD卡是否存在, 如果存在返回SD卡路径 , 如果不存在返回路径为空
     * </p>
     *
     * @return
     */
    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.toString();
        }
        return "";
    }

    /**
     * 更具uri获取绝对路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getAbsolutePath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        if (uri.toString().contains("file://")) {

            String path = uri.toString().replace("file://", "");

            return Uri.decode(path);
        }
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null,
                null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    /**
     * 日期转换 .  / -
     *
     * @param date
     * @return
     */
    public static String convertDateFormat(String date) {

        String result = "";
        if (!TextUtils.isEmpty(date) && date.contains(".")) {
            result = date.replace(".", "-");
        } else if (!TextUtils.isEmpty(date) && date.contains("-")) {
            result = date.replace("-", ".");
        }

        return result;
    }

    /**
     * Toast
     *
     * @param contentStr
     */
    public static void getToast(String contentStr) {
        Toast.makeText(AppConfig.getContext(), contentStr, Toast.LENGTH_SHORT).show();

    }

    /*
        *  获取登录信息
        * @return
        */
    public static Enterprise getEnterPrise() {
        Enterprise enterprise = new Enterprise();
        String str = AppConfig.getStringConfig("enterPrise", null);
        if (!TextUtils.isEmpty(str)) {
            enterprise = new GsonBuilder().create().fromJson(str, new TypeToken<Enterprise>() {
            }.getType());
        }
        return enterprise;
    }


    /**
     * 保存登录信息
     * <p>
     * //     * @param enterprise
     */
    public static void saveEnterpriseModel(Enterprise enterprise) {
        String userModelString = new GsonBuilder().create().toJson(enterprise);
        AppConfig.setStringConfig("enterPrise", userModelString);
    }

    /*
     *  获取登录人对象
     * @return
     */
    public static User getUser() {
        User user = new User();
        String str = AppConfig.getStringConfig("userModel", null);
        if (!TextUtils.isEmpty(str)) {
            user = new GsonBuilder().create().fromJson(str, new TypeToken<User>() {
            }.getType());
        }
        return user;
    }


    /**
     * 保存用户登录信息
     *
     * @param userModel
     */
    public static void saveUserModel(User userModel) {
        String userModelString = new GsonBuilder().create().toJson(userModel);
        AppConfig.setStringConfig("userModel", userModelString);
        AppConfig.setStringConfig("userAccount", userModel.getUserAccount());
        AppConfig.setStringConfig("login", "1"); //启动页面
    }

    /**
     * 退出当前账号
     */
    public static void clearUser() {
        AppConfig.setStringConfig("userModel", "");
        AppConfig.setStringConfig("userAccount", "");
    }

    /**
     * 是否打开权限 5.0 以上不起作用
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean isOpenPermissions(Context context, String permission) {
        int p = context.checkPermission(permission, Binder.getCallingPid(), Binder.getCallingUid());
        if (PackageManager.PERMISSION_DENIED == p) {
            return false;
        } else
            return true;
    }

    /**
     * 当判断权限时选择是否打开设置
     *
     * @param context
     */
    public static void showNoSetDlg(final Context context, String message) {
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher) //
                .setTitle(R.string.app_name) //
                .setMessage(message).setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // 跳转到系统的网络设置界面
                Intent intent = null;
                // 先判断当前系统版本
                if (android.os.Build.VERSION.SDK_INT > 10) { // 3.0以上
                    intent = new Intent(android.provider.Settings.ACTION_SETTINGS);

                } else {
                    intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings");
                }
                context.startActivity(intent);

            }
        }).setNegativeButton("知道了", null).show();
    }

    /**
     * 转换布尔值
     *
     * @param flag
     * @return
     */
    public static boolean getBoolean(boolean flag) {

        return flag ? false : true;
    }

    /**
     * 忽略map中指定字段
     *
     * @param map
     * @param property
     * @return
     */
    public static Map<String, String> ignoreProperty(Map<String, String> map, String property) {
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (property.equals(key)) {
                iterator.remove();        //删除createTime字段
                map.remove(key);
            }
        }
        return map;
    }

    /**
     * 获取本地数据字典
     *
     * @return
     */
    public static List<BaseDataModel> generationDictionary(String[] strs) {

        List<BaseDataModel> data = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            BaseDataModel model = new BaseDataModel();
            model.setDictionaryId(i + "");
            model.setDictionaryName(strs[i]);
            data.add(model);
        }
        return data;
    }

    /**
     * 发短信
     *
     * @param phone
     */
    public static void sendSMS(Context context, String phone) {
        Uri smsToUri = Uri.parse("smsto:" + (TextUtils.isEmpty(phone) ? "" : phone));
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }

    /**
     * 打电话
     *
     * @param context
     * @param phone
     */
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + (TextUtils.isEmpty(phone) ? "" : phone)));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 异步加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void setImageViewPicture(Context context, String url, ImageView imageView) {

        Glide.with(context).load(url).placeholder(R.drawable.defaultavatar).error(R.drawable.defaultavatar).transform(new GlideRoundTransform(context)).into(imageView);

    }

    /**
     * 异步加载方块图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void setImageViewClub(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.photo_none).error(R.drawable.photo_none).into(imageView);
    }

    /**
     * 异步加载图片 圆角矩形
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void setImageViewPictureRoundRect(Context context, String url, ImageView imageView) {

        Glide.with(context).load(url).bitmapTransform(new RoundedCornersTransformation(context, DensityUtil.dip2px(context, 2), 0, RoundedCornersTransformation.CornerType.TOP)).into(imageView);

    }

    /**
     * 切换动画效果
     *
     * @param view
     * @param alpha
     * @param startScaleX
     * @param startScaleY
     * @param duration
     */
    public static void fadeInView(View view, float alpha, float startScaleX, float startScaleY, int duration) {
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", alpha, 1f);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", startScaleX, 1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", startScaleY, 1f);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorAlpha).with(animatorScaleX).with(animatorScaleY);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    /**
     * 千分位
     *
     * @param s
     * @return
     */
    public static String getNumKb(String s) {
        if (!TextUtils.isEmpty(s)) {
            NumberFormat formatter = new DecimalFormat("###,###");
            String result = formatter.format(Double.parseDouble(s));
            return result;
        }
        return s;
    }

    /**
     * 千分位 两位小数
     *
     * @param s
     * @return
     */
    public static String getNumKbDot(String s) {
        if (!TextUtils.isEmpty(s)) {
            NumberFormat formatter = new DecimalFormat("###,##0.00");
            String result = formatter.format(Double.parseDouble(s));
            return result;
        }
        return "0.00";
    }

    public static String getNumKbDotSpecile(String s) {
        if (!TextUtils.isEmpty(s)) {
            NumberFormat formatter = new DecimalFormat("###,##0.0000");
            String result = formatter.format(Double.parseDouble(s));
            return result;
        }
        return s;
    }

    /**
     * 是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取本机wifi mac 地址
     *
     * @return
     */
    public static String getWifiMacAddress() {
        WifiManager wifiMan = (WifiManager) AppConfig.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMan.getConnectionInfo();
        String mac = info.getMacAddress();// 获得本机的MAC地址
        return mac;
    }

    /**
     * 获取BUGHD需要的信息
     */
//    public static void obtainBugHdInfo() {
//        FIR.addCustomizeValue("UserInfo", AppTools.getUser().getUserId() + "  " + AppTools.getUser().getUserName());
//        FIR.addCustomizeValue("TenementInfo", AppTools.getUser().getTenementId() + "  " + AppTools.getUser().getTenementName());
//        FIR.addCustomizeValue("SchemaFlag", AppTools.getUser().getSchemaFlag());
//        FIR.addCustomizeValue("AppInfo", DeviceInfoUtil.instance().getAppInfo());
//        FIR.addCustomizeValue("OSInfo", DeviceInfoUtil.instance().getDeviceInfo());
//    }


    /**
     * 方法名:getFileByte
     * <p>
     * 功能说明：将字节数转换成文件流
     * </p>
     *
     * @param filePath
     * @return
     */
    public static String getFileByte(String filePath) {
        int count;
        int num = 0;
        File file = new File(filePath);
        long len = file.length();
        if (file.exists()) {
            FileInputStream fis = null;
            StringBuffer sb = new StringBuffer();
            try {
                fis = new FileInputStream(file);
                byte[] buffer = new byte[(int) len];
                while ((count = fis.read(buffer)) != -1) {
                    sb.append(Base64.encodeToString(buffer, Base64.DEFAULT));
                    num = count++;
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 比较两个日期
     *
     * @param activity
     * @param textVie
     * @param textView
     */
    public static void obtainDataCompare(final Activity activity, final TextView textVie, final TextView textView) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int isFore = DateUtil.compareDate(textVie.getText().toString(), AppTools.inspectDate(year, monthOfYear, dayOfMonth), DateUtil.yyyyMMdd);
                if (isFore != -1) {
                    AppTools.getToast("截止时间不能早于起始时间");
                    textView.setText("");
                } else {
                    textView.setText(AppTools.inspectDate(year, monthOfYear, dayOfMonth));
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 判断是否大于当前日期
     *
     * @param activity
     * @param textView
     */
    public static void obtainDataCompareNow(final Activity activity, final TextView textView) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int isFore = DateUtil.compareDate(DateUtil.getStringByDate(new Date(), DateUtil.yyyyMMdd), AppTools.inspectDate(year, monthOfYear, dayOfMonth), DateUtil.yyyyMMdd);
                if (isFore != -1) {
                    textView.setText(AppTools.inspectDate(year, monthOfYear, dayOfMonth));
                } else {
                    AppTools.getToast("不能大于当前日期");
                    textView.setText("");
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 判断是否大于当前日期 "-"
     *
     * @param activity
     * @param textView
     */
    public static void obtainDataCompareNowSlip(final Activity activity, final TextView textView) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int isFore = DateUtil.compareDate(DateUtil.getStringByDate(new Date(), DateUtil.yyyy_MM_dd), AppTools.inspectDateSlip(year, monthOfYear, dayOfMonth), DateUtil.yyyy_MM_dd);
                if (isFore != -1) {
                    textView.setText(AppTools.inspectDateSlip(year, monthOfYear, dayOfMonth));
                } else {
                    AppTools.getToast("不能大于当前日期");
                    textView.setText("");
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 比较两个日期
     *
     * @param activity
     * @param textVie
     * @param textView
     */
    public static void obtainDataCompareSlip(final Activity activity, final TextView textVie, final TextView textView) {
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int isFore = DateUtil.compareDate(textVie.getText().toString(), AppTools.inspectDateSlip(year, monthOfYear, dayOfMonth), DateUtil.yyyy_MM_dd);
                if (isFore != -1 && isFore != 0) {
                    AppTools.getToast("截止时间不能早于起始时间");
                    textView.setText("");
                } else {
                    textView.setText(AppTools.inspectDateSlip(year, monthOfYear, dayOfMonth));
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 遍历参数
     *
     * @param map
     */
    public static void ergodicParameters(Map<String, String> map) {
        if (map != null) {
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                Log.i("Retrofit", "Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        }
    }

    /**
     * 无数据View
     *
     * @param context
     * @param resid
     * @return
     */
    public static View getNodataView(Context context, int resid) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View notdataView = LayoutInflater.from(context).inflate(R.layout.no_data_layout, null);
        notdataView.setLayoutParams(lp);
        ((ImageView) notdataView.findViewById(R.id.nodata_image)).setBackgroundResource(resid);
        return notdataView;
    }


    /**
     * 图片压缩
     *
     * @param paths
     * @return
     */
    public static List<String> getConvertImages(List<String> paths) {
        List<String> result = new ArrayList<>();

        for (String s : paths) {
            result.add(getCompressImage(s));
        }

        return result;
    }

    /**
     * 按比例压缩图片
     *
     * @param srcPath 路径
     * @return
     */
    public static String getCompressImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空


        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inJustDecodeBounds = false;

        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if (bitmap == null) {
            return null;
        }
        int degree = readPictureDegree(srcPath);
        if (degree != 0) {
            bitmap = rotateBitmap(bitmap, degree);
        }
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 按质量压缩
     *
     * @param image
     * @return
     */
    public static String compressImage(Bitmap image) {

        String path = getFileSavePath(AppConfig.getContext()) + "/" + (Math.random() * 9000 + 1000) + ".png";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 30;
//        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//            if (options <= 0) {
//                break;
//            }
//        }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            FileOutputStream stream = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }


    /**
     * 读取图片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转至0°
     *
     * @param bitmap
     * @param degree
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 清除图片缓存
     */
    public static void clearPictureCache() {
        String path = AppTools.getFileSavePath(AppConfig.getContext());
        File file = new File(path);
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < files.length; i++) {
                            files[i].delete();
                        }

                    }
                }).start();
            }

        }

    }

    /**
     * 判断程序是否在前台运行
     *
     * @param context
     * @return
     */
    public static boolean isAppOnForeground(Activity context) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses)

        {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    //隐藏虚拟键盘
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

    //显示虚拟键盘
    public static void showKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 弹出键盘
     *
     * @param mHandler
     * @param s
     */
    public static void openKeyboard(final Context mContext, Handler mHandler, int s) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, s);
    }

    /**
     * 校验登陆信息
     *
     * @param isLose
     */
    public static boolean verifyLoginInfo(String isLose, boolean isJumpToLogin) {

        boolean result = false;
        switch (isLose) {
            case "0":
                result = true;
                break;
            case "1":
                Toast.makeText(AppConfig.getContext(), "您的账号已过期，请联系管理员", Toast.LENGTH_SHORT).show();
                if (isJumpToLogin) {
//                    exitLogin();
                }
                break;
            case "2":
                Toast.makeText(AppConfig.getContext(), "该账号已绑定其他设备", Toast.LENGTH_SHORT).show();
                if (isJumpToLogin) {
//                    exitLogin();
                }
                break;
        }
        return result;
    }

    /**
     * 校验账号是否生效
     *
     * @param isEffect
     */
    public static boolean verifyLoginNoEffect(String isEffect, boolean isJumpToLogin) {

        boolean result = false;

        if (Integer.parseInt(isEffect) > 0) {
            Toast.makeText(AppConfig.getContext(), "您的账号未生效，请联系管理员", Toast.LENGTH_SHORT).show();
            if (isJumpToLogin) {
//                exitLogin();
            }
        } else {
            result = true;
        }

        return result;
    }


    public static String convertPinYin(String content) {
        //把汉字转串化拼音串
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < content.length(); j++) {
            char c = content.charAt(j);
            String[] vals;
            try {
                vals = PinyinHelper.toHanyuPinyinStringArray(c, format);

                if (vals == null) {
                    stringBuffer.append("null");
                    continue;
                }
                if (!TextUtils.isEmpty(vals[0]) && vals[0].length() > 1) {
                    vals[0] = vals[0].substring(0, vals[0].length() - 1);
                }
                stringBuffer.append(vals[0]);

            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        String key = stringBuffer.toString();
        return key;
    }


    /**
     * 选择框
     *
     * @param activity
     * @param data
     * @param handler
     */
    public static void selectDialog(String title, Activity activity, List<BaseDataModel> data, Handler handler, int selectSign) {
        AppTools.selectHeightDialog(title, activity, data, handler, selectSign, 0);
    }


    /**
     * 动态设置高度的选择框
     *
     * @param activity
     * @param data
     * @param handler
     * @param selectSign
     */
    public static void selectHeightDialog(String title, Activity activity, List<BaseDataModel> data, Handler handler, int selectSign, int height) {
        SelectionDialogListAdapter adapter = new SelectionDialogListAdapter(activity, R.layout.selection_dialog_listview_item, data);
        SelectionDialog dialog = new SelectionDialog(activity, R.layout.selection_dialog_listview_layout, handler, selectSign, height);

        dialog.buildDialog().setAdapter(adapter).setTitle(title);
    }


    /**
     * 语音界面弹出框
     *
     * @param title
     * @param activity
     * @param data
     * @param handler
     * @param selectSign
     */
    public static void selectVoiceDialog(String title, Activity activity, List<BaseDataModel> data, Handler handler, int selectSign) {
        AppTools.selectVoiceHeightDialog(title, activity, data, handler, selectSign, 0);
    }


    public static void selectVoiceHeightDialog(String title, Activity activity, List<BaseDataModel> data, Handler handler, int selectSign, int height) {
        SelectionDialogListAdapter adapter = new SelectionDialogListAdapter(activity, R.layout.selection_dialog_voice_listview_item, data);
        SelectionVoiceDialog dialog = new SelectionVoiceDialog(activity, R.layout.selection_dialog_voice_listview_layout, handler, selectSign, height);

        dialog.buildDialog().setAdapter(adapter).setTitle(title);
    }

    /**
     * label
     *
     * @param context
     * @param datas
     * @param flowLayout
     */
    public static void generationLabels(final Context context, List<BaseDataModel> datas, final FlowLayout flowLayout) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.height = DensityUtil.dip2px(context, 40);
        flowLayout.removeAllViews();

        for (int i = 0; i < datas.size(); i++) {
            final View view = LayoutInflater.from(context).inflate(R.layout.main_lable_edit_view, null);

            final TextView textView = (TextView) view.findViewById(R.id.label_tv);
            textView.setText(datas.get(i).getDictionaryName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AppConfig.getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            view.setLayoutParams(lp);
            flowLayout.addView(view);
        }

    }

    /**
     * 弹出对话框
     */
    public static void dialogShow(Context context) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);

//        switch (v.getId()){
//            case R.id.fadein:effect= Effectstype.Fadein;break;
//            case R.id.slideright:effect=Effectstype.Slideright;break;
//            case R.id.slideleft:effect=Effectstype.Slideleft;break;
//            case R.id.slidetop:effect=Effectstype.Slidetop;break;
//            case R.id.slideBottom:effect=Effectstype.SlideBottom;break;
//            case R.id.newspager:effect=Effectstype.Newspager;break;
//            case R.id.fall:effect=Effectstype.Fall;break;
//            case R.id.sidefall:effect=Effectstype.Sidefill;break;
//            case R.id.fliph:effect=Effectstype.Fliph;break;
//            case R.id.flipv:effect=Effectstype.Flipv;break;
//            case R.id.rotatebottom:effect=Effectstype.RotateBottom;break;
//            case R.id.rotateleft:effect=Effectstype.RotateLeft;break;
//            case R.id.slit:effect=Effectstype.Slit;break;
//            case R.id.shake:effect=Effectstype.Shake;break;
//        }

        dialogBuilder
                .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(context.getResources().getDrawable(R.mipmap.ic_launcher))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(200)                                          //def
                .withEffect(Effectstype.SlideBottom)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setCustomView(R.layout.custome_view, context)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }


    /**
     * 添加数据到数据库
     */
    public static void insertClientDataToDB(Context context, List<SyncClientInfoModel> data) {

        List<ClientModel> persons = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ClientModel m = new ClientModel();
            m.setClient_name(data.get(i).getCustomerName());
            m.setClient_info(data.get(i).getClientNameWordsSplit());
            m.setClient_id(data.get(i).getCsrId());
            persons.add(m);
        }

        DBManager dbManager = new DBManager(context);
        dbManager.truncate();
        if (persons.size() > 0) {
            dbManager.addClient(persons);
        }

        dbManager.closeDB();

    }


    /**
     * 清空表
     *
     * @param context
     */
    public static void clearForm(Context context) {
        DBManager dbManager = new DBManager(context);
        dbManager.truncate();

        dbManager.closeDB();
    }

    /**
     * 添加单个人到数据库
     */
    public static void insertClientDataToDB(Context context, SyncClientInfoModel data) {

        ClientModel m = new ClientModel();
        m.setClient_name(data.getCustomerName());
        m.setClient_info(data.getClientNameWordsSplit());
        m.setClient_id(data.getCsrId());


        DBManager dbManager = new DBManager(context);

        if (dbManager.queryTheClientCursor(data.getCsrId())) {
            dbManager.updateClient(m);
        }else{
            dbManager.addClient(m);
        }

        dbManager.closeDB();

    }


    /**
     * 上传用户词表
     */
    public static String getUploadClientNamesWordForm(List<SyncClientInfoModel> list) {

//        {"userword":[{"name":"我的常用词","words":["佳晨实业","蜀南庭苑"]}
//                ,{"name":"我的好友","words":["李馨琪","鹿晓雷"]}]}
        JSONObject wordsOjb = new JSONObject();
        JSONArray wordFormList = new JSONArray();
        JSONObject inner = new JSONObject();
        JSONArray innerNames = new JSONArray();

//        JSONObject inner2 = new JSONObject();
//        JSONArray innerNames2 = new JSONArray();
        try {
            inner.put("name", "客户名称词表");
            for (int i = 0; i < list.size(); i++) {
                innerNames.put(i, list.get(i).getCustomerName());
            }
            inner.put("words", innerNames);
            wordFormList.put(0, inner);

//            inner2.put("name", "我的好友");
//            innerNames2.put(0, "张范孚");
//            inner2.put("words", innerNames2);
//            wordFormList.put(1, inner2);

            wordsOjb.put("userword", wordFormList);
            return wordsOjb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * 删除数据库数据
     */
    public static void deleteClientDataToDB(Context context) {

        DBManager dbManager = new DBManager(context);
        dbManager.truncate();
        dbManager.closeDB();

    }

    /**
     * 查询数据库数据
     */
    public static List<ClientModel> queryClientDataToDB(Context context) {
        List<ClientModel> persons = new ArrayList<>();
        DBManager dbManager = new DBManager(context);
        persons = dbManager.query();
        dbManager.closeDB();

        return persons;
    }

    /**
     * 判断指定客户是否存在
     *
     * @param context
     * @param clientId
     * @return
     */
    public static boolean queryClientIsExist(Context context, String clientId) {
        DBManager dbManager = new DBManager(context);
        boolean isExist = dbManager.queryTheClientCursor(clientId);
        dbManager.closeDB();

        return isExist;
    }


    /**
     * 获取省列表
     *
     * @param type 1、省  2、市 3、区
     * @return
     */
    public static List<AreaModel> getAreaData(Context context, String type, String areaName) {
        AreaDBManager dbHelper = new AreaDBManager(context);

        List<AreaModel> data = new ArrayList<>();

        SQLiteDatabase db = dbHelper.openDatabase();

//        Cursor cursor = db.query("area", new String[]{"areaId", "areaType", "areaName", "parentId"}, "areaName = ?", new String[]{type, areaName}, null, null, null);
        String current_sql_sel = "SELECT  * FROM AREA where areaName like '%" + areaName + "%'";
        Cursor cursor = db.rawQuery(current_sql_sel, null);

        while (cursor.moveToNext()) {

            AreaModel model = new AreaModel();
            model.setAreaName(cursor.getString(cursor.getColumnIndex("areaName")));
            model.setAreaId(cursor.getString(cursor.getColumnIndex("areaId")));
            model.setAreaType(cursor.getInt(cursor.getColumnIndex("areaType")));
            model.setParentId(cursor.getString(cursor.getColumnIndex("parentId")));
            data.add(model);
        }
        cursor.close();
        db.close();

        return data;
    }
}
