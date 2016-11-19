package com.rongfeng.speedclient.voice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rongfeng.speedclient.MainActivity;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 语音
 * 2016/1/13
 */
public class VoiceFragment extends BaseFragment {


    @Bind(R.id.note_tv)
    TextView noteTv;
    @Bind(R.id.voice_bt)
    ImageButton voiceBt;
    @Bind(R.id.h_time_tv)
    TextView hTimeTv;
    @Bind(R.id.voice_status_tv)
    TextView voiceStatusTv;


    private int timeNum = 0;//录音时长

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    hTimeTv.setText("剩余 " + timeNum++ + " s");
                    break;
                case 1:
                    timeNum = 0;
                    break;
            }

        }
    };

    private Timer timer = new Timer();

    private TimerTask timerTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_layout, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {

        voiceBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                mHandler.sendEmptyMessage(0);
                            }
                        };
                        timer.schedule(timerTask, 0, 1000);
                        voiceStatusTv.setText("聆听中……");

                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:

                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        mHandler.sendEmptyMessage(1);

                        if (timeNum > 1) { //录音时长大于1秒开始解析
                            analysisVoice();
                        } else {
                            AppTools.getToast("时间太短");
                            voiceStatusTv.setText("长按语音输入");


                        }

                        break;
                }


                return true;
            }
        });
    }


    /**
     * 语音解析
     */
    private void analysisVoice() {
        voiceStatusTv.setText("正在解析……");


    }

    @OnClick(R.id.note_tv)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_tv:
//                startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
