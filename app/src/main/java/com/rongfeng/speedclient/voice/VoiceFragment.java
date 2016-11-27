package com.rongfeng.speedclient.voice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.components.SearchPopupWindow;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.datanalysis.DBManager;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.utils.JsonParser;

import java.util.ArrayList;
import java.util.List;
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
public class VoiceFragment extends BaseFragment implements View.OnTouchListener {


    @Bind(R.id.note_tv)
    TextView noteTv;
    @Bind(R.id.voice_bt)
    ImageButton voiceBt;
    @Bind(R.id.h_time_tv)
    TextView hTimeTv;
    @Bind(R.id.voice_status_tv)
    TextView voiceStatusTv;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.root_layout)
    LinearLayout rootLayout;
    @Bind(R.id.voice_hint_layout)
    LinearLayout voiceHintLayout;
    @Bind(R.id.click_input_tv)
    TextView clickInputTv;
    @Bind(R.id.input_count_tv)
    TextView inputCountTv;
    @Bind(R.id.input_cancel_tv)
    TextView inputCancelTv;
    @Bind(R.id.input_confirm_tv)
    TextView inputConfirmTv;
    @Bind(R.id.voice_input_layout)
    RelativeLayout voiceInputLayout;

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

    private List<ClientModel> clientModels;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_layout, null);
        ButterKnife.bind(this, view);
        init();
        initVoice();
        return view;
    }

    /**
     * 获取类型
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

                    AppTools.getToast("Success");
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
        voiceBt.setOnTouchListener(this);
        searchPopupWindow = new SearchPopupWindow(getActivity(), Utils.getDeviceHeightPixels(getActivity()), mHandler);
        searchPopupWindow.getPopupWindow();
    }


    /**
     * 语音解析
     */
    private void analysisVoice() {
        voiceStatusTv.setText("正在解析……");


    }

    @OnClick({R.id.note_tv, R.id.click_input_tv, R.id.input_cancel_tv, R.id.input_confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_tv:
                startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
//                AppTools.selectDialog("选择客户", this, clientAddPresenter.generationClientOrigin(), mHandler, clientAddPresenter.SELECT_TYPE_CLIENT_ORIGIN);
//                showPop();
//                startActivity(new Intent(getActivity(), ScheduleActivity.class));
                break;
            case R.id.click_input_tv:
                setEditLayoutStatus(true);
//                AppTools.dialogShow(getActivity());

                break;
            case R.id.input_cancel_tv:
                contentEt.setText("");
                break;
            case R.id.input_confirm_tv:

                analysisData();
                AppTools.hideKeyboard(contentEt);
                break;
        }
    }


    /**
     * 显示搜索框
     */
    public void showPop() {
        if (!searchPopupWindow.mPopupWindow.isShowing()) {
            searchPopupWindow.mPopupWindow.showAtLocation(rootLayout, Gravity.TOP, 0, 0);
            searchPopupWindow.setContent(contentEt.getText().toString());
        }
    }


    private Handler mHandler = new Handler() {
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


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                AppTools.getToast("初始化失败，错误码：" + code);
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

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言 // 简体中文:"zh_cn", 美式英文:"en_us"
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            //普通话：mandarin(默认)
            //粤 语：cantonese
            //四川话：lmz
            //河南话：henanese
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

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

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            AppTools.getToast(error.getPlainDescription(true));
        }
    };


    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        contentEt.setText(contentEt.getText().toString() + text);
        contentEt.setSelection(contentEt.length());
        setEditLayoutStatus(false);

//        analysisData();
    }

    /**
     * 解析数据
     */
    private void analysisData() {
        String resultStr = contentEt.getText().toString();
        String pinYinStr = AppTools.convertPinYin(resultStr);
        if (!TextUtils.isEmpty(resultStr)) {
            invoke();
            setEditLayoutStatus(false);

            DBManager dbManager = new DBManager(getActivity());
            clientModels = dbManager.query();

            List<BaseDataModel> clientData = new ArrayList<>();

            if (clientModels.size() != 0) {
                for (int i = 0; i < clientModels.size(); i++) {

                    String name = clientModels.get(i).client_name;
                    String namePY = AppTools.convertPinYin(name);

                    if (resultStr.indexOf(name) != -1 || pinYinStr.indexOf(namePY) != -1) {//全名匹配
                        clientData.add(new BaseDataModel(i + "", name));
                    } else if (name.length() > 2 && (resultStr.contains(name.substring(0, 2)) || pinYinStr.contains(AppTools.convertPinYin(name.substring(0, 2))))) {//模糊匹配，开始2个字
                        clientData.add(new BaseDataModel(i + "", name));
                    }

                }
            }


            //关闭数据库
            dbManager.closeDB();

            showPop();

        } else {
            AppTools.getToast("请输入内容");
        }


    }


    /**
     * 设置编辑layout
     */
    private void setEditLayoutStatus(boolean isShowKeyBoard) {
        contentEt.setVisibility(View.VISIBLE);
        contentEt.setFocusable(true);
        contentEt.setFocusableInTouchMode(true);
        contentEt.requestFocus();//获取焦点 光标出现
        if (isShowKeyBoard) {
            AppTools.openKeyboard(getActivity(), mHandler, 200);
        }
        voiceInputLayout.setVisibility(View.VISIBLE);
        voiceHintLayout.setVisibility(View.GONE);
        clickInputTv.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

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
                // 设置参数
                setParam();
                boolean isShowDialog = mSharedPreferences.getBoolean(
                        getString(R.string.pref_key_iat_show), true);
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
//                    showTip(getString(R.string.text_begin));
                } else {
                    // 不显示听写对话框
//                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
//                        showTip("听写失败,错误码：" + ret);
                    } else {
//                        showTip(getString(R.string.text_begin));
                    }
                }
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
//                            AppTools.getToast("时间太短");
                    voiceStatusTv.setText("长按语音输入");

                }


                break;
        }


        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
