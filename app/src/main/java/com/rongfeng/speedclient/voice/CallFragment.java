package com.rongfeng.speedclient.voice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddVisitRecordModel;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.utils.JsonParser;
import com.rongfeng.speedclient.wave.WaveView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * 语音
 * 2016/1/13
 */
public class CallFragment extends BaseFragment implements View.OnTouchListener {


    public static final int VOICE_RECORD_START_INDEX = 0;//录音开始
    public static final int VOICE_RECORD_END_INDEX = 1;//录音结束


    @Bind(R.id.cancel_bt)
    TextView cancelBt;
    @Bind(R.id.confirm_bt)
    TextView confirmBt;


    @Bind(R.id.top_layout)
    RelativeLayout topLayout;
    @Bind(R.id.time_second_tv)
    TextView timeSecondTv;
    @Bind(R.id.wave_layout)
    LinearLayout waveLayout;
    @Bind(R.id.wave_view)
    WaveView waveView;
    public int timeNum = 0;//录音时长


    public Timer timer = new Timer();

    public TimerTask timerTask;

    // 语音听写对象
    public SpeechRecognizer mIat;
    // 语音听写UI
    public RecognizerDialog mIatDialog;
    public SharedPreferences mSharedPreferences;
    int ret = 0; // 函数调用返回值


    public MediaPlayer mediaPlayer;//mediaPlayer对象
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.voice_bt)
    ImageView voiceBt;
    @Bind(R.id.tip_tv)
    TextView tipTv;

    private AddVisitRecordModel visitRecordModel = new AddVisitRecordModel();

    public static CallFragment newInstance(String clientName, String contactName, String clientId) {

        Bundle args = new Bundle();
        args.putString("clientName", clientName);
        args.putString("contactName", contactName);
        args.putString("clientId", clientId);
        CallFragment fragment = new CallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alert_layout, null);
        ButterKnife.bind(this, view);
        init();
        initVoice();
        return view;
    }

    /**
     * 初始化语音
     */
    public void initVoice() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(getActivity(), mInitListener);
        mIatDialog = new RecognizerDialog(getActivity(), mInitListener);
        mSharedPreferences = getActivity().getSharedPreferences("SPEED",
                Activity.MODE_PRIVATE);

    }


    public void init() {
        tipTv.setText("录入\"" + getArguments().getString("clientName") + " " + getArguments().getString("contactName") + "\"的跟进内容：");
        voiceBt.setOnTouchListener(this);

    }

    private void invoke() {
        visitRecordModel.setCsrId(getArguments().getString("clientId"));
        visitRecordModel.setContent(contentEt.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTFOLLOWUP, visitRecordModel, new TypeToken<BaseDataModel>() {
        });
    }

    @OnClick({R.id.cancel_bt, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                getActivity().finish();
                break;
            case R.id.confirm_bt:

                if (!TextUtils.isEmpty(contentEt.getText().toString())) {
                    invoke();
                } else {
                    AppTools.getToast("请输入内容");
                }

                break;
        }
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
                        timeSecondTv.setText("01:0" + timeNum++ % 60);
                    }
                    break;
                case VOICE_RECORD_END_INDEX:


                    mIat.stopListening();

                    if (timeNum > 1) { //录音时长大于1秒开始解析
                        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.qrcode_completed);
                        mediaPlayer.start();
                    }

                    timeNum = 0;
                    if (waveView.renderThread != null) {
                        waveView.renderThread.setRun(false);
                    }
                    topLayout.setVisibility(View.VISIBLE);
                    waveLayout.setVisibility(View.GONE);
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
    private InitListener mInitListener = new InitListener() {

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
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理0~10000
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "10000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音0~10000
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "10000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }


    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        contentEt.setText(contentEt.getText().toString() + text);
        contentEt.setSelection(contentEt.length());

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.talkroom_begin);
                mediaPlayer.start();
                topLayout.setVisibility(View.INVISIBLE);
                waveLayout.setVisibility(View.VISIBLE);
                // 设置参数
                setParam();
                // 不显示听写对话框
                ret = mIat.startListening(mRecognizerListener);
                if (waveView.renderThread != null) {
                    waveView.renderThread.setRun(true);
                }
                timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(VOICE_RECORD_START_INDEX);
                    }
                };
                timer.schedule(timerTask, 0, 1000);

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                mHandler.sendEmptyMessage(VOICE_RECORD_END_INDEX);

                break;
        }
        return true;
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

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

    @Override
    public void onDestroyView() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();//释放资源
        }
        super.onDestroyView();
        ButterKnife.unbind(this);


    }
}

