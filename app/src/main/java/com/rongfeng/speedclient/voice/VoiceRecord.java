package com.rongfeng.speedclient.voice;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.home.IVoiceData;
import com.rongfeng.speedclient.utils.JsonParser;
import com.rongfeng.speedclient.wave.WaveView;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * AUTHOR: Alex
 * DATE: 3/1/2017 09:33
 */

public class VoiceRecord {


    public static final int VOICE_RECORD_START_INDEX = 0;//录音开始
    public static final int VOICE_RECORD_END_INDEX = 1;//录音结束


    public static final int VOICE_RECORD_SEND_RESULT_INDEX = 2;//录音解析结果


    public int timeNum = 0;//录音时长

    public Timer timer = new Timer();

    public TimerTask timerTask;

    // 语音听写对象
    public SpeechRecognizer mIat;

    public MediaPlayer mediaPlayer;//mediaPlayer对象


    public TextView timeSecondTv;

    public Context mContext;

    public WaveView waveView;

    public Handler transHandler;

    public Vibrator vibrator;
    public long[] pattern = {50, 50};   // 停止 开启 停止 开启

    public EditText editText;


    public VoiceRecord(Context context, WaveView waveView, TextView timeSecondTv, Handler handler) {
        this.mContext = context;
        this.timeSecondTv = timeSecondTv;
        this.waveView = waveView;
        this.transHandler = handler;
        initVoice();
    }

    public VoiceRecord(Context context, WaveView waveView, TextView timeSecondTv, Handler handler, EditText editText) {
        this.mContext = context;
        this.timeSecondTv = timeSecondTv;
        this.waveView = waveView;
        this.transHandler = handler;
        this.editText = editText;
        initVoice();
    }

    /**
     * 初始化语音
     */
    public void initVoice() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);

        // 设置参数
        setParam();
        // 不显示听写对话框
        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

    }


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case VOICE_RECORD_START_INDEX:
                    if (timeNum < 10) {
                        timeSecondTv.setText("00:0" + timeNum++);
                    } else if (timeNum < 59) {
                        timeSecondTv.setText("00:" + timeNum++);
                    } else {
//                        timeSecondTv.setText("01:0" + timeNum++ % 60);
                        timeSecondTv.setText("01:00");
                        mHandler.sendEmptyMessage(VOICE_RECORD_END_INDEX);//最长一分钟

                    }

                    break;
                case VOICE_RECORD_END_INDEX:

                    mIat.stopListening();

                    if (timeNum > 1) { //录音时长大于1秒开始解析
//                        mediaPlayer = MediaPlayer.create(mContext, R.raw.qrcode_completed);
//                        mediaPlayer.start();
                        vibrator.vibrate(pattern, 1);

                    }

                    timeNum = 0;
                    if (timerTask != null) {
                        timerTask.cancel();
                    }

                    break;

            }

        }
    };


    /**
     * 初始化监听器。
     */
    public InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
//          AppTools.getToast("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎云端
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        //普通话：mandarin(默认)
        //粤 语：cantonese
        //四川话：lmz
        //河南话：henanese
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理0~10000
        mIat.setParameter(SpeechConstant.VAD_BOS, "10000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音0~10000
        mIat.setParameter(SpeechConstant.VAD_EOS, "10000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }


    IVoiceData iVoiceData;


    public void setIVoiceData(IVoiceData iVoiceData) {
        this.iVoiceData = iVoiceData;
    }

    public void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        iVoiceData.voiceParseData(text);

    }


    /**
     * 听写监听器。
     */
    public RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
//            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//            showTip("结束说话");
            mHandler.sendEmptyMessage(VOICE_RECORD_END_INDEX);//语音结束

        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前正在说话，音量大小：" + volume);
//            Log.d("wave", "当前正在说话，音量大小：" + volume);

            waveView.setAmplitudeValue((int) (volume / 30f * Utils.dip2px(AppConfig.getContext(), 14)));
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };


    /**
     * 开始听写
     */
    public void startPlay() {

        vibrator.vibrate(pattern, 1);
//        mediaPlayer = MediaPlayer.create(mContext, R.raw.talkroom_begin);
//        mediaPlayer.start();
        mIat.startListening(mRecognizerListener);

        timerTask = new TimerTask() {

            @Override
            public void run() {
                mHandler.sendEmptyMessage(VOICE_RECORD_START_INDEX);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 释放资源
     */
    public void stopPlay() {
//        if (mediaPlayer != null) {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//            }
//            mediaPlayer.release();//释放资源
//
//        }
        vibrator.cancel();

    }


}
