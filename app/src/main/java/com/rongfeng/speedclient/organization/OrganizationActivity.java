package com.rongfeng.speedclient.organization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.ContactPersonModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.GetCustomerContactNum;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.components.MyDialog;
import com.rongfeng.speedclient.entity.ContactDetail;
import com.rongfeng.speedclient.organization.model.OrganizationInfoModel;
import com.rongfeng.speedclient.organization.model.OrganizationReceivedModel;
import com.rongfeng.speedclient.organization.model.TransOrganizationModel;
import com.rongfeng.speedclient.permisson.PermissionsChecker;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationActivity extends BaseActivity implements BackHandledInterface {

    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.container_layout)
    FrameLayout containerLayout;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.plus_ib)
    ImageButton plusIb;
    @Bind(R.id.edit_bt)
    Button editBt;
    private BackHandledFragment mBackHandedFragment;
    private boolean hadIntercept;
    private String searchDepartmentId;//部门id

    private OrganizationReceivedModel receivedModel = new OrganizationReceivedModel();// 当前Fragment信息
    private TransOrganizationModel selectPersonInfo = new TransOrganizationModel();

    private RefreshBroadCastReceiver refreshBroadCastReceiver;


//    String SMSContent = "您的好友陈建化邀请您加入【语音快脑】\n用户名：2342342343  密码：123456 。\n快去体验吧，手机App下载地址：http://www.3swin.com/download";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_layout);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {

        refreshBroadCastReceiver = new RefreshBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ORGANIZATION_REFRESH_FLAG);
        registerReceiver(refreshBroadCastReceiver, filter);


        OrganizationInfoModel model = new OrganizationInfoModel();
        model.setDepartmentName(AppTools.getUser().getDepartmentName());
        model.setDepartmentId("");
        mHandler.sendMessage(mHandler.obtainMessage(Constant.ADD_FRAGMENT_REPEAT_INDEX, model));


    }

    private void invoke(TransOrganizationModel transModel) {
        commonPresenter.invokeInterfaceObtainData(XxbService.PULLTHEUSER, transModel, new TypeToken<TransOrganizationModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.PULLTHEUSER:
                if (status == 1) {
                    TransOrganizationModel m = (TransOrganizationModel) data;
                    if (m.getIsExist().equals("1")) {
                        AppTools.getToast("该用户已存在");
                    } else {
                        AppTools.getToast("添加成功");
                    }
                }
                break;

        }
    }

    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.ADD_FRAGMENT_REPEAT_INDEX:
                    OrganizationInfoModel m = (OrganizationInfoModel) msg.obj;
                    searchDepartmentId = m.getDepartmentId();
                    titleTv.setText(m.getDepartmentName());
                    setFragment();

                    break;
                case Constant.JUDGE_DISPLAY_BUTTON:
                    plusIb.setVisibility(View.GONE);
                    editBt.setVisibility(View.GONE);

                    break;
                case Constant.SHOW_SEARCH_VIEW_INDEX:
                    setPopSearch();
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
                        selectPersonInfo.setDepartmentId(AppTools.getUser().getDepartmentId());
                        selectPersonInfo.setName(modelCon.getName());
                        selectPersonInfo.setPhone(modelCon.getPhone());

                        MyDialog dialog = new MyDialog(OrganizationActivity.this, mHandler);
                        dialog.buildDialog().setTitle("提示").setCancelText("取消").setConfirm("确定").setMessage("是否发送短信邀请 " + modelCon.getName() + " " + modelCon.getPhone() + " ?");
                    }

                    break;

                case Constant.CONFIRMDIALOG:
                    invoke(selectPersonInfo);
//                    sendSMS(selectPersonInfo.getPhone(), AppTools.getUser().getUserName() + "邀请您加入【快脑】" +
//                            "用户名:" + selectPersonInfo.getPhone() + " 密码:123456" +
//                            "，欢迎体验," + "App下载：http://t.cn/Rf8OoAJ");//发送短信

                    doSendSMSTo(selectPersonInfo.getPhone(), AppTools.getUser().getUserName() + "邀请您加入【快脑】" +
                            "用户名:" + selectPersonInfo.getPhone() + " 密码:123456" +
                            "，欢迎体验," + "App下载：http://t.cn/Rf8OoAJ");

                    break;
            }
        }

    };

    public void doSendSMSTo(String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }

    public void sendSMS(String phoneNumber, String message) {
        //获取短信管理器
        SmsManager smsManager = SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }

    }

    private void setPopSearch() {
        SearchNewPopupWindow searchPopupWindow = new SearchNewPopupWindow(this, Utils.getDecorViewHeightPixels(this) - 1);
        searchPopupWindow.getPopupWindow().showAsDropDown(view);
//        searchPopupWindow.setListData(fragment.list);
    }


    /**
     * 添加Fragment
     */
    private void setFragment() {
        OrganizationFragment fragment = OrganizationFragment.newInstance(searchDepartmentId);
        fragment.setHandler(mHandler);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_layout, fragment);
        ft.addToBackStack("tag");
        ft.commit();


    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;


    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                super.onBackPressed();
                finish();
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 2) {//第一个Fragment
                getSupportFragmentManager().popBackStack();
                titleTv.setText(AppTools.getUser().getDepartmentName());
                plusIb.setVisibility(View.VISIBLE);
                editBt.setVisibility(View.VISIBLE);
                searchDepartmentId = "";
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }


    @Override
    public void editDepartment(OrganizationReceivedModel organizationReceivedModel) {
        this.receivedModel = organizationReceivedModel;
    }

    @OnClick({R.id.cancel_tv, R.id.edit_bt, R.id.plus_ib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                onBackPressed();
                break;
            case R.id.edit_bt://编辑部门

                if (mBackHandedFragment != null) {
                    startActivityForResult(new Intent(this, OrganizationEditDepartmentActivity.class).putExtra("model", receivedModel), Constant.EDIT_DEPARTMENT_INDEX);
                }

                break;
            case R.id.plus_ib:
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
                    new GetCustomerContactNum(OrganizationActivity.this,
                            mHandler, Constant.CONTACT_SELECT_RESULT)
                            .getPhone(data);
                    break;
                case Constant.EDIT_DEPARTMENT_INDEX:

                    if (mBackHandedFragment != null) {
                        mBackHandedFragment.invoke();
                    }
                    break;


            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshBroadCastReceiver);
    }

    class RefreshBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constant.ORGANIZATION_REFRESH_FLAG)) {

                if (mBackHandedFragment != null) {
                    mBackHandedFragment.invoke();
                }

            }
        }
    }

}
