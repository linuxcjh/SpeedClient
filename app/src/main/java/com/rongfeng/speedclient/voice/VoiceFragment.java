package com.rongfeng.speedclient.voice;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientPersonaActivity;
import com.rongfeng.speedclient.client.ClientRegisterActivity;
import com.rongfeng.speedclient.client.ClientSearchActivity;
import com.rongfeng.speedclient.client.ClientVisitActivity;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.components.MyDialog;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.mbiwise.materialintro.animation.MaterialIntroListener;
import co.mbiwise.materialintro.shape.Focus;
import co.mbiwise.materialintro.shape.FocusGravity;
import co.mbiwise.materialintro.view.MaterialIntroView;

/**
 * 语音
 * 2016/1/13
 */
public class VoiceFragment extends BaseFragment implements MaterialIntroListener {


    public static final int SEARCH_CLIENT_INDEX = 2; //查找客户
    public static final int PROGRESS_CLIENT_INDEX = 3; //跟进客户

    @Bind(R.id.select_language_tv)
    public TextView selectLanguageTv;
    @Bind(R.id.note_tv)
    public TextView noteTv;
    @Bind(R.id.content_et)
    public EditText contentEt;
    @Bind(R.id.input_cancel_tv)
    public TextView inputCancelTv;
    @Bind(R.id.input_search_client_tv)
    public TextView inputSearchClientTv;
    @Bind(R.id.input_to_log_tv)
    public TextView inputToLogTv;
    @Bind(R.id.input_confirm_tv)
    public TextView inputConfirmTv;
    @Bind(R.id.voice_input_layout)
    public LinearLayout voiceInputLayout;


    private int currentIndex;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_layout, null);
        ButterKnife.bind(this, view);
        initView();
        init();
        return view;
    }


    public void initView() {
        showIntro(inputSearchClientTv, INTRO_CARD1, "根据录入内容快速查找客户~", FocusGravity.CENTER);
    }

    /**
     * 添加日志
     *
     * @param
     */
    public void invoke() {

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


    public void init() {


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

    public void changeStatus(TextView textView, int drawableIndex, int color) {
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
            case R.id.input_search_client_tv:

                connectClient(SEARCH_CLIENT_INDEX);

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
    public void connectClient(int index) {
        AppTools.hideKeyboard(contentEt);
        currentIndex = index;
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

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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
                case Constant.SEARCH_CLIENT_INDEX:
                    startActivityForResult(new Intent(getActivity(), ClientSearchActivity.class)
                            .putExtra("voiceConent", contentEt.getText().toString()), Constant.SEARCH_CLIENT_INDEX);
                    break;
                case Constant.ADD_CLIENT_INDEX:
                    startActivityForResult(new Intent(getActivity(), ClientRegisterActivity.class)
                            .putExtra("voiceConent", contentEt.getText().toString()), Constant.ADD_CLIENT_INDEX);
                    break;
                case Constant.CONFIRMDIALOG:
                    startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
                    break;
            }

        }
    };


    static final String INTRO_CARD1 = "intro_card_1";
    static final String INTRO_CARD2 = "intro_card_2";

    @Override
    public void onUserClicked(String materialIntroViewId) {
        if (materialIntroViewId == INTRO_CARD1) {
            showIntro(inputConfirmTv, INTRO_CARD2, "可以将录入内容关联到客户跟进记录~", FocusGravity.CENTER);
        }
    }

    public void showIntro(View view, String id, String text, FocusGravity focusGravity) {
        new MaterialIntroView.Builder(getActivity())
                .enableDotAnimation(true)
                .setTargetPadding(20)
                .setFocusGravity(focusGravity)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText(text)
                .setTarget(view)
                .setListener(this)
                .setUsageId(id) //THIS SHOULD BE UNIQUE ID
                .show();
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
            AddClientTransModel transModel = (AddClientTransModel) data.getSerializableExtra("model");

            if (currentIndex == SEARCH_CLIENT_INDEX) {//查找客户
                startActivity(new Intent(getActivity(), ClientPersonaActivity.class)
                        .putExtra("customerId", transModel.getCsrId())
                        .putExtra("customerName", transModel.getCustomerName()));
            } else {//客户跟进
                startActivity(new Intent(getActivity(), ClientVisitActivity.class)
                        .putExtra("customerId", transModel.getCsrId())
                        .putExtra("customerName", transModel.getCustomerName())
                        .putExtra("content", contentEt.getText().toString()));

            }

        }
    }

}
