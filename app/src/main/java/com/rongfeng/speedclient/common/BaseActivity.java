package com.rongfeng.speedclient.common;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.login.TransDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 21/10/2015 18:55
 */
public class BaseActivity extends FragmentActivity implements ICommonAction {

    public static List<Activity> activityList = new ArrayList<>();
    public SystemBarTintManager tintManager;

    public boolean isActive = true;//是否处于前台运行
    public CommonPresenter commonPresenter = new CommonPresenter(this);
    public TransDataModel transDataModel = new TransDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityList.add(0, this);
        initWindow();
    }


    //    @TargetApi(19)
    public void initWindow() {
        //设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintColor(ContextCompat.getColor(this, R.color.colorBlue));
        tintManager.setStatusBarTintEnabled(true);
//        }
    }

    /**
     * app 字体不受系统字体大小影响
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (AppTools.isAppOnForeground(BaseActivity.this)) {
            isActive = true;
        } else {
            isActive = false;
            AppTools.stopLbsLocation();//位于后台时停止定位
        }
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
    }


}
