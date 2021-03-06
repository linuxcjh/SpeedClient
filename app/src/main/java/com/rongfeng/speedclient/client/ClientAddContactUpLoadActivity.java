package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.ContactPersonModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.IUpLoadPictureAction;
import com.rongfeng.speedclient.common.UpLoadPicturePresenter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.GetCustomerContactNum;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.datanalysis.DBManager;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.entity.ContactDetail;
import com.rongfeng.speedclient.permisson.PermissionsActivity;
import com.rongfeng.speedclient.permisson.PermissionsChecker;
import com.rongfeng.speedclient.voice.VoiceAnalysisTools;
import com.rongfeng.speedclient.voice.model.CsrContactJSONArray;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加联系人
 */
public class ClientAddContactUpLoadActivity extends BaseActivity implements IUpLoadPictureAction {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.res_contact_input_tv)
    TextView resContactInputTv;
    @Bind(R.id.res_scan_card_tv)
    TextView resScanCardTv;
    @Bind(R.id.avatar_iv)
    ImageView avatarIv;
    @Bind(R.id.res_name_et)
    EditText resNameEt;
    @Bind(R.id.res_phone_et)
    EditText resPhoneEt;
    @Bind(R.id.res_position_et)
    EditText resPositionEt;
    @Bind(R.id.expand_tv)
    TextView expandTv;
    @Bind(R.id.res_contact_op_layout)
    LinearLayout resContactOpLayout;
    @Bind(R.id.reg_email_et)
    EditText regEmailEt;
    @Bind(R.id.reg_qq_et)
    EditText regQqEt;
    @Bind(R.id.reg_remark_et)
    EditText regRemarkEt;
    @Bind(R.id.contact_ex_info_layout)
    LinearLayout contactExInfoLayout;
    @Bind(R.id.title_tv)
    TextView titleTv;

    @Bind(R.id.reg_confirm_tb)
    ToggleButton regConfirmTb;
    @Bind(R.id.reg_sex_et)
    ToggleButton regSexEt;

    ContactPersonModel model = new ContactPersonModel();
    private UpLoadPicturePresenter upLoadPicturePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_contact_layout);
        ButterKnife.bind(this);
        initViews();

        setData();

    }


    private void setData() {
        if (getIntent().getSerializableExtra("model") != null) {
            model = (ContactPersonModel) getIntent().getSerializableExtra("model");
            titleTv.setText("编辑联系人");
            resNameEt.setText(model.getName());
            resPhoneEt.setText(model.getPhone());
            resPositionEt.setText(model.getContactPosition());
            regEmailEt.setText(model.getEmail());
            regRemarkEt.setText(model.getRemark());
            if (model.getIsPolicymaker().equals("1")) {
                regConfirmTb.setChecked(true);
            } else {
                regConfirmTb.setChecked(false);
            }
            if (model.getGender().equals("1")) {
                regSexEt.setChecked(true);
            } else {
                regSexEt.setChecked(false);
            }

        }
    }

    private void initViews() {
        upLoadPicturePresenter = new UpLoadPicturePresenter(this, this);

        model.setCsrId(getIntent().getStringExtra("customerId"));
        model.setIsPolicymaker("1");
        model.setGender("1");

        regConfirmTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    model.setIsPolicymaker("1");
                    ;//isPolicymaker;// 是否决策人（0不是决策 人1是决策 人）
                } else {
                    model.setIsPolicymaker("0");
                }
            }
        });

        regSexEt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    model.setGender("1");
                    //gender;// 性别【0女 1男】

                } else {
                    model.setGender("0");
                }
            }
        });
    }

    /**
     * 获取类型
     */
    private void invoke(ContactPersonModel model) {
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTCSRCONTACT, model,
                new TypeToken<CsrContactJSONArray>() {
                });

    }

    @Override
    public void obtainFileId(int size) {
        if (size > 0 && (size == upLoadPicturePresenter.paths.size())) {
            model.setFileId(upLoadPicturePresenter.picIds.get(0).getFileId());
            upLoadPicturePresenter.picIds.clear();
            invoke(model);
        }
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTCSRCONTACT:
                if (status == 1) {
                    AppTools.getToast("添加成功");

                    CsrContactJSONArray csrContact = (CsrContactJSONArray) data;
                    if(!TextUtils.isEmpty(resPhoneEt.getText().toString())){
                        csrContact.setPhone(resPhoneEt.getText().toString());
                    }
                    DBManager dbManager = new DBManager(AppConfig.getContext());
                    ClientModel clientModel = dbManager.queryTheClient(model.getCsrId());//查询联系人所属的客户信息
                    dbManager.closeDB();

                    if (clientModel != null) {
                        ArrayList<CsrContactJSONArray> lists = BasePresenter.gson.fromJson(clientModel.getContact_name(), new TypeToken<List<CsrContactJSONArray>>() {
                        }.getType());
                        lists.add(csrContact);

                        List<SyncClientInfoModel> list = new ArrayList<>();
                        SyncClientInfoModel m = new SyncClientInfoModel();
                        m.setClientNameWordsSplit(clientModel.getClient_info());
                        m.setCsrContactJSONArray(lists);
                        m.setCsrId(clientModel.getClient_id());
                        m.setCustomerName(clientModel.getClient_name());
                        m.setUpdateTime(clientModel.getClient_update_time());

                        list.add(m);
                        VoiceAnalysisTools.getInstance().analysisData(list);// 新增联系人插入数据库
                    }

                    sendBroadcast(new Intent(Constant.CLIENT_REFRESH_PERSONA)); //刷新客户画像
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.expand_tv, R.id.avatar_iv, R.id.res_contact_input_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                model.setName(resNameEt.getText().toString());
                model.setPhone(resPhoneEt.getText().toString());
                model.setContactPosition(resPositionEt.getText().toString());
                model.setEmail(regEmailEt.getText().toString());
                model.setQq(regQqEt.getText().toString());
                model.setRemark(regRemarkEt.getText().toString());

                if (TextUtils.isEmpty(resNameEt.getText().toString())) {
                    AppTools.getToast("请填写联系人姓名");
                    return;
                }
                if (upLoadPicturePresenter.paths.size() > 0) {
                    upLoadPicturePresenter.uploadFile(upLoadPicturePresenter.paths);
                } else {
                    invoke(model);
                }
                break;
            case R.id.avatar_iv:

                AppTools.selectPhotoShow(this, mHandler, Constant.SINGLESELECTION);
                break;
            case R.id.expand_tv:
                if (contactExInfoLayout.getVisibility() == View.VISIBLE) {
                    contactExInfoLayout.setVisibility(View.GONE);
                } else {
                    contactExInfoLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.res_contact_input_tv:

                if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_READ_CONTACTS)) {
                    PermissionsChecker.getPermissionsChecker().startPermissionsActivity(this, ConstantPermission.PERMISSIONS_READ_CONTACTS);
                } else {

                    Intent intent = new Intent(Intent.ACTION_PICK,
                            ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, Constant.CONTACT_SELECT_RESULT);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {

                case Constant.CONTACT_SELECT_RESULT://从通讯录选择
                    new GetCustomerContactNum(ClientAddContactUpLoadActivity.this,
                            mHandler, Constant.CONTACT_SELECT_RESULT)
                            .getPhone(data);
                    break;
//                case Constant.SELECT_PICTURE: //图片
//                    if (!TextUtils.isEmpty(AppTools.getAbsolutePath(this, data.getData()))) {
//                        AppTools.setImageViewPicture(this, AppTools.getAbsolutePath(this, data.getData()), avatarIv);
//                    }
//
//                    break;
//                case Constant.CAMERA_REQUEST_CODE: //拍照
//                    if (!TextUtils.isEmpty(AppConfig.getStringConfig("cameraPath", ""))) {
//                        AppTools.setImageViewPicture(this, AppConfig.getStringConfig("cameraPath", ""), avatarIv);
//                    }
//                    break;
                case Constant.SELECT_PICTURE: //图片
                    String imagePath = AppTools.getAbsolutePath(this, data.getData());
                    if (!TextUtils.isEmpty(imagePath)) {
                        upLoadPicturePresenter.paths.clear();
                        upLoadPicturePresenter.paths.add(imagePath);
                        avatarIv.setVisibility(View.VISIBLE);
                        AppTools.setImageViewPicture(this, imagePath, avatarIv);
                    }
                    break;
                case Constant.CAMERA_REQUEST_CODE: //拍照
                    String cmPath = AppConfig.getStringConfig("cameraPath", "");
                    if (!TextUtils.isEmpty(cmPath)) {
                        upLoadPicturePresenter.paths.clear();
                        upLoadPicturePresenter.paths.add(cmPath);
                        avatarIv.setVisibility(View.VISIBLE);
                        AppTools.setImageViewPicture(this, cmPath, avatarIv);
                    }
                    break;

            }
        }
        //拒绝定位权限退出
        if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED)

        {
            finish();
        } else if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED)

        {
            AppTools.selectPhotoShow(this, mHandler, Constant.SINGLESELECTION);
        }

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case Constant.SINGLESELECTION:
                    BaseDataModel model = (BaseDataModel) msg.obj;

                    if (model.getDictionaryName().equals(getString(R.string.camera_picture))) {//拍照
                        AppTools.getCapturePath(ClientAddContactUpLoadActivity.this);

                    } else if (model.getDictionaryName().equals(getString(R.string.photo_picture))) {//相册
                        AppTools.getSystemImage(ClientAddContactUpLoadActivity.this);
                    }
                    break;
                case Constant.CONTACT_SELECT_RESULT:

                    ContactDetail detail = (ContactDetail) msg.obj;
                    if (!TextUtils.isEmpty(detail.getOnlyPhone())) {
                        ContactPersonModel modelCon = new ContactPersonModel();
                        modelCon.setName(detail.getName());
                        String result = detail.getOnlyPhone().replace(" ", "");
                        if (result.length() > 11) {
                            modelCon.setPhone(result.substring(detail
                                    .getOnlyPhone().length() - 11));

                        } else {
                            modelCon.setPhone(result.replace(" ", ""));

                        }
                        resNameEt.setText(modelCon.getName());
                        resPhoneEt.setText(modelCon.getPhone());
                    }

                    break;
            }

        }
    };

}
