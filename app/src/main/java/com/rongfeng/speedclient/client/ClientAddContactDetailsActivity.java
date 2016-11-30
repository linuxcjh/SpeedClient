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
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.IUpLoadPictureAction;
import com.rongfeng.speedclient.common.UpLoadPicturePresenter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.GetCustomerContactNum;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.entity.ContactDetail;
import com.rongfeng.speedclient.permisson.PermissionsActivity;
import com.rongfeng.speedclient.permisson.PermissionsChecker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加联系人
 */
public class ClientAddContactDetailsActivity extends BaseActivity implements IUpLoadPictureAction {


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


        model.setCsrContactId(getIntent().getStringExtra("contactId"));
        invoke(model);

    }


    private void initViews() {
        upLoadPicturePresenter = new UpLoadPicturePresenter(this, this);
        titleTv.setText("信息编辑");
        contactExInfoLayout.setVisibility(View.VISIBLE);
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
     */
    private void invoke(ContactPersonModel model) {
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCONTACTBYCSRCONTACTID, model,
                new TypeToken<ContactPersonModel>() {
                });

    }

    /**
     */
    private void invokeUpate(ContactPersonModel model) {
        commonPresenter.invokeInterfaceObtainData(XxbService.UPDATECSRCONTACT, model,
                new TypeToken<BaseDataModel>() {
                });

    }

    @Override
    public void obtainFileId(int size) {
        if (size > 0 && (size == upLoadPicturePresenter.paths.size())) {
            model.setFileId(upLoadPicturePresenter.picIds.get(0).getFileId());
            upLoadPicturePresenter.picIds.clear();
            invokeUpate(model);
        }
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.SEARCHCSRCONTACTBYCSRCONTACTID:
                if (status == 1) {

                    model = (ContactPersonModel) data;
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
                break;
            case XxbService.UPDATECSRCONTACT:
                if (status == 1) {
                    AppTools.getToast("更新成功");
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
                    invokeUpate(model);
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
                    new GetCustomerContactNum(ClientAddContactDetailsActivity.this,
                            mHandler, Constant.CONTACT_SELECT_RESULT)
                            .getPhone(data);
                    break;
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
                        AppTools.getCapturePath(ClientAddContactDetailsActivity.this);

                    } else if (model.getDictionaryName().equals(getString(R.string.photo_picture))) {//相册
                        AppTools.getSystemImage(ClientAddContactDetailsActivity.this);
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
