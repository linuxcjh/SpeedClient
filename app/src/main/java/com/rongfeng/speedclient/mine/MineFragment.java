package com.rongfeng.speedclient.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.schedule.ScheduleActivity;
import com.rongfeng.speedclient.voice.VoiceAnalysisTools;
import com.rongfeng.speedclient.voice.VoiceNoteActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * 2016/1/13
 */
public class MineFragment extends BaseFragment {


    @Bind(R.id.contact_avatar_iv)
    ImageView contactAvatarIv;
    @Bind(R.id.re_layout)
    RelativeLayout reLayout;
    @Bind(R.id.contact_gender_iv)
    ImageView contactGenderIv;
    @Bind(R.id.sign_layout)
    RelativeLayout signLayout;
    @Bind(R.id.mine_first_name)
    TextView mineFirstName;
    @Bind(R.id.note_layout)
    RelativeLayout noteLayout;
    @Bind(R.id.connect_layout)
    RelativeLayout connectLayout;
    @Bind(R.id.remind_layout)
    RelativeLayout remindLayout;
    @Bind(R.id.set_tv)
    ImageView setTv;
    @Bind(R.id.position_layout)
    RelativeLayout positionLayout;
    @Bind(R.id.performance_layout)
    RelativeLayout performanceLayout;
    @Bind(R.id.target_layout)
    RelativeLayout targetLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        AppTools.setImageViewPicture(getActivity(), AppTools.getUser().getUserImageUrl(), contactAvatarIv);
        mineFirstName.setText(AppTools.getUser().getUserName());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.note_layout, R.id.connect_layout, R.id.remind_layout, R.id.set_tv, R.id.position_layout, R.id.performance_layout, R.id.target_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_layout:
                startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
                break;
            case R.id.connect_layout:
                startActivity(new Intent(getActivity(), MineInviteActivity.class));

                break;
            case R.id.remind_layout:
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
                break;

            case R.id.set_tv:
                startActivityForResult(new Intent(getActivity(), SetActivity.class), 0x11);
                break;
            case R.id.position_layout:
                startActivity(new Intent(getActivity(), MinePositionRecordActivity.class));

                break;
            case R.id.performance_layout:
//                final ProgressDialog dialog = new ProgressDialog(getActivity());
//                dialog.show();
//                WebView webView = new WebView(getActivity());
//                webView.getSettings().setJavaScriptEnabled(true);
//                webView.loadUrl("http://q.url.cn/s/IIpYHXm");
//                webView.setWebViewClient(new WebViewClient() { //mqqwpa
//                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        if (url.startsWith("http:") || url.startsWith("https:")) {
//                            return false;
//                        }
//
////                        if (url.startsWith("mqqwpa")) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        startActivity(intent);
//                        dialog.dismiss();
////                        } else {
////                            return false;
////                        }
//
//                        return true;
//                    }
//                });


                startActivity(new Intent(getActivity(), RebackActivity.class));
                break;
            case R.id.target_layout:

                startActivity(new Intent(getActivity(), MineSalesTargetActivity.class));

                break;
        }

    }

    public void sendSMS(String phoneNumber, String message) {
        //获取短信管理器
        SmsManager smsManager = SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            AppConfig.setStringConfig("userModel", "");
            AppConfig.setStringConfig("login", "0"); //启动页面
            VoiceAnalysisTools.getInstance().clearForm();
            getActivity().finish();
            Process.killProcess(Process.myPid());
            System.exit(0);
        }

    }
}
