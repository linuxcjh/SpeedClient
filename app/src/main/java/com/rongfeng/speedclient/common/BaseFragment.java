package com.rongfeng.speedclient.common;

import android.support.v4.app.Fragment;

/**
 * AUTHOR: Alex
 * DATE: 21/10/2015 18:55
 * 【Parent Fragment】
 */
public class BaseFragment extends Fragment {

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


}
