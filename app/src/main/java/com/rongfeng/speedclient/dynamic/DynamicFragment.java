package com.rongfeng.speedclient.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;

import butterknife.ButterKnife;

/**
 * 动态
 * 2016/1/13
 */
public class DynamicFragment extends BaseFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_layout, null);
        ButterKnife.bind(this, view);
//        mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 1000);


        return view;
    }

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    viewLine.setFactor(50);
//                    mHandler.sendMessageDelayed(mHandler.obtainMessage(1), 1000);
//
//                    break;
//                case 1:
//                    viewLine.setFactor(50);
//                    mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 1000);
//                    break;
//            }
//
//        }
//    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mHandler.removeMessages(0);
//        mHandler.removeMessages(1);
        ButterKnife.unbind(this);
    }


}
