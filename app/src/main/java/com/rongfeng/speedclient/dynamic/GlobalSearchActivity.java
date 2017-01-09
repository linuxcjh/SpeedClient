package com.rongfeng.speedclient.dynamic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientAddContactDetailsActivity;
import com.rongfeng.speedclient.client.ClientDetailsContractActivity;
import com.rongfeng.speedclient.client.ClientDetaisBusinessActivity;
import com.rongfeng.speedclient.client.ClientPersonaActivity;
import com.rongfeng.speedclient.client.ClientRecordsActivity;
import com.rongfeng.speedclient.client.entry.AddBusinessTransModel;
import com.rongfeng.speedclient.client.entry.AddContractTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.dynamic.model.GlobalSearchItemModel;
import com.rongfeng.speedclient.dynamic.model.GlobalSearchModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 全局搜索
 * <p/>
 * Alex
 */
public class GlobalSearchActivity extends BaseActivity {


    @Bind(R.id.left_icon)
    ImageView leftIcon;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.clear_bt)
    ImageView clearBt;
    @Bind(R.id.cancel_bt)
    Button cancelBt;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.client_layout)
    LinearLayout clientLayout;
    @Bind(R.id.contact_layout)
    LinearLayout contactLayout;
    @Bind(R.id.business_layout)
    LinearLayout businessLayout;
    @Bind(R.id.bargain_layout)
    LinearLayout bargainLayout;
    @Bind(R.id.note_layout)
    LinearLayout noteLayout;
    @Bind(R.id.follow_layout)
    LinearLayout followLayout;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_search_layout);
        ButterKnife.bind(this);
        initViews();
    }

    public void invoke() {
        commonPresenter.invokeInterfaceObtainData(XxbService.GLOBALSEARCH, transDataModel, new TypeToken<GlobalSearchModel>() {
        });
    }

    private void initViews() {
        searchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {//搜索调接口
                    invokeSearch();
                }
                return false;
            }

        });

        searchEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    clearBt.setVisibility(View.VISIBLE);
                    transDataModel.setKeyword(s.toString());
                    invoke();
                } else {
                    clearBt.setVisibility(View.GONE);
                    clientLayout.setVisibility(View.GONE);
                    contactLayout.setVisibility(View.GONE);
                    businessLayout.setVisibility(View.GONE);
                    bargainLayout.setVisibility(View.GONE);
                    noteLayout.setVisibility(View.GONE);
                    followLayout.setVisibility(View.GONE);
                }
            }
        });

        if (!TextUtils.isEmpty(getIntent().getStringExtra("voiceConent"))) {
            searchEt.setText(getIntent().getStringExtra("voiceConent"));
            searchEt.setSelection(searchEt.getText().toString().length());
        }
        openKeyboard(mHandler, 200);
    }


    /**
     * 弹出键盘
     *
     * @param mHandler
     * @param s
     */
    private void openKeyboard(Handler mHandler, int s) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, s);
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        if (data != null) {
            setDataToLayout((GlobalSearchModel) data);
        }
    }

    /**
     * 设置界面
     *
     * @param model
     */
    private void setDataToLayout(GlobalSearchModel model) {

        initLayout(model.getCsrJsonArray(), clientLayout, "客户");
        initLayout(model.getCsrcontactJsonArray(), contactLayout, "客户联系人");
        initLayout(model.getCsrconJsonArray(), bargainLayout, "成交");
        initLayout(model.getCsrbusinessJsonArray(), businessLayout, "商机");
        initLayout(model.getFollowUpJsonArray(), followLayout, "客户跟进");
        initLayout(model.getNoteJsonArray(), noteLayout, "日志");

    }


    private void initLayout(List<GlobalSearchItemModel> specialList, final LinearLayout specialLayout, String typeName) {

        specialLayout.removeAllViews();
        if (specialList.size() > 0) {

            specialLayout.setVisibility(View.VISIBLE);
            View view = getLayoutInflater().inflate(R.layout.global_search_title_layout, null);
            TextView titleTv = (TextView) view.findViewById(R.id.type_name_tv);
            titleTv.setText(typeName + "(" + specialList.size() + ")");
            specialLayout.addView(view);

            for (int i = 0; i < specialList.size(); i++) {
                View contentView = getLayoutInflater().inflate(R.layout.global_search_title_layout, null);
                TextView contentTv = (TextView) contentView.findViewById(R.id.type_name_tv);
                contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                contentTv.setTextColor(ContextCompat.getColor(this, R.color.colorListName));
                contentTv.setText(specialList.get(i).getTitle());
                SpannableString ss = new SpannableString(specialList.get(i).getTitle());
                int pos = specialList.get(i).getTitle().indexOf(searchEt.getText().toString());
                if (pos != -1) {
                    ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AppConfig.getContext(), R.color.colorBlue)), pos, pos + searchEt.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    contentTv.setText(ss);
                }


                if (i > 2) {
                    contentView.setVisibility(View.GONE);
                }
                specialLayout.addView(contentView);


                contentView.setTag(specialList.get(i));
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlobalSearchItemModel m = (GlobalSearchItemModel) v.getTag();

                        switch (m.getGlobalType()) {
                            case "1"://客户
                                startActivity(new Intent(GlobalSearchActivity.this, ClientPersonaActivity.class)
                                        .putExtra("customerId", m.getCsrId())
                                        .putExtra("customerName", m.getTitle()));
                                break;
                            case "2"://客户联系人
                                startActivity(new Intent(GlobalSearchActivity.this, ClientAddContactDetailsActivity.class)
                                        .putExtra("contactId", m.getId()));

                                break;
                            case "3"://商机
                                AddBusinessTransModel mb = new AddBusinessTransModel();
                                mb.setBusinessId(m.getId());
                                mb.setBusinessName(m.getTitle());
                                startActivity(new Intent(GlobalSearchActivity.this, ClientDetaisBusinessActivity.class).putExtra("model", mb).putExtra("customerId", m.getCsrId()));

                                break;
                            case "4"://成交
                                AddContractTransModel model = new AddContractTransModel();
                                model.setConId(m.getId());
                                model.setCsrId(m.getCsrId());
                                startActivity(new Intent(GlobalSearchActivity.this, ClientDetailsContractActivity.class).putExtra("model", model).putExtra("customerId", m.getCsrId()));

                                break;
                            case "5"://客户跟进
                                startActivity(new Intent(GlobalSearchActivity.this, ClientRecordsActivity.class).putExtra("customerId", m.getCsrId()).putExtra("customerName", m.getTitle()));
                                break;
                            case "6"://日志
                                startActivity(new Intent(GlobalSearchActivity.this, NoticeDetailActivity.class)
                                        .putExtra("content", m.getTitle()));
                                break;
                        }
                    }
                });
            }
            if (specialList.size() > 3) {
                View moreView = getLayoutInflater().inflate(R.layout.global_search_bottom_layout, null);
                moreView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < specialLayout.getChildCount(); i++) {

                            if (specialLayout.getChildAt(i).getVisibility() == View.GONE) {
                                specialLayout.getChildAt(i).setVisibility(View.VISIBLE);
                            }
                        }
                        specialLayout.removeView(specialLayout.getChildAt(specialLayout.getChildCount() - 1));

                    }
                });
                specialLayout.addView(moreView);
            }
        } else {
            specialLayout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.left_icon, R.id.clear_bt, R.id.cancel_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_icon:
                invokeSearch();
                break;
            case R.id.clear_bt:
                searchEt.setText("");
                break;
            case R.id.cancel_bt:
                AppTools.hideKeyboard(searchEt);
                finish();
                break;
        }
    }

    private void invokeSearch() {
        if (!TextUtils.isEmpty(searchEt.getText().toString())) {
            transDataModel.setKeyword(searchEt.getText().toString());
            invoke();
        } else {
            AppTools.getToast("搜索关键字不能为空！");
        }
    }

    @Override
    public void onBackPressed() {
        AppTools.hideKeyboard(searchEt);
        super.onBackPressed();
    }
}
