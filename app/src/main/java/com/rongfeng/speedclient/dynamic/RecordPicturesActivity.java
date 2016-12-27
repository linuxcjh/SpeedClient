package com.rongfeng.speedclient.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.IUpLoadPictureAction;
import com.rongfeng.speedclient.common.UpLoadPicturePresenter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.components.AddDeviceMaintainGridLayoutDisplayView;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.login.TransDataModel;
import com.rongfeng.speedclient.permisson.PermissionsActivity;
import com.rongfeng.speedclient.permisson.PermissionsChecker;
import com.rongfeng.speedclient.selectpicture.SelectPictureActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AUTHOR: Alex
 * DATE: 21/10/2015 19:16
 */
public class RecordPicturesActivity extends BaseActivity implements ICommonAction, IUpLoadPictureAction, AMapLocationListener {


    {

        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_LOCATION)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(this, ConstantPermission.PERMISSIONS_LOCATION);
        } else {
            AppTools.startLbsLocation(this, false);
        }

    }

    public static final int SELECT_POSITION_CLIENT_REQUEST_CODE = 0x13;


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.add_select_pic_layout)
    AddDeviceMaintainGridLayoutDisplayView addSelectPicLayout;
    @Bind(R.id.current_date_tv)
    TextView currentDateTv;


    private CommonPresenter commonPresenter = new CommonPresenter(this);
    private UpLoadPicturePresenter upLoadPicturePresenter;
    private TransDataModel transDataModel = new TransDataModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_record_layout);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {

        upLoadPicturePresenter = new UpLoadPicturePresenter(this, this);
        transDataModel.setType("1");
        addSelectPicLayout.setData(getIntent().getStringExtra("cameraPath"));

    }


    @Override
    public void obtainFileId(int size) {

        if (addSelectPicLayout.getData().size() == size) {
            transDataModel.setPositionImages(BasePresenter.gson.toJson(upLoadPicturePresenter.picIds));
            upLoadPicturePresenter.picIds.clear();
            commonPresenter.invokeInterfaceObtainData(XxbService.INSERTRELATEDPOSITION, transDataModel, new TypeToken<BaseDataModel>() {
            });
        }
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        switch (methodIndex) {
            case XxbService.INSERTRELATEDPOSITION:
                if (status == 1) {
                    AppTools.getToast("提交成功");
                    finish();
                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.current_date_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:

                if (addSelectPicLayout.getData().size() > 0) {
                    upLoadPicturePresenter.uploadFile(addSelectPicLayout.getData());
                } else if (addSelectPicLayout.getData().size() == 0) {
                    commonPresenter.invokeInterfaceObtainData(XxbService.INSERTRELATEDPOSITION, transDataModel, new TypeToken<BaseDataModel>() {
                    });
                }
                break;
            case R.id.current_date_tv:
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                if (TextUtils.isEmpty(aMapLocation.getAddress())) {
                    return;
                }
                transDataModel.setLongitude(aMapLocation.getLongitude() + "");
                transDataModel.setLatitude(aMapLocation.getLatitude() + "");
                transDataModel.setAddress(aMapLocation.getAddress());
                currentDateTv.setText(aMapLocation.getAddress());
                AppConfig.setStringConfig("location", aMapLocation.getLatitude() + ":" + aMapLocation.getLongitude());

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {

                case SELECT_POSITION_CLIENT_REQUEST_CODE://选择位置

                    PoiItem poiInfo = data.getParcelableExtra("poiInfo");
                    if (poiInfo != null) {
                        transDataModel.setAddress(poiInfo.getSnippet() + " " + poiInfo.getTitle());
                        transDataModel.setLongitude(poiInfo.getLatLonPoint().getLongitude() + "");
                        transDataModel.setLatitude(poiInfo.getLatLonPoint().getLatitude() + "");
                    }

                    break;
                case Constant.SELECT_PICTURE: //图片
                    List<String> result = (List<String>) data.getSerializableExtra(SelectPictureActivity.SELECTED_RESULT);
                    addSelectPicLayout.setData(result);
                    break;

                case Constant.CAMERA_REQUEST_CODE: //拍照
                    addSelectPicLayout.setData(AppConfig.getStringConfig("cameraPath", ""));
                    break;

            }
        }
        if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            AppTools.startLbsLocation(this, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppTools.stopLbsLocation();
    }

}
