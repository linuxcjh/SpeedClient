package com.rongfeng.speedclient.common.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * com.mingzhi.samattendance.framework.Utils
 *
 * @author 陈建辉 <br/>
 *         create at 2013-6-18 上午10:59:36
 * @note: 框架工具类
 */
public class Utils {

    public static String getFunctionUrl(int functionId) {
        switch (functionId) {
            case 0x00:
                return "";

            default:
                return "";
        }
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int toPx(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);
        return px;
    }

    /**
     * 通过资源ID获得Drawable对象
     *
     * @param context
     * @param id
     * @return
     */
    public static Drawable getResourceImage(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    /**
     * dip值转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);

    }

    /**
     * px值转dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得设备屏幕宽度 单位像素
     *
     * @param context
     * @return
     */
    public static int getDeviceWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得DecorView高度 单位像素
     *
     * @param context
     * @return
     */
    public static int getDecorViewHeightPixels(Context context) {
        Rect outRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        int h = Utils.getDeviceHeightPixels(context) - outRect.top;
        return h;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获得设备屏幕高度 单位像素
     *
     * @param context
     * @return
     */
    public static int getDeviceHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到一个进度提示对话框
     *
     * @param context
     * @param msgStr
     * @return
     */
    public static ProgressDialog getProgressDialog(Context context,
                                                   String msgStr) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
        progressDialog.setMessage(msgStr);// 进度条显示内容
        progressDialog.setIndeterminate(true);// 设置进度条是否为不明?
        progressDialog.setCancelable(true);// 设置进度条是否可以按取消键取消
        return progressDialog;
    }


    /**
     * 获得一个消息提示框
     *
     * @param context
     * @param msg
     * @param listener
     * @return
     */
    public static Builder getAlertDialog(Context context, String msg,
                                         DialogInterface.OnClickListener listener) {
        Builder builder = new Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(msg);// 设置标题

        builder.setPositiveButton("确定", listener);
        builder.setNegativeButton("取消", listener);
        return builder;
    }

    public static Builder getAlertDialog(Context context, int iconid, String title, String msg, DialogInterface.OnClickListener listener) {
        Builder builder = new Builder(context);
        builder.setIcon(iconid);
        builder.setTitle(title);// 设置标题
        builder.setMessage(msg);
        builder.setPositiveButton("确定", listener);
        builder.setNegativeButton("取消", listener);
        return builder;
    }


    /**
     * 获得一个消息提示框
     *
     * @param context
     * @param resId    提示内容以资源形式传入
     * @param listener
     * @return
     */
    public static Builder getAlertDialog(Context context, int resId,
                                         DialogInterface.OnClickListener listener) {
        String msg = context.getResources().getString(resId);
        return getAlertDialog(context, msg, listener);
    }

    public static Builder getAlertDialog(Context context, int resId) {
        String msg = context.getResources().getString(resId);
        return getAlertDialog(context, msg,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing to do
                    }
                });
    }

    public static Builder getAlertDialog(Context context, String msg) {
        return getAlertDialog(context, msg,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing to do
                    }
                });
    }

    /**
     * @param dateString <p>
     *                   日期字符串转化成日期类型
     *                   </p>
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param date <p>
     *             日期转化成字符串
     *             </p>
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        return dateString;

    }

    /**
     * @param currentDate 当前日期
     * @return <p>
     * 当前日期前一天时期字符串
     * </p>
     */
    public static String getPreviousDayString(String currentDate) {
        if (TextUtils.isEmpty(currentDate)) {
            return null;
        }
        return dateToString(getPreviousDay(stringToDate(currentDate)));

    }

    /**
     * @param currentDate 当前日期
     * @return <p>
     * 当前日期后一天时期字符串
     * </p>
     */
    public static String getNextDayString(String currentDate) {
        if (TextUtils.isEmpty(currentDate)) {
            return null;
        }
        return dateToString(getNextDay(stringToDate(currentDate)));

    }

    /**
     * 前一天
     *
     * @param date
     * @return
     */
    public static Date getPreviousDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 后一天
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 判断字符长包含数字 字母 其他字符 数
     *
     * @param str
     * @return
     */
    public static int countSum(String str) {
        int abccount = 0;
        int numcount = 0;
        int unicodeCount = 0;
        char[] b = str.toCharArray();
        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 'a' && b[i] <= 'z' || b[i] >= 'A' && b[i] <= 'Z') {
                abccount++;
            } else if (b[i] >= '0' && b[i] <= '9') {
                numcount++;
            } else if (b[i] == ' ' || b[i] == '·') {
                unicodeCount++;
            }
        }
        int sum = abccount + numcount + unicodeCount;
        return sum;
    }

    /**
     * 单行文字根据字数 动态设置字体大小
     *
     * @param context
     * @param textView
     * @param maxTextWidth
     * @return
     */
    public static void setCalculateFontSize(Context context, TextView textView, int maxTextWidth) {
        float sp = 0;
        int W = Utils.getDeviceWidthPixels(context);
        TextPaint mTextPaint = textView.getPaint();
        String str = textView.getText().toString();
        float textWidth = mTextPaint.measureText(str);
        if (textWidth > maxTextWidth) {
            int length = str.length();
            int cs = Utils.countSum(str);
            if (cs % 2 != 0) cs++;
            length = length - cs + cs / 2;
            float wordWidth = (float) maxTextWidth / (float) length;
            sp = Utils.px2sp(context, wordWidth);
        } else sp = Utils.px2sp(context, mTextPaint.getTextSize());
        textView.setTextSize(sp);
    }


    /**
     * 判断网络连接是否连通
     *
     * @return
     */
    public static boolean netWorkJuder(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo == null || !networkinfo.isAvailable()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断字符是否为中文
     * <p/>
     * Í
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * 设置小数位数控制
     *
     * @param digits 小数位数
     * @param length 字符长限制
     */
    public static InputFilter getInputFilter(final int length, final int digits) {
        InputFilter lengthfilter = new InputFilter() {

            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                if (dValue.length() == length) {
                    Toast.makeText(AppConfig.getContext().getApplicationContext(), "最多只能输入" + length + "个字符", Toast.LENGTH_SHORT).show();
                    return "";
                } else if (dValue.length() + source.length() > length) {
                    Toast.makeText(AppConfig.getContext().getApplicationContext(), "最多只能输入" + length + "个字符", Toast.LENGTH_SHORT).show();
                    return source.subSequence(0, length - dValue.length());
                }

                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length();
                    if (diff == digits) {
                        if (dstart <= splitArray[0].length()) return null;
                        Toast.makeText(AppConfig.getContext().getApplicationContext(), "最多只能输入" + digits + "位小数", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };
        return lengthfilter;
    }

    static public int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

    static public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    static public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

}
