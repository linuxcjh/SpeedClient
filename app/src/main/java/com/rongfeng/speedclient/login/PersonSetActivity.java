package com.rongfeng.speedclient.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.IUpLoadPictureAction;
import com.rongfeng.speedclient.common.UpLoadPicturePresenter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.home.MainTableActivity;
import com.rongfeng.speedclient.permisson.PermissionsActivity;
import com.rongfeng.speedclient.permisson.PermissionsChecker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex on 2015/11/25.
 * 个人设置
 */
public class PersonSetActivity extends BaseActivity implements IUpLoadPictureAction {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.total_tv)
    TextView totalTv;
    @Bind(R.id.avatar_iv)
    ImageView avatarIv;
    @Bind(R.id.input_name_et)
    EditText inputNameEt;
    @Bind(R.id.confirm_bt)
    Button confirmBt;
    @Bind(R.id.title_tv)
    TextView titleTv;

    private UpLoadPicturePresenter upLoadPicturePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_set_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        upLoadPicturePresenter = new UpLoadPicturePresenter(this, this);

        if (!TextUtils.isEmpty(AppTools.getUser().getUserImageUrl())) {
            AppTools.setImageViewPicture(this, AppTools.getUser().getUserImageUrl(), avatarIv);
        }

        if (!TextUtils.isEmpty(AppTools.getUser().getUserName())) {
            inputNameEt.setText(AppTools.getUser().getUserName());
            inputNameEt.setSelection(AppTools.getUser().getUserName().length());
        }

        if (getIntent().getBooleanExtra("isEdit", false)) {
            titleTv.setText("信息编辑");
            confirmBt.setText("确定");
        }


    }

    @Override
    public void obtainFileId(int size) {
        if (size > 0 && (size == upLoadPicturePresenter.paths.size())) {
            transDataModel.setFileId(upLoadPicturePresenter.picIds.get(0).getFileId());
            upLoadPicturePresenter.picIds.clear();
            invoke();
        }
    }

    private void invoke() {
        commonPresenter.invokeInterfaceObtainData(XxbService.UPDATEUSERIMAGE, transDataModel, new TypeToken<Object>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        if (status == 1) {
            startActivity(new Intent(this, MainTableActivity.class));
            finish();
        }

    }


    @OnClick({R.id.cancel_tv, R.id.confirm_bt, R.id.avatar_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.avatar_iv:
                AppTools.selectPhotoShow(this, mHandler, Constant.SINGLESELECTION);

                break;
            case R.id.confirm_bt:

                if (!TextUtils.isEmpty(inputNameEt.getText().toString())) {
                    transDataModel.setUserName(inputNameEt.getText().toString());
                    if (upLoadPicturePresenter.paths.size() > 0) {
                        upLoadPicturePresenter.uploadFile(upLoadPicturePresenter.paths);
                    } else {
                        invoke();
                    }
                } else {
                    AppTools.getToast("请输入姓名");
                }
                break;
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
                        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_PICTURE)) {
                            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(PersonSetActivity.this, ConstantPermission.PERMISSIONS_PICTURE);
                        } else {
                            AppTools.getCapturePathNoWater(PersonSetActivity.this, null);
                        }
                    } else if (model.getDictionaryName().equals(getString(R.string.photo_picture))) {//相册
                        AppTools.getSystemImage(PersonSetActivity.this);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {

                case Constant.SELECT_PICTURE: //图片
                    String imagePath = AppTools.getAbsolutePath(this, data.getData());
                    if (!TextUtils.isEmpty(imagePath)) {
                        upLoadPicturePresenter.paths.clear();
                        upLoadPicturePresenter.paths.add(imagePath);
                        AppTools.setImageViewPicture(this, imagePath, avatarIv);
                    }
                    break;
                case Constant.CAMERA_REQUEST_CODE: //拍照
                    String cmPath = AppConfig.getStringConfig("cameraPath", "");
                    if (!TextUtils.isEmpty(cmPath)) {
                        upLoadPicturePresenter.paths.clear();
                        upLoadPicturePresenter.paths.add(cmPath);
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

}

