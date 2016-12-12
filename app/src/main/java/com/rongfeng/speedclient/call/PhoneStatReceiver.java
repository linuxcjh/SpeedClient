package com.rongfeng.speedclient.call;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.datanalysis.ClientModel;

import java.util.List;

public class PhoneStatReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStatReceiver";

    private static boolean incomingFlag = false;

    private static String incoming_number = null;

    private String phoneNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        //如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false;
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + phoneNumber);
        } else {
            //如果是来电
            TelephonyManager tm =
                    (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incomingFlag = true;//标识当前是来电
                    phoneNumber = intent.getStringExtra("incoming_number");
                    Log.i(TAG, "RINGING :" + incoming_number);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingFlag) {
                        Log.i(TAG, "incoming ACCEPT :" + incoming_number);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (incomingFlag) {
                        Log.i(TAG, "incoming IDLE");
                    }
//                    judgeIsExist(phoneNumber);
                    setAlarActivity();

                    break;
            }
        }
    }

    /**
     * 判断联系人是否存在
     *
     * @param phoneNum
     */
    private void judgeIsExist(String phoneNum) {

        List<ClientModel> clientModels = AppTools.queryClientDataToDB(AppConfig.getContext());
        for (int i = 0; i < clientModels.size(); i++) {
            if (clientModels.get(i).getContact_phone().equals(phoneNum)) {
                setAlarActivity();
            }
        }


    }

    /**
     * 跳转输入页
     */
    private void setAlarActivity() {

        AppConfig.getContext().startActivity(new Intent(AppConfig.getContext(), AlertActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }


}
