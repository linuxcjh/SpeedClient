package com.rongfeng.speedclient;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ContactManager;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.datanalysis.DBManager;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.utils.ApkInstaller;
import com.rongfeng.speedclient.utils.DensityUtil;
import com.rongfeng.speedclient.utils.FlowLayout;
import com.rongfeng.speedclient.utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static String TAG = MainActivity.class.getSimpleName();


    @Bind(R.id.image_iat_set)
    ImageButton imageIatSet;
    @Bind(R.id.iatRadioCloud)
    RadioButton iatRadioCloud;
    @Bind(R.id.iatRadioLocal)
    RadioButton iatRadioLocal;
    @Bind(R.id.iatRadioMix)
    RadioButton iatRadioMix;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.iat_recognize)
    Button iatRecognize;
    @Bind(R.id.iat_stop)
    Button iatStop;
    @Bind(R.id.iat_cancel)
    Button iatCancel;
    @Bind(R.id.iat_recognize_stream)
    Button iatRecognizeStream;
    @Bind(R.id.iat_upload_contacts)
    Button iatUploadContacts;
    @Bind(R.id.iat_upload_userwords)
    Button iatUploadUserwords;
    @Bind(R.id.result_tv)
    TextView resultTv;
    @Bind(R.id.flowLayout_client)
    FlowLayout flowLayoutClient;
    @Bind(R.id.flowLayout_client_layout)
    LinearLayout flowLayoutClientLayout;
    @Bind(R.id.flowLayout_analysis)
    FlowLayout flowLayoutAnalysis;
    @Bind(R.id.fflowLayout_analysis_layout)
    LinearLayout fflowLayoutAnalysisLayout;
    @Bind(R.id.flowLayout_function)
    FlowLayout flowLayoutFunction;
    @Bind(R.id.flowLayout_function_layout)
    LinearLayout flowLayoutFunctionLayout;
    @Bind(R.id.tab_ws_iv)
    ImageView tabWsIv;
    @Bind(R.id.tab_ws_tv)
    TextView tabWsTv;
    @Bind(R.id.tab_ws_layout)
    LinearLayout tabWsLayout;
    @Bind(R.id.tab_client_iv)
    ImageView tabClientIv;
    @Bind(R.id.tab_client_tv)
    TextView tabClientTv;
    @Bind(R.id.tab_client_layout)
    LinearLayout tabClientLayout;
    @Bind(R.id.tab_plus_iv)
    ImageView tabPlusIv;
    @Bind(R.id.tab_plus_layout)
    LinearLayout tabPlusLayout;
    @Bind(R.id.tab_app_iv)
    ImageView tabAppIv;
    @Bind(R.id.tab_app_tv)
    TextView tabAppTv;
    @Bind(R.id.app_layout)
    LinearLayout appLayout;
    @Bind(R.id.tab_app_new_layout)
    RelativeLayout tabAppNewLayout;
    @Bind(R.id.tab_mine_iv)
    ImageView tabMineIv;
    @Bind(R.id.tab_mine_tv)
    TextView tabMineTv;
    @Bind(R.id.tab_mine_layout)
    LinearLayout tabMineLayout;
    @Bind(R.id.commit_tv)
    TextView commitTv;
    @Bind(R.id.hint_tv)
    TextView hintTv;
    @Bind(R.id.flowLayout_other)
    FlowLayout flowLayoutOther;

    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private EditText mResultText;
    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 语记安装助手类
    private ApkInstaller mInstaller;

    private List<ClientModel> persons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initLayout();
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);

        mSharedPreferences = getSharedPreferences("SPEED",
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mResultText = ((EditText) findViewById(R.id.iat_text));
        mInstaller = new ApkInstaller(this);


    }

    /**
     * 初始化数据
     */
    private void initData() {

        long time = System.currentTimeMillis();
        DBManager dbManager = new DBManager(this);
        persons = dbManager.query();

        if (persons.size() == 0) {
            for (int i = 0; i < 5000; i++) {
                persons.add(new ClientModel((i + 1) + "", "董龙" + i, (i + 2) + "", i + "陈建辉", "18710428556"));
            }
            persons.add(new ClientModel((10003) + "", "张治", (10004) + "", "魏萌", "18710428556"));
            persons.add(new ClientModel((10004) + "", "李昊泽", (10005) + "", "常征", "18710428556"));

            persons.add(new ClientModel((10003) + "", "张志", (10004) + "", "陈建辉", "18710428556"));
            persons.add(new ClientModel((10003) + "", "张制", (10004) + "", "董世龙", "18710428556"));
            persons.add(new ClientModel((10003) + "", "三一重工", (10004) + "", "马锐", "18710428556"));

            persons.add(new ClientModel((10003) + "", "中国移动", (10004) + "", "肖秋风", "18710428556"));
            persons.add(new ClientModel((10003) + "", "中国联通", (10004) + "", "董世龙", "18710428556"));
            persons.add(new ClientModel((10003) + "", "荣峰软件科技有限公司", (10004) + "", "陈建辉", "18710428556"));

            dbManager.add(persons);
            Toast.makeText(this, persons.size() + "", Toast.LENGTH_SHORT).show();

        }


        dbManager.closeDB();
//        long result = System.currentTimeMillis() - time;

//        Toast.makeText(this, result + "", Toast.LENGTH_LONG).show();

    }

    /**
     * 初始化Layout。
     */
    private void initLayout() {
//        mEngineType = SpeechConstant.TYPE_CLOUD;
//        findViewById(R.id.iat_upload_contacts).setEnabled(true);
//        findViewById(R.id.iat_upload_userwords).setEnabled(true);
        // 选择云端or本地
//        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
//        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.iatRadioCloud:
//                        mEngineType = SpeechConstant.TYPE_CLOUD;
//                        findViewById(R.id.iat_upload_contacts).setEnabled(true);
//                        findViewById(R.id.iat_upload_userwords).setEnabled(true);
//                        break;
//                    case R.id.iatRadioLocal:
//                        mEngineType = SpeechConstant.TYPE_LOCAL;
//                        findViewById(R.id.iat_upload_contacts).setEnabled(false);
//                        findViewById(R.id.iat_upload_userwords).setEnabled(false);
//                        /**
//                         * 选择本地听写 判断是否安装语记,未安装则跳转到提示安装页面
//                         */
//                        if (!SpeechUtility.getUtility().checkServiceInstalled()) {
//                            mInstaller.install();
//                        } else {
//                            String result = FucUtil.checkLocalResource();
//                            if (!TextUtils.isEmpty(result)) {
//                                showTip(result);
//                            }
//                        }
//                        break;
//                    case R.id.iatRadioMix:
//                        mEngineType = SpeechConstant.TYPE_MIX;
//                        findViewById(R.id.iat_upload_contacts).setEnabled(false);
//                        findViewById(R.id.iat_upload_userwords).setEnabled(false);
//                        /**
//                         * 选择本地听写 判断是否安装语记,未安装则跳转到提示安装页面
//                         */
//                        if (!SpeechUtility.getUtility().checkServiceInstalled()) {
//                            mInstaller.install();
//                        } else {
//                            String result = FucUtil.checkLocalResource();
//                            if (!TextUtils.isEmpty(result)) {
//                                showTip(result);
//                            }
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                showTip("初始化失败，错误码：" + code);
            }
        }
    };


    int ret = 0; // 函数调用返回值d


    @OnClick({R.id.image_iat_set, R.id.commit_tv, R.id.tab_app_new_layout, R.id.iat_recognize, R.id.iat_stop, R.id.iat_cancel, R.id.iat_recognize_stream, R.id.iat_upload_contacts, R.id.iat_upload_userwords, R.id.tab_ws_layout, R.id.tab_client_layout, R.id.tab_plus_layout, R.id.tab_mine_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_iat_set:
                resultTv.setText("");
                break;
            case R.id.tab_plus_layout:
                resetStatus();
            case R.id.iat_recognize:// 开始听写 如何判断一次听写结束：OnResult isLast=true 或者 onError
                mResultText.setText(null);// 清空显示内容
                mIatResults.clear();
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
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
//                        showTip("听写失败,错误码：" + ret);
                    } else {
//                        showTip(getString(R.string.text_begin));
                    }
                }
                break;
            case R.id.iat_stop:
                mIat.stopListening();
                showTip("停止听写");
                break;
            case R.id.iat_cancel:
                mIat.cancel();
                showTip("取消听写");
                break;
            case R.id.iat_recognize_stream:
                showTip("暂无……");

                break;
            case R.id.iat_upload_contacts:
                showTip(getString(R.string.text_upload_contacts));
                ContactManager mgr = ContactManager.createManager(this,
                        mContactListener);
                mgr.asyncQueryAllContactsName();

                break;

            case R.id.iat_upload_userwords:
