package com.rongfeng.speedclient.organization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
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
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.entity.ContactDetail;
import com.rongfeng.speedclient.organization.model.OrganizationReceivedModel;
import com.rongfeng.speedclient.organization.model.TransOrganizationModel;
import com.rongfeng.speedclient.permisson.PermissionsChecker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationActivity extends BaseActivity implements BackHandledInterface {

    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.container_layout)
    FrameLayout containerLayout;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.plus_ib)
    ImageButton plusIb;
    private BackHandledFragment mBackHandedFragment;
    private boolean hadIntercept;


    private OrganizationReceivedModel receivedModel = new OrganizationReceivedModel();// 当前Fragment信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_layout);
        ButterKnife.bind(this);
        setFragment();
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
                    setFragment();

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
                        TransOrganizationModel transModel = new TransOrganizationModel();
                        transModel.setDepartmentId(AppTools.getUser().getDepartmentId());
                        transModel.setName(modelCon.getName());
                        transModel.setPhone(modelCon.getPhone());
                        invoke(transModel);
                    }

                    break;
            }
        }

    };

    private void setPopSearch() {
        SearchNewPopupWindow searchPopupWindow = new SearchNewPopupWindow(this, Utils.getDecorViewHeightPixels(this) - 1);
        searchPopupWindow.getPopupWindow().showAsDropDown(view);
//        searchPopupWindow.setListData(fragment.list);
    }


    private void setFragment() {
        OrganizationFragment fragment = OrganizationFragment.newInstance();
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
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }


    @Override
    public void editDepartment(OrganizationReceivedModel organizationReceivedModel) {
        this.receivedModel = organizationReceivedModel;
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.plus_ib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                onBackPressed();
                break;
            case R.id.commit_tv://编辑部门

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

}
