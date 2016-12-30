package com.rongfeng.speedclient.voice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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
import com.rongfeng.speedclient.client.ClientPersonaActivity;
import com.rongfeng.speedclient.client.ClientRegisterActivity;
import com.rongfeng.speedclient.client.ClientVisitActivity;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.components.GuideViewDisplayUtil;
import com.rongfeng.speedclient.components.MyDialog;
import com.rongfeng.speedclient.components.SearchPopupWindow;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.utils.JsonParser;
import com.rongfeng.speedclient.wave.WaveView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;
import static com.rongfeng.speedclient.common.Constant.ADD_CLIENT_INDEX;

/**
 * 语音
 * 2016/1/13
 */
public class VoiceFragment extends BaseFragment implements View.OnTouchListener {

    public static final int SELECT_LANGUAGE_INDEX = 0x11;//选择语言

    public static final int VOICE_RECORD_START_INDEX = 0;//录音开始
    public static final int VOICE_RECORD_END_INDEX = 1;//录音结束

    public static final int SEARCH_CLIENT_INDEX = 2; //查找客户

    public static final int PROGRESS_CLIENT_INDEX = 3; //跟进客户


    @Bind(R.id.top_layout)
    RelativeLayout topLayout;
    @Bind(R.id.time_second_tv)
    TextView timeSecondTv;
    @Bind(R.id.wave_layout)
    LinearLayout waveLayout;
    @Bind(R.id.wave_view)
    WaveView waveView;

