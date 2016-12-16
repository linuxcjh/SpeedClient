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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.rongfeng.speedclient.client.ClientRegisterActivity;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.components.GuideViewDisplayUtil;
import com.rongfeng.speedclient.components.MyDialog;
import com.rongfeng.speedclient.components.SearchPopupWindow;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.utils.JsonParser;
import com.rongfeng.speedclient.voice.model.AreaModel;
import com.rongfeng.speedclient.voice.model.CsrContactJSONArray;
import com.rongfeng.speedclient.voice.model.SplitWordModel;

import java.util.ArrayList;
import java.util.HashSet;
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

    public static final int SELECT_LANGUAGE_INDEX = 0x11;


    @Bind(R.id.select_language_tv)
    TextView selectLanguageTv;
    @Bind(R.id.note_tv)
    TextView noteTv;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.input_cancel_tv)
    TextView inputCancelTv;
    @Bind(R.id.input_to_schedule_tv)
    TextView inputToScheduleTv;
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

    private List<ClientModel> clientModels;
    private GuideViewDisplayUtil mGuideViewUtil;


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

//        voiceBt.setOnTouchListener(this);
        searchPopupWindow = new SearchPopupWindow(getActivity(), Utils.getDeviceHeightPixels(getActivity()), mHandler);
        searchPopupWindow.getPopupWindow();
    }


    /**
     * 语音解析
     */
    private void analysisVoice() {
        voiceStatusTv.setText("说完了");


    }


    @OnClick({R.id.input_to_schedule_tv, R.id.input_to_log_tv, R.id.input_confirm_tv, R.id.note_tv, R.id.input_cancel_tv, R.id.voice_bt, R.id.select_language_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_tv:
                startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
                break;
            case R.id.input_cancel_tv:
                contentEt.setText("");
                break;
            case R.id.input_confirm_tv:

                analysisData();
                AppTools.hideKeyboard(contentEt);
                break;
            case R.id.voice_bt:
                // 设置参数
                setParam();
                boolean isShowDialog = mSharedPreferences.getBoolean(
                        getString(R.string.pref_key_iat_show), true);
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
                } else {
                }
                break;
            case R.id.select_language_tv:
                List<BaseDataModel> data = new ArrayList<>();
                data.add(new BaseDataModel("mandarin", "普通话"));
                data.add(new BaseDataModel("cantonese", "粤 语"));
                data.add(new BaseDataModel("lmz", "四川话"));
                data.add(new BaseDataModel("henanese", "河南话"));
//                /普通话：mandarin(默认)
//                //粤 语：cantonese
//                //四川话：lmz
//                //河南话：henanese
                AppTools.selectDialog("请选语言", getActivity(), data, mHandler, SELECT_LANGUAGE_INDEX);

                break;
            case R.id.input_to_schedule_tv:
                if (TextUtils.isEmpty(contentEt.getText().toString())) {
                    AppTools.getToast("请输入内容");
                    return;
                }
                startActivity(new Intent(getActivity(), AddScheduleActivity.class).putExtra("content", contentEt.getText().toString()));


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
                case 0:
                    hTimeTv.setText(timeNum++ + " s");
                    break;
                case 1:
                    timeNum = 0;
                    break;
                case 2:
                    BaseDataModel m = (BaseDataModel) msg.obj;
                    showPop(m);
                    break;
                case 3:
                    invoke();
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
//        String lag = mSharedPreferences.getString("iat_language_preference",
//                "mandarin");
        String language = AppConfig.getStringConfig("language_select_id", "mandarin");
        if (language.equals("en_us")) {
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
            mIat.setParameter(SpeechConstant.ACCENT, language);
            //目前提供三个垂直领域的听写模型：商旅、视频和音乐
            //商旅:travel
            //视频:video
            //音乐:entrancemusic
//            mIat.setParameter(SpeechConstant.DOMAIN, "travel");


        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理0~10000
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "3000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音0~10000
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "4000"));

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

    }


    /**
     * 解析数据
     */
    private void analysisData() {
        String resultStr = contentEt.getText().toString();
        String pinYinStr = AppTools.convertPinYin(resultStr);
        if (!TextUtils.isEmpty(resultStr)) {

            clientModels = VoiceAnalysisTools.getInstance().queryClientDataToDB();

            List<String> clientData = new ArrayList<>();

            if (clientModels.size() != 0) {
                for (int i = 0; i < clientModels.size(); i++) {

                    ClientModel resultModel = clientModels.get(i);
                    String name = resultModel.getClient_name();
                    String namePY = AppTools.convertPinYin(name);
                    //客户名称分词结果
                    List<SplitWordModel> splitWordModels = BasePresenter.gson.fromJson(resultModel.getClient_info(), new TypeToken<List<SplitWordModel>>() {
                    }.getType());
                    ArrayList<CsrContactJSONArray> contactModels = BasePresenter.gson.fromJson(resultModel.getContact_name(), new TypeToken<List<CsrContactJSONArray>>() {
                    }.getType());
                    if (resultStr.indexOf(name) != -1 || pinYinStr.indexOf(namePY) != -1) {//客户全名匹配
                        clientData.add(resultModel.getClient_id() + "," + name);
                    }

                    if (splitWordModels != null && splitWordModels.size() > 0) {//客户名称分词匹配

                        for (int j = 0; j < splitWordModels.size(); j++) {

                            if (j == 0 && !TextUtils.isEmpty(splitWordModels.get(j).getCont()) && dupArea(splitWordModels.get(j).getCont())) {

                            } else {
                                if (!word.contains(splitWordModels.get(j).getCont()) && resultStr.indexOf(splitWordModels.get(j).getCont()) != -1) {
                                    clientData.add(resultModel.getClient_id() + "," + name);
                                }
                            }

                        }
                    }

                    if (contactModels != null && contactModels.size() > 0) {//联系人匹配

                        for (int k = 0; k < contactModels.size(); k++) {
                            if (resultStr.indexOf(contactModels.get(k).getName()) != -1) {
                                clientData.add(resultModel.getClient_id() + "," + name + "   " + contactModels.get(k).getName());
                            }
                        }
                    }
                }
            }

            List<BaseDataModel> temp = obtainWithoutDup(clientData);
            if (clientData.size() >= 1) {
                AppTools.selectVoiceDialog("选择需要关联的客户：", getActivity(), temp, mHandler, 2);
            } else if (clientData.size() == 0) {
                AppTools.selectVoiceDialog("查找或新建需要关联的客户：", getActivity(), temp, mHandler, 2);
            }

        } else {
            AppTools.getToast("请输入内容");
        }

    }


    //过滤公司名 需要排除的词
    private String word = "公司 股份 证券 有限 责任 咨询 设备 信息 科技 实业 中国 国际";


    /**
     * 判断客户名称第一个分词是不是地名
     *
     * @param content
     * @return
     */
    private boolean dupArea(String content) {
        List<AreaModel> areaModels = AppTools.getAreaData(getActivity(), "", content);
        areaModels.size();

        if (areaModels.size() > 0) {
            return true;
        }
        return false;

    }

    /**
     * 去掉重复
     *
     * @param clientData
     * @param
     * @return
     */
    private List<BaseDataModel> obtainWithoutDup(List<String> clientData) {

        List<String> listWithoutDup = new ArrayList<>(new HashSet<>(clientData));

        List<BaseDataModel> temp = new ArrayList<>();
        for (int i = 0; i < listWithoutDup.size(); i++) {
            temp.add(new BaseDataModel(listWithoutDup.get(i).split(",")[0], listWithoutDup.get(i).split(",")[1]));
        }
        return temp;
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