//                showTip("暂无……");

                startActivity(new Intent(this, WordsActivity.class));
//                analysisData();

                break;
            case R.id.tab_ws_layout:
                Toast.makeText(getApplicationContext(),"动态",Toast.LENGTH_SHORT).show();
                changeStatus(view.getId());

                break;
            case R.id.tab_client_layout:
                Toast.makeText(getApplicationContext(),"客户",Toast.LENGTH_SHORT).show();

                changeStatus(view.getId());

                break;
            case R.id.tab_mine_layout:
                Toast.makeText(getApplicationContext(),"我的",Toast.LENGTH_SHORT).show();

                changeStatus(view.getId());

                break;
            case R.id.tab_app_new_layout:
                Toast.makeText(getApplicationContext(),"管理",Toast.LENGTH_SHORT).show();

                changeStatus(view.getId());
                break;
            case R.id.commit_tv:
                startActivity(new Intent(this, WordsActivity.class));
                break;
        }
    }

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

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
            showTip(error.getPlainDescription(true));
        }
    };

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
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
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

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        mResultText.setText(resultBuffer.toString());
        mResultText.setSelection(mResultText.length());

        analysisData();
    }

    /**
     * 解析数据
     */
    private void analysisData() {
        DBManager dbManager = new DBManager(this);
        List<BaseDataModel> clientData = new ArrayList<>();
        List<BaseDataModel> analysisData = new ArrayList<>();
        List<BaseDataModel> functionData = new ArrayList<>();


        long time = System.currentTimeMillis();
        String resultStr = mResultText.getText().toString();
        String pinYinStr = AppTools.convertPinYin(resultStr);

        mResultText.setText(resultStr);
        mResultText.setSelection(mResultText.length());
        if (!TextUtils.isEmpty(resultStr)) {
            hintTv.setVisibility(View.GONE);
            if (persons.size() != 0) {
                for (int i = 0; i < persons.size(); i++) {

                    String name = persons.get(i).client_name;
                    String namePY = AppTools.convertPinYin(name);


                    if (resultStr.indexOf(name) != -1 || pinYinStr.indexOf(namePY) != -1) {//全名匹配
                        clientData.add(new BaseDataModel(i + "", name));
                    } else if (name.length() > 2 && (resultStr.contains(name.substring(0, 2)) || pinYinStr.contains(AppTools.convertPinYin(name.substring(0, 2))))) {//模糊匹配，开始2个字
                        clientData.add(new BaseDataModel(i + "", name));
                    }

                }
            }

            //添加拜访记录
            if (resultStr.indexOf("拜访") != -1) {
                analysisData.add(new BaseDataModel("", "添加拜访记录"));
            }

            //添加工作日志
            if (resultStr.indexOf("日志") != -1
                    || resultStr.indexOf("今天") != -1
                    || resultStr.indexOf("完成") != -1
                    || resultStr.indexOf("昨天") != -1
                    || resultStr.indexOf("约") != -1) {
                analysisData.add(new BaseDataModel("", "添加工作日志"));

            }

            //添加日程提醒
            if (resultStr.indexOf("明天") != -1
                    || resultStr.indexOf("后天") != -1
                    || resultStr.indexOf("约") != -1
                    || resultStr.indexOf("参加") != -1
                    || resultStr.indexOf("跟进") != -1) {
                analysisData.add(new BaseDataModel("", "添加日程提醒"));

            }

            //功能全局搜索
            if (resultStr.indexOf("签到") != -1
                    ) {
                functionData.add(new BaseDataModel("", "考勤签到"));
                functionData.add(new BaseDataModel("", "外勤签到"));
            }

            //出差
            if (resultStr.indexOf("出差") != -1) {
                functionData.add(new BaseDataModel("", "出差审批"));
                functionData.add(new BaseDataModel("", "费用报销"));
            }

            //添加标签
            addLabels(clientData, analysisData, functionData);

            //关闭数据库
            dbManager.closeDB();

            long result = System.currentTimeMillis() - time;

//            Toast.makeText(this, result + " 毫秒", Toast.LENGTH_LONG).show();
        } else {
            hintTv.setVisibility(View.VISIBLE);
        }


    }


    /**
     * 添加标签
     *
     * @param clientData
     * @param analysisData
     * @param functionData
     */
    private void addLabels(List<BaseDataModel> clientData,
                           List<BaseDataModel> analysisData,
                           List<BaseDataModel> functionData) {


        if (clientData.size() == 0) {
            flowLayoutClientLayout.setVisibility(View.GONE);
        } else {
            flowLayoutClientLayout.setVisibility(View.VISIBLE);
            generationLabels(this, clientData, flowLayoutClient);
        }

        if (analysisData.size() == 0) {
            fflowLayoutAnalysisLayout.setVisibility(View.GONE);
        } else {
            fflowLayoutAnalysisLayout.setVisibility(View.VISIBLE);
            generationLabels(this, analysisData, flowLayoutAnalysis);
        }

        if (functionData.size() == 0) {
            flowLayoutFunctionLayout.setVisibility(View.GONE);
        } else {
            flowLayoutFunctionLayout.setVisibility(View.VISIBLE);
            generationLabels(this, functionData, flowLayoutFunction);

        }

        if (clientData.size() == 0) {
            List<BaseDataModel> data = new ArrayList<>();
            data.add(new BaseDataModel("", "新增客户"));
            data.add(new BaseDataModel("", "查找客户"));
            data.add(new BaseDataModel("", "新增工作日志"));

            flowLayoutOther.setVisibility(View.VISIBLE);
            generationLabels(this, data, flowLayoutOther);

        } else {
            flowLayoutOther.setVisibility(View.GONE);

        }

    }

    /**
     * 获取联系人监听器。
     */
    private ContactManager.ContactListener mContactListener = new ContactManager.ContactListener() {

        @Override
        public void onContactQueryFinish(final String contactInfos, boolean changeFlag) {
            // 注：实际应用中除第一次上传之外，之后应该通过changeFlag判断是否需要上传，否则会造成不必要的流量.
            // 每当联系人发生变化，该接口都将会被回调，可通过ContactManager.destroy()销毁对象，解除回调。
            // if(changeFlag) {
            // 指定引擎类型
            runOnUiThread(new Runnable() {
                public void run() {
                    mResultText.setText(contactInfos);
                }
            });

            mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
            ret = mIat.updateLexicon("contact", contactInfos, mLexiconListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip("上传联系人失败：" + ret);
            }
        }
    };
    /**
     * 上传联系人/词表监听器。
     */
    private LexiconListener mLexiconListener = new LexiconListener() {

        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
                showTip(error.toString());
            } else {
                showTip(getString(R.string.text_upload_success));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
    }

    /**
     * label
     *
     * @param context
     * @param datas
     * @param flowLayout
     */
    public void generationLabels(final Context context, List<BaseDataModel> datas, final FlowLayout flowLayout) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.height = DensityUtil.dip2px(context, 40);
        flowLayout.removeAllViews();

        for (int i = 0; i < datas.size(); i++) {
            final View view = LayoutInflater.from(context).inflate(R.layout.main_lable_edit_view, null);

            final TextView textView = (TextView) view.findViewById(R.id.label_tv);
            textView.setText(datas.get(i).getDictionaryName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplication().getApplicationContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            view.setLayoutParams(lp);
            flowLayout.addView(view);
        }

    }
