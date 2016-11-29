package com.rongfeng.speedclient.organization;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.login.TransDataModel;
import com.rongfeng.speedclient.organization.model.OrganizationInfoModel;
import com.rongfeng.speedclient.organization.model.OrganizationReceivedModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 编辑部门
 */
public class OrganizationEditDepartmentActivity extends BaseActivity {

    static final int IS_EDIT_INDEX = 0x11;//是否有改动
    static final int IS_ADD_INDEX = 0x12;//是否有新增

    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.container_layout)
    GridLayout containerLayout;
    @Bind(R.id.add_layout)
    RelativeLayout addLayout;
    @Bind(R.id.container_add_layout)
    GridLayout containerAddLayout;
    private OrganizationReceivedModel receivedModel = new OrganizationReceivedModel();// 当前Fragment信息
    private List<OrganizationInfoModel> addModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_edit_department_layout);
        ButterKnife.bind(this);
        initViews();

    }


    private void initViews() {

        receivedModel = (OrganizationReceivedModel) getIntent().getSerializableExtra("model");

        setApprovalFlowData(receivedModel.getJsonArrayDep());

        addModels.add(new OrganizationInfoModel());
        addLayout(addModels);
    }

    /**
     * 新增部门
     *
     * @param departmentName
     */
    protected void invoke(String departmentName) {
        TransDataModel transDataModel = new TransDataModel();
        transDataModel.setDepartmentParentId(AppTools.getUser().getDepartmentId());
        transDataModel.setDepartmentName(departmentName);
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTDEPARTMENT, transDataModel, new TypeToken<BaseDataModel>() {
        });
    }

    /**
     * 修改部门
     *
     * @param transDataModel
     */
    protected void updateInvoke(TransDataModel transDataModel) {
        commonPresenter.invokeInterfaceObtainData(XxbService.UPDATEDEPARTMENT, transDataModel, new TypeToken<BaseDataModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTDEPARTMENT:
                if (status == 1) {
                    AppTools.getToast("添加成功");
                }
                break;
            case XxbService.UPDATEDEPARTMENT:
                if (status == 1) {
                    AppTools.getToast("修改成功");
                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.add_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:

                boolean isEdit = false;

                for (int i = 0; i < receivedModel.getJsonArrayDep().size(); i++) {
                    if (receivedModel.getJsonArrayDep().get(i).isEdit()) {
                        TransDataModel m = new TransDataModel();
                        m.setDepartmentName(receivedModel.getJsonArrayDep().get(i).getDepartmentName());
                        m.setDepartmentId(receivedModel.getJsonArrayDep().get(i).getDepartmentId());
                        updateInvoke(m);
                        isEdit = true;
                    }
                }

                for (int i = 0; i < addModels.size(); i++) {
                    if (!TextUtils.isEmpty(addModels.get(i).getDepartmentName())) {
                        invoke(addModels.get(i).getDepartmentName());
                        isEdit = true;
                    }

                }
                if (!isEdit) {
                    AppTools.getToast("部门名称未改变");
                }
                break;
            case R.id.add_layout:

                if (!TextUtils.isEmpty(addModels.get(addModels.size() - 1).getDepartmentName())) {
                    addModels.add(new OrganizationInfoModel());
                    addLayout(addModels);
                }

                break;

        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case IS_EDIT_INDEX:
                    OrganizationInfoModel m = (OrganizationInfoModel) msg.obj;
                    for (int i = 0; i < receivedModel.getJsonArrayDep().size(); i++) {
                        if (i == m.getIndex()) {
                            receivedModel.getJsonArrayDep().get(i).setEdit(true);
                            receivedModel.getJsonArrayDep().get(i).setDepartmentName(m.getDepartmentName());
                        }
                    }
                    break;
                case IS_ADD_INDEX:
                    OrganizationInfoModel ma = (OrganizationInfoModel) msg.obj;
                    addModels.get(ma.getIndex()).setDepartmentName(ma.getDepartmentName());
                    break;
            }

        }
    };

    /**
     * 添加已有
     *
     * @param rModel
     */
    public void setApprovalFlowData(final List<OrganizationInfoModel> rModel) {
        containerLayout.removeAllViews();
        int size = rModel.size();

        if (size > 0) {
            final int column = 1; //列数
            int rows = size;//行数

            containerLayout.setRowCount(rows);
            containerLayout.setColumnCount(column);

            int count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < column; j++) {
                    if (size > count) {
                        final OrganizationInfoModel model = rModel.get(count);
                        View view = LayoutInflater.from(this).inflate(R.layout.edit_add_department_item, null);
                        final TextView department_name_tv = (TextView) view.findViewById(R.id.department_name_tv);
                        final EditText department_name_edit = (EditText) view.findViewById(R.id.department_name_edit);

                        department_name_tv.setText(model.getDepartmentName());

                        department_name_tv.setTag(model);
                        department_name_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                department_name_edit.setVisibility(View.VISIBLE);
                                department_name_tv.setVisibility(View.GONE);
                                department_name_edit.setText(department_name_tv.getText().toString());
                                department_name_edit.setSelection(department_name_edit.getText().toString().length());


                            }
                        });

                        department_name_edit.setTag(count);
                        department_name_edit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {


                                OrganizationInfoModel model = new OrganizationInfoModel();
                                int count = (int) department_name_edit.getTag();
                                model.setIndex(count);
                                model.setDepartmentName(editable.toString());
                                mHandler.sendMessage(mHandler.obtainMessage(IS_EDIT_INDEX, model));

                            }
                        });

                        count++;

                        GridLayout.Spec rowSpec = GridLayout.spec(i);
                        GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.setGravity(Gravity.FILL);
                        containerLayout.addView(view, params);
                    }
                }
            }
        }
    }

    /**
     * 添加新增
     *
     * @param rModel
     */

    public void addLayout(final List<OrganizationInfoModel> rModel) {
        containerAddLayout.removeAllViews();
        int size = rModel.size();

        if (size > 0) {
            final int column = 1; //列数
            int rows = size;//行数

            containerAddLayout.setRowCount(rows);
            containerAddLayout.setColumnCount(column);

            int count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < column; j++) {
                    if (size > count) {
                        final OrganizationInfoModel model = rModel.get(count);
                        View view = LayoutInflater.from(this).inflate(R.layout.edit_add_department_item, null);
                        final TextView department_name_tv = (TextView) view.findViewById(R.id.department_name_tv);
                        final EditText department_name_edit = (EditText) view.findViewById(R.id.department_name_edit);

                        if (!TextUtils.isEmpty(model.getDepartmentName())) {
                            department_name_edit.setText(model.getDepartmentName());
                            department_name_edit.setSelection(department_name_edit.getText().toString().length());
                        }
                        department_name_tv.setVisibility(View.GONE);
                        department_name_edit.setVisibility(View.VISIBLE);
                        department_name_edit.setTag(count);
                        department_name_edit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                OrganizationInfoModel model = new OrganizationInfoModel();
                                int count = (int) department_name_edit.getTag();
                                model.setIndex(count);
                                model.setDepartmentName(editable.toString());
                                mHandler.sendMessage(mHandler.obtainMessage(IS_ADD_INDEX, model));

                            }
                        });

                        count++;

                        GridLayout.Spec rowSpec = GridLayout.spec(i);
                        GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.setGravity(Gravity.FILL);
                        containerAddLayout.addView(view, params);
                    }
                }
            }
        }
    }


}