    @Bind(R.id.select_language_tv)
    TextView selectLanguageTv;
    @Bind(R.id.note_tv)
    TextView noteTv;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.input_cancel_tv)
    TextView inputCancelTv;
    @Bind(R.id.input_search_client_tv)
    TextView inputSearchClientTv;
    @Bind(R.id.input_to_log_tv)
    TextView inputToLogTv;
    @Bind(R.id.input_confirm_tv)
    TextView inputConfirmTv;
    @Bind(R.id.voice_input_layout)
    LinearLayout voiceInputLayout;
    @Bind(R.id.h_time_tv)
    TextView hTimeTv;
    @Bind(R.id.voice_bt)
    ImageView voiceBt;
    @Bind(R.id.voice_status_tv)
    TextView voiceStatusTv;
    @Bind(R.id.root_layout)
    LinearLayout rootLayout;

    private SearchPopupWindow searchPopupWindow;
    private int timeNum = 0;//录音时长


    private Timer timer = new Timer();

    private TimerTask timerTask;

    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    private SharedPreferences mSharedPreferences;
    int ret = 0; // 函数调用返回值

    private GuideViewDisplayUtil mGuideViewUtil;

    private MediaPlayer mediaPlayer;//mediaPlayer对象


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_layout, null);
        ButterKnife.bind(this, view);
        initView();
        init();
        initVoice();
        return view;
    }


    private void initView() {
    }

    /**
     * 添加日志
     *
     * @param
     */
    private void invoke() {

        transDataModel.setNoteContent(contentEt.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTNOTE, transDataModel,
                new TypeToken<BaseDataModel>() {
                });

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTNOTE:
                if (status == 1) {
                    MyDialog dialog = new MyDialog(getActivity(), mHandler);
                    dialog.buildDialog().setTitle("保存成功").setCancelText("留在当前页").setConfirm("查看日志").setMessage("是否跳转到日志页?");

                }
                break;
        }
    }

    /**
     * 初始化语音
     */
    private void initVoice() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(getActivity(), mInitListener);
        mIatDialog = new RecognizerDialog(getActivity(), mInitListener);
        mSharedPreferences = getActivity().getSharedPreferences("SPEED",
                Activity.MODE_PRIVATE);

    }


    private void init() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.tips_view_layout, null);

        mGuideViewUtil = new GuideViewDisplayUtil(getActivity(), view);
        selectLanguageTv.setText(AppConfig.getStringConfig("language_select_name", "普通话"));

        voiceBt.setOnTouchListener(this);
        searchPopupWindow = new SearchPopupWindow(getActivity(), Utils.getDeviceHeightPixels(getActivity()), mHandler);
        searchPopupWindow.getPopupWindow();

        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {

                    changeStatus(inputToLogTv, R.drawable.voice_worklog, R.color.colorBlue);
                    changeStatus(inputSearchClientTv, R.drawable.voice_find, R.color.colorBlue);
                    changeStatus(inputConfirmTv, R.drawable.voice_customer, R.color.colorBlue);

                } else {
                    changeStatus(inputToLogTv, R.drawable.voice_worklog_grey, R.color.colorAssist);
                    changeStatus(inputSearchClientTv, R.drawable.voice_find_grey, R.color.colorAssist);
                    changeStatus(inputConfirmTv, R.drawable.voice_customer_grey, R.color.colorAssist);
                }

            }
        });
    }

    private void changeStatus(TextView textView, int drawableIndex, int color) {
        Drawable drawable = getActivity().getResources().getDrawable(drawableIndex);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
        textView.setTextColor(ContextCompat.getColor(getActivity(), color));

    }

    @OnClick({R.id.input_search_client_tv, R.id.input_to_log_tv, R.id.input_confirm_tv, R.id.note_tv, R.id.input_cancel_tv, R.id.select_language_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_tv:

                startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
                break;
            case R.id.input_cancel_tv:
                contentEt.setText("");

                break;
            case R.id.input_confirm_tv:

                connectClient(PROGRESS_CLIENT_INDEX);

                break;
            case R.id.select_language_tv:
                List<BaseDataModel> data = new ArrayList<>();
                data.add(new BaseDataModel("mandarin", "普通话"));
                data.add(new BaseDataModel("cantonese", "粤 语"));
                data.add(new BaseDataModel("lmz", "四川话"));
                data.add(new BaseDataModel("henanese", "河南话"));
//                //普通话：mandarin(默认)
//                //粤 语：cantonese
//                //四川话：lmz
//                //河南话：henanese
                AppTools.selectDialog("请选语言", getActivity(), data, mHandler, SELECT_LANGUAGE_INDEX);

                break;
            case R.id.input_search_client_tv:

                connectClient(SEARCH_CLIENT_INDEX);

//                startActivity(new Intent(getActivity(), AddScheduleActivity.class).putExtra("content", contentEt.getText().toString()));

                break;
            case R.id.input_to_log_tv:

                if (TextUtils.isEmpty(contentEt.getText().toString())) {
                    AppTools.getToast("请输入内容");
                    return;
                }
                invoke();
                break;
        }
    }


    /**
     * 查找、跟进客户
     */
    private void connectClient(int index) {
        AppTools.hideKeyboard(contentEt);

        if (!TextUtils.isEmpty(contentEt.getText().toString())) {

            List<BaseDataModel> temp = VoiceAnalysisTools.getInstance().analysisData(contentEt);

            if (temp.size() >= 1) {
                AppTools.selectVoiceDialog("选择需要关联的客户：", getActivity(), temp, mHandler, index);
            } else if (temp.size() == 0) {
                AppTools.selectVoiceDialog("查找或新建需要关联的客户：", getActivity(), temp, mHandler, index);
            }
        } else {
            AppTools.getToast("请输入内容");
        }

    }

    /**
     * 显示搜索框
     */
    public void showPop(BaseDataModel model) {
        if (!searchPopupWindow.mPopupWindow.isShowing()) {
            searchPopupWindow.mPopupWindow.showAtLocation(rootLayout, Gravity.TOP, 0, 0);
            searchPopupWindow.setContent(contentEt.getText().toString());
            searchPopupWindow.setSelectClient(model);
        }
    }


    private Handler mHandler = new Handler() {
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
                    voiceStatusTv.setText("长按语音输入");

                    break;
                case SEARCH_CLIENT_INDEX:
                    BaseDataModel m = (BaseDataModel) msg.obj;
//                    showPop(m);
                    startActivity(new Intent(getActivity(), ClientPersonaActivity.class)
                            .putExtra("customerId", m.getDictionaryId())
                            .putExtra("customerName", m.getDictionaryName()));
                    break;
                case PROGRESS_CLIENT_INDEX:
                    BaseDataModel proIndex = (BaseDataModel) msg.obj;
                    startActivity(new Intent(getActivity(), ClientVisitActivity.class)
                            .putExtra("customerId", proIndex.getDictionaryId())
                            .putExtra("customerName", proIndex.getDictionaryName())
                            .putExtra("content", contentEt.getText().toString()));

                    break;
                case SELECT_LANGUAGE_INDEX:
                    BaseDataModel mm = (BaseDataModel) msg.obj;
                    selectLanguageTv.setText(mm.getDictionaryName());
                    AppConfig.setStringConfig("language_select_name", mm.getDictionaryName());
                    AppConfig.setStringConfig("language_select_id", mm.getDictionaryId());
                    setParam();
                    AppTools.getToast(mm.getDictionaryName());
                    break;
                case Constant.SEARCH_CLIENT_INDEX:

                    break;
                case Constant.ADD_CLIENT_INDEX:
                    startActivityForResult(new Intent(getActivity(), ClientRegisterActivity.class), ADD_CLIENT_INDEX);
                    break;
                case Constant.CONFIRMDIALOG:

                    startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
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
//        String lag = mSharedPreferences.getString("iat_language_preference",
//                "mandarin");
        String language = AppConfig.getStringConfig("language_select_id", "mandarin");
//        if (language.equals("en_us")) {
//            // 设置语言 // 简体中文:"zh_cn", 美式英文:"en_us"
//            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
//        } else {
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        //普通话：mandarin(默认)
        //粤 语：cantonese
        //四川话：lmz
        //河南话：henanese
        mIat.setParameter(SpeechConstant.ACCENT, language);
        //目前提供三个垂直领域的听写模型：商旅、视频和音乐
        //商旅:travel
        //视频:video
        //音乐:entrancemusic
//            mIat.setParameter(SpeechConstant.DOMAIN, "travel");
//        }

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
                voiceStatusTv.setText("聆听中……");

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
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();//释放资源
        super.onDestroyView();
        ButterKnife.unbind(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case Constant.ADD_CLIENT_INDEX:
                    AddClientTransModel transModel = (AddClientTransModel) data.getSerializableExtra("model");
                    showPop(new BaseDataModel(transModel.getCsrId(), transModel.getCustomerName()));//TODO 新增接口需返回客户ID
                    break;
            }
        }
    }
}
