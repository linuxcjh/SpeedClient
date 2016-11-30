package com.rongfeng.speedclient.organization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.organization.model.OrganizationInfoModel;
import com.rongfeng.speedclient.organization.model.OrganizationReceivedModel;
import com.rongfeng.speedclient.organization.model.SetPermissionModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 人员设置
 */
public class OrganizationSetPersonActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.select_department_tv)
    TextView selectDepartmentTv;
    @Bind(R.id.input_position_tv)
    EditText inputPositionTv;
    @Bind(R.id.product_layout)
    LinearLayout productLayout;
    @Bind(R.id.select_cb)
    CheckBox selectCb;
    @Bind(R.id.title_tv)
    TextView titleTv;

    private SetPermissionModel model = new SetPermissionModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_set_person_layout);
        ButterKnife.bind(this);
        initViews();

    }


    private void initViews() {
        OrganizationInfoModel m = (OrganizationInfoModel) getIntent().getSerializableExtra("model");
        titleTv.setText(m.getUserName());
        selectDepartmentTv.setText(m.getDepartmentName());
        inputPositionTv.setText(m.getUserPosition());
        model.setDepartmentId(m.getDepartmentId());
        model.setContactPosition(m.getUserPosition());
        model.setQueryId(m.getUserId());
        model.setQueryName(m.getUserName());

        model.setIsAlly("0");
        selectCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    model.setIsAlly("1");

                } else {
                    model.setIsAlly("0");

                }
            }
        });


    }


    private void invoke(SetPermissionModel transModel) {
        commonPresenter.invokeInterfaceObtainData(XxbService.SETPERMISSIONASSIGNMENT, transModel, new TypeToken<BaseDataModel>() {
        });
    }

    /**
     * 查询自己创建的部门
     */
    protected void searchOwnDepartment() {
        transDataModel.setDepartmentId(AppTools.getUser().getDepartmentId());
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHMYDEPARTMENTBYALL, transDataModel, new TypeToken<OrganizationReceivedModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.SETPERMISSIONASSIGNMENT:

                if (status == 1) {
                    AppTools.getToast("设置成功");
                    sendBroadcast(new Intent(Constant.ORGANIZATION_REFRESH_FLAG));
                    finish();
                }

                break;
            case XxbService.SEARCHMYDEPARTMENTBYALL:
                if (data != null) {
                    OrganizationReceivedModel receivedModel = (OrganizationReceivedModel) data;
                    List<BaseDataModel> models = new ArrayList<>();
                    for (int i = 0; i < receivedModel.getJsonArrayDep().size(); i++) {
                        BaseDataModel m = new BaseDataModel();
                        m.setDictionaryId(receivedModel.getJsonArrayDep().get(i).getDepartmentId());
                        m.setDictionaryName(receivedModel.getJsonArrayDep().get(i).getDepartmentName());
                        models.add(m);
                    }

                    AppTools.selectDialog("请选择部门", OrganizationSetPersonActivity.this, models, mHandler, 0x110);

                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.select_department_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:

                if (!TextUtils.isEmpty(model.getDepartmentId())) {
                    model.setContactPosition(inputPositionTv.getText().toString());
                    invoke(model);
                } else {
                    AppTools.getToast("请选择部门");
                }

                break;
            case R.id.select_department_tv:
                searchOwnDepartment();

                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0x110:
                    BaseDataModel modelLevel = (BaseDataModel) msg.obj;
                    selectDepartmentTv.setText(modelLevel.getDictionaryName());
                    model.setDepartmentId(modelLevel.getDictionaryId());
                    break;
            }
        }
    };

}
