package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddVisitRecordModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.IUpLoadPictureAction;
import com.rongfeng.speedclient.common.UpLoadPicturePresenter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.components.AddVisitGridLayoutDisplayView;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.selectpicture.SelectPictureActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加跟进
 */
public class ClientVisitActivity extends BaseActivity implements IUpLoadPictureAction {


    @Bind(R.id.back_bt)
    ImageView backBt;
    @Bind(R.id.save_bt)
    SingleClickBt saveBt;
    @Bind(R.id.client_name_tv)
    TextView clientNameTv;
    @Bind(R.id.arrow_iv)
    ImageView arrowIv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.add_pic_layout)
    AddVisitGridLayoutDisplayView addPicLayout;
    @Bind(R.id.linear_add_pic_layout)
    LinearLayout linearAddPicLayout;
    @Bind(R.id.remark_et)
    EditText remarkEt;
    @Bind(R.id.time_layout)
    LinearLayout timeLayout;


    private AddVisitRecordModel visitRecordModel = new AddVisitRecordModel();
    private UpLoadPicturePresenter upLoadPicturePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_vist_customer_activity);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        upLoadPicturePresenter = new UpLoadPicturePresenter(this, this);
        remarkEt.setText(getIntent().getStringExtra("content"));
        clientNameTv.setText(getIntent().getStringExtra("customerName"));
        timeTv.setText(DateUtil.getTime(DateUtil.yyyy_MM_dd_HH_mm));
    }


    private void invoke() {
        visitRecordModel.setCsrId(getIntent().getStringExtra("customerId"));
        visitRecordModel.setContent(remarkEt.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTFOLLOWUP, visitRecordModel, new TypeToken<BaseDataModel>() {
        });
    }

    @Override
    public void obtainFileId(int size) {

        if (addPicLayout.getData().size() == size) {
            visitRecordModel.setFollowUpImages(BasePresenter.gson.toJson(upLoadPicturePresenter.picIds));
            upLoadPicturePresenter.picIds.clear();
            invoke();
        }

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);

        switch (methodIndex) {
            case XxbService.INSERTFOLLOWUP:
                if (status == 1) {
                    AppTools.getToast("添加成功");
                    sendBroadcast(new Intent(Constant.CLIENT_REFRESH_PERSONA));
                    finish();
                }

                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseDataModel model = (BaseDataModel) msg.obj;

            switch (msg.what) {

                case Constant.SINGLESELECTION:

                    if (model.getDictionaryName().equals(getString(R.string.camera_picture))) {//拍照
                        AppTools.getCapturePath(ClientVisitActivity.this);

                    } else if (model.getDictionaryName().equals(getString(R.string.photo_picture))) {//相册
//                        AppTools.getSystemImage(ClientVisitCustomerActivity.this);
                        Intent intent = new Intent(ClientVisitActivity.this, SelectPictureActivity.class);
                        intent.putExtra(SelectPictureActivity.IS_MULTI_SELECT, true);
                        intent.putExtra(SelectPictureActivity.HASCOUNT_PICTURE, SelectPictureActivity.MAX_SIZE - addPicLayout.list.size());
                        startActivityForResult(intent, Constant.SELECT_PICTURE);
                    }
                    break;
            }
        }
    };

    @OnClick({R.id.back_bt, R.id.save_bt, R.id.time_layout, R.id.linear_add_pic_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_bt:
                finish();
                break;
            case R.id.save_bt:

                if (addPicLayout.getData().size() > 0) {
                    upLoadPicturePresenter.uploadFile(addPicLayout.getData());
                } else if (addPicLayout.getData().size() == 0) {
                    invoke();
                }


                break;
            case R.id.time_layout:

                AppTools.obtainDataAndTime(this, timeTv);
                break;
            case R.id.linear_add_pic_layout:
                if (addPicLayout.getData().size() < 9) {
                    addPicLayout.setColumn(4);
                    addPicLayout.setWidth(addPicLayout.getWidth());
                    AppTools.getCapturePath(this);

                } else {
                    AppTools.getToast("最多添加9张图片");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {

                case Constant.SELECT_PICTURE: //图片
                    List<String> result = (List<String>) data.getSerializableExtra(SelectPictureActivity.SELECTED_RESULT);
                    addPicLayout.setData(result);
                    break;

                case Constant.CAMERA_REQUEST_CODE: //拍照
                    addPicLayout.setData(AppConfig.getStringConfig("cameraPath", ""));
                    break;

            }
        }

    }
}
