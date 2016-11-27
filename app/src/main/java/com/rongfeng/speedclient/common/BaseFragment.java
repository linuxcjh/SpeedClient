package com.rongfeng.speedclient.common;

import android.support.v4.app.Fragment;

import com.rongfeng.speedclient.login.TransDataModel;

/**
 * AUTHOR: Alex
 * DATE: 21/10/2015 18:55
 * 【Parent Fragment】
 */
public class BaseFragment extends Fragment implements ICommonAction{
    public CommonPresenter commonPresenter = new CommonPresenter(this);
    public TransDataModel transDataModel = new TransDataModel();
    @Override
    public void onPause() {
        super.onPause();
        // 配对页面埋点，与start的页面名称要一致
//        AppTools.baiDuOnPageStart(getActivity(),getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        // 页面埋点
//        AppTools.baiDuOnPageEnd(getActivity(),getClass().getSimpleName());
    }
    @Override
    public void obtainData(Object data, String methodIndex, int status) {
    }


}
