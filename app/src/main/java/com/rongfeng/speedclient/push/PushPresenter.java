package com.rongfeng.speedclient.push;

import android.widget.Toast;

import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.Map;

/**
 * ${DESCRIPTION}
 * <p/>
 * HP
 * <p/>
 * 2016/1/13
 */
public class PushPresenter extends BasePresenter {


    public void PushPresenter() {
    }

    @Override
    public void onResponse(String methodName, Object object, int status) {
        switch (methodName) {
            case XxbService.UPDATEPWD:
                if (status == 1) {
                    Toast.makeText(AppConfig.getContext(), "成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void pushDinding(String channelId) {

        Map<String, String> map = AppTools.toMap();
        map.put("markedIdentifying", channelId);
        commonApi(XxbService.PUSHDINDING, map);
    }
}
