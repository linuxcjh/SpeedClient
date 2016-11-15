package com.rongfeng.speedclient.common;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.rongfeng.speedclient.common.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @Override public void onLocationChanged(AMapLocation aMapLocation) {
 * if (aMapLocation != null) {
 * if (aMapLocation.getErrorCode() == 0) {
 * //可在其中解析amapLocation获取相应内容。
 * aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
 * aMapLocation.getLatitude();//获取纬度
 * aMapLocation.getLongitude();//获取经度
 * aMapLocation.getAccuracy();//获取精度信息
 * aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
 * aMapLocation.getCountry();//国家信息
 * aMapLocation.getProvince();//省信息
 * aMapLocation.getCity();//城市信息
 * aMapLocation.getDistrict();//城区信息
 * aMapLocation.getStreet();//街道信息
 * aMapLocation.getStreetNum();//街道门牌号信息
 * aMapLocation.getCityCode();//城市编码
 * aMapLocation.getAdCode();//地区编码
 * aMapLocation.getAoiName();//获取当前定位点的AOI信息
 * <p/>
 * AppTools.getToast("Retrofit " + aMapLocation.getAddress());
 * }
 * else {
 * //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
 * Log.e("AmapError", "location Error, ErrCode:"
 * + aMapLocation.getErrorCode() + ", errInfo:"
 * + aMapLocation.getErrorInfo());
 * }
 * }
 * }
 */
public class AmapLbsLocationManager {


    public volatile static AmapLbsLocationManager instance;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption;

    public List<AMapLocationClient> mapLocationClients;

    private AmapLbsLocationManager() {
        mapLocationClients = new ArrayList<>();

    }

    public static AmapLbsLocationManager getInstance() {
        if (instance == null) {
            synchronized (AmapLbsLocationManager.class) {
                if (instance == null) {
                    instance = new AmapLbsLocationManager();
                }
            }
        }
        return instance;
    }


    /**
     * 初始化
     */
    public void initLbsConfig(AMapLocationListener listener, boolean isOnce) {

        mLocationClient = new AMapLocationClient(AppConfig.getContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        mLocationClient.setLocationListener(listener);

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        if (isOnce) {
            mLocationOption.setOnceLocation(true);

        } else {
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            mLocationOption.setInterval(1000);
        }

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        mapLocationClients.add(mLocationClient);

    }

    /**
     * 设置定位回调监听
     *
     * @param listener
     */
    public void setOnBDLocationListener(AMapLocationListener listener) {

        mLocationClient.setLocationListener(listener);
    }


    /**
     * 开始定位
     */
    public void startLbsLocation(AMapLocationListener listener, boolean isOnce) {
        initLbsConfig(listener, isOnce);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 是否已启动
     *
     * @return
     */
    public boolean isStarted() {
        for (AMapLocationClient c : mapLocationClients) {
            if (c.isStarted()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 停止定位
     */
    public void stopLbsLocation() {

        for (AMapLocationClient c : mapLocationClients) {
            if (c != null) {
                c.stopLocation();
                c.onDestroy();
            }
        }
        mapLocationClients.clear();

    }


}
