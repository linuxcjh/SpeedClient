package com.rongfeng.speedclient.call;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.login.TransDataModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加商机
 */
public class AlertActivity extends Activity implements ICommonAction {


    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.cancel_bt)
    Button cancelBt;
    @Bind(R.id.confirm_bt)
    Button confirmBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏，一定要在setContentView之前
        setContentView(R.layout.activity_alert_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

    }

    @OnClick({R.id.cancel_bt, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                finish();
                break;
            case R.id.confirm_bt:
                TransDataModel model = new TransDataModel();
                model.setNoteContent(contentEt.getText().toString());
                CommonPresenter commonPresenter = new CommonPresenter(this);
                commonPresenter.isShowProgressDialog = false;
                commonPresenter.invokeInterfaceObtainData(XxbService.INSERTNOTE, model,
                        new TypeToken<BaseDataModel>() {
                        });
                AppTools.getToast("已存为笔记");
                finish();
                break;
        }
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status) {


    }
}
