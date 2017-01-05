package com.rongfeng.speedclient.call;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.voice.model.AddRemindModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加提醒
 * 2016/1/13
 */
public class CallAddRemindFragment extends BaseFragment {


    @Bind(R.id.cancel_bt)
    TextView cancelBt;
    @Bind(R.id.confirm_bt)
    TextView confirmBt;
    @Bind(R.id.tip_tv)
    TextView tipTv;
    @Bind(R.id.content_et)
    TextView contentEt;
    private AddRemindModel addRemindModel = new AddRemindModel();


    public static CallAddRemindFragment newInstance(String clientName, String contactName, String clientId) {

        Bundle args = new Bundle();
        args.putString("clientName", clientName);
        args.putString("contactName", contactName);
        args.putString("clientId", clientId);
        CallAddRemindFragment fragment = new CallAddRemindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alert_add_remind_layout, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    public void init() {
        addRemindModel.setCsrId(getArguments().getString("clientId"));

    }

    private void invoke() {
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTSKREMIND, transDataModel, new TypeToken<BaseDataModel>() {
        });
    }

    @OnClick({R.id.cancel_bt, R.id.confirm_bt, R.id.content_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                getActivity().finish();
                break;
            case R.id.confirm_bt:


                if (!TextUtils.isEmpty(contentEt.getText().toString())) {
                    addRemindModel.setRemindDate(contentEt.getText().toString().split(" ")[0]);
                    addRemindModel.setRemindHour(contentEt.getText().toString().split(" ")[1]);

                    invoke();
                } else {
                    AppTools.getToast("请选择时间");
                }
                break;
            case R.id.content_et:

                AppTools.obtainDataAndTime(getActivity(), contentEt);

                break;
        }
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        if (status == 1) {
            AppTools.getToast("已成功设置下次跟进提醒");
            getActivity().finish();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);


    }
}

