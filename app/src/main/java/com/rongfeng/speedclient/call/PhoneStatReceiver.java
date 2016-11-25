package com.rongfeng.speedclient.call;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;

public class PhoneStatReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStatReceiver";

    private static boolean incomingFlag = false;

    private static String incoming_number = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        //如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false;
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + phoneNumber);
//            AppTools.getToast("call OUT:" + phoneNumber);
        } else {
            //如果是来电
            TelephonyManager tm =
                    (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incomingFlag = true;//标识当前是来电
                    incoming_number = intent.getStringExtra("incoming_number");
                    Log.i(TAG, "RINGING :" + incoming_number);
//                    AppTools.getToast("RINGING :" + incoming_number);


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
                    AppTools.getToast("CALL_STATE_IDLE");
//                    setCustomWindow();
                    setAlarActivity();
                    break;
            }
        }
    }

    /**
     * 跳转输入页
     */
    private void setAlarActivity() {

        AppConfig.getContext().startActivity(new Intent(AppConfig.getContext(), AlertActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

//    private void setCustomWindow() {
//        final WindowManager
//                mWM = (WindowManager) AppConfig.getContext().getSystemService(Context.WINDOW_SERVICE);
//
//        final WindowManager.LayoutParams
//                params = new WindowManager.LayoutParams();
//
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.format = PixelFormat.TRANSLUCENT;
//        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
//        params.gravity = Gravity.LEFT + Gravity.TOP;
//        params.x = Utils.getDeviceWidthPixels(AppConfig.getContext()) / 2 - Utils.dip2px(AppConfig.getContext(), 300) / 2;
//        params.y = Utils.getDeviceHeightPixels(AppConfig.getContext()) / 2 - Utils.dip2px(AppConfig.getContext(), 200);
//
//        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//
//        final View contentView = View.inflate(AppConfig.getContext(), R.layout.call_pop_view, null);
//
//        EditText content = (EditText) contentView.findViewById(R.id.content_et);
//        Button cancel = (Button) contentView.findViewById(R.id.cancel_bt);
//        Button confirm = (Button) contentView.findViewById(R.id.confirm_bt);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mWM.removeView(contentView);
//            }
//        });
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//
//        mWM.addView(contentView,
//                params);  //创建View
//
//        InputMethodManager imm = (InputMethodManager) AppConfig.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//    }


}
