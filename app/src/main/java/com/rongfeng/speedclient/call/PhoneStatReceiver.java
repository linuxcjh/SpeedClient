package com.rongfeng.speedclient.call;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.voice.VoiceAnalysisTools;
import com.rongfeng.speedclient.voice.model.CsrContactJSONArray;

import java.util.ArrayList;
import java.util.List;

public class PhoneStatReceiver extends BroadcastReceiver {

    private static final String IN_COMMING_FLAG = "incomingFlag";
    private static final String NUMBER = "number";


    private static final String TAG = "PhoneStatReceiver";

    private static boolean incomingFlag = false;

    private static String incoming_number = null;

    private String outPhoneNmber;
    private String phoneNumber;


    @Override
    public void onReceive(Context context, Intent intent) {
        //如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false;
            outPhoneNmber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            AppConfig.setBooleanConfig(IN_COMMING_FLAG, false);
            AppConfig.setStringConfig(NUMBER, intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
            Log.i(TAG, "call OUT:" + phoneNumber);
        } else {
            //如果是来电
            TelephonyManager tm =
                    (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incomingFlag = true;//标识当前是来电
                    phoneNumber = intent.getStringExtra("incoming_number");
                    AppConfig.setBooleanConfig(IN_COMMING_FLAG, true);
                    AppConfig.setStringConfig(NUMBER, intent.getStringExtra("incoming_number"));
                    Log.i(TAG, "RINGING :" + incoming_number);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingFlag) {
                        Log.i(TAG, "incoming ACCEPT :" + incoming_number);
                    }

                    break;
                case TelephonyManager.CALL_STATE_IDLE:
//                    if (AppConfig.getBooleanConfig(IN_COMMING_FLAG, false)) {
//                        judgeIsExist(incoming_number);
//
//                    } else {
//                        judgeIsExist(AppConfig.getStringConfig(NUMBER, ""));
//                    }

//                    judgeIsExist(AppConfig.getStringConfig(NUMBER, ""));

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
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }

        List<ClientModel> clientModels = VoiceAnalysisTools.getInstance().queryClientDataToDB();
        for (int i = 0; i < clientModels.size(); i++) {
            if (clientModels.get(i).getContact_name().contains(phoneNum)) {

                ArrayList<CsrContactJSONArray> contactModels = BasePresenter.gson.fromJson(clientModels.get(i).getContact_name(), new TypeToken<List<CsrContactJSONArray>>() {
                }.getType());

                Intent intent = new Intent(AppConfig.getContext(), AlertActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("clientName", clientModels.get(i).getClient_name());
                intent.putExtra("clientId", clientModels.get(i).getClient_id());


                for (int j = 0; j < contactModels.size(); j++) {
                    if (phoneNum.equals(contactModels.get(j).getPhone())) {
                        intent.putExtra("contactName", contactModels.get(j).getName());
                    }
                }
                AppConfig.getContext().startActivity(intent);
                return;
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