/**
 *
 */

    /**
     * Change navigation status
     *
     * @param resId
     */
    private void changeStatus(int resId) {

        resetStatus();
        switch (resId) {
            case R.id.tab_ws_layout:
                setStatus(tabWsIv, tabWsTv, R.drawable.tabbar_workbench_select);
                break;
            case R.id.tab_client_layout:
                setStatus(tabClientIv, tabClientTv, R.drawable.tabbar_client_select);
                break;
            case R.id.tab_plus_layout:

                break;
            case R.id.tab_app_new_layout:
                setStatus(tabAppIv, tabAppTv, R.drawable.tabbar_application_select);
                break;
            case R.id.tab_mine_layout:
                setStatus(tabMineIv, tabMineTv, R.drawable.tabbar_me_select);
                break;
        }
    }


    /**
     * Set navigation status
     *
     * @param imageView
     * @param textView
     * @param resId
     */
    private void setStatus(ImageView imageView, TextView textView, int resId) {


        imageView.setImageResource(resId);
        textView.setTextColor(getResources().getColor(R.color.colorBlueLight));

    }

    /**
     * Reset navigation status
     */
    private void resetStatus() {
        tabWsIv.setImageResource(R.drawable.tabbar_workbench);
        tabWsTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        tabClientIv.setImageResource(R.drawable.tabbar_client);
        tabClientTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        tabAppIv.setImageResource(R.drawable.tabbar_application);
        tabAppTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        tabMineIv.setImageResource(R.drawable.tabbar_me);
        tabMineTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
    }

}
