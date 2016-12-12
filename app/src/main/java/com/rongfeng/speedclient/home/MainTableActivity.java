package com.rongfeng.speedclient.home;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientFragment;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.dynamic.DynamicFragment;
import com.rongfeng.speedclient.manage.ManageFragment;
import com.rongfeng.speedclient.mine.MineFragment;
import com.rongfeng.speedclient.permisson.PermissionsChecker;
import com.rongfeng.speedclient.voice.VoiceFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AUTHOR: Alex
 * DATE: 21/10/2015 19:16
 */
public class MainTableActivity extends BaseActivity {


    @Bind(R.id.container_layout)
    FrameLayout containerLayout;
    @Bind(R.id.tab_ws_iv)
    ImageView tabWsIv;
    @Bind(R.id.tab_ws_tv)
    TextView tabWsTv;
    @Bind(R.id.tab_ws_layout)
    LinearLayout tabWsLayout;
    @Bind(R.id.tab_client_iv)
    ImageView tabClientIv;
    @Bind(R.id.tab_client_tv)
    TextView tabClientTv;
    @Bind(R.id.tab_client_layout)
    public LinearLayout tabClientLayout;
    @Bind(R.id.tab_plus_iv)
    ImageView tabPlusIv;
    @Bind(R.id.tab_plus_layout)
    LinearLayout tabPlusLayout;
    @Bind(R.id.tab_app_iv)
    ImageView tabAppIv;
    @Bind(R.id.tab_app_tv)
    TextView tabAppTv;
    @Bind(R.id.tab_mine_iv)
    ImageView tabMineIv;
    @Bind(R.id.tab_mine_tv)
    TextView tabMineTv;
    @Bind(R.id.tab_mine_layout)
    LinearLayout tabMineLayout;
    @Bind(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @Bind(R.id.top_view)
    ImageView topView;
    @Bind(R.id.root_layout)
    LinearLayout rootLayout;
    @Bind(R.id.tab_app_new_layout)
    RelativeLayout tabAppNewLayout;

    MineFragment mineFirstFragment;
    DynamicFragment dynamicFragment;
    ClientFragment clientFragment;
    ManageFragment manageFragment;
    VoiceFragment voiceFragment;

    @Bind(R.id.middle_container_layout)
    FrameLayout middleContainerLayout;


    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main_tab_layout);
        ButterKnife.bind(this);
        AppTools.clearPictureCache();
        init();
        obtainDisplayHeight();
        defaultSelect();
    }


    private void init() {

        fragmentManager = getSupportFragmentManager();
        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_LOGIN)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(this, ConstantPermission.PERMISSIONS_LOGIN);
        }
    }

    /**
     * 客户
     */
    private void invokeClient() {
        transDataModel.setClientType("5");
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSR, transDataModel, new TypeToken<List<AddClientTransModel>>() {
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        invokeClient();
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {

            case XxbService.SEARCHCSR:
                List<AddClientTransModel> list =  (List<AddClientTransModel>) data;
                startService(new Intent(this,UpdateClientInfoService.class).putExtra("clientList", (Serializable) list));
                AppTools.insertClientDataToDB(this, (List<AddClientTransModel>) data);
                break;
        }
    }


    /**
     * Default selected
     */
    private void defaultSelect() {
        changeStatus(R.id.tab_ws_layout);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        dynamicFragment = new DynamicFragment();
        fragments.add(dynamicFragment);
        transaction.add(R.id.container_layout, dynamicFragment);
        transaction.commit();

    }

    @OnClick({R.id.tab_plus_iv, R.id.tab_ws_layout, R.id.tab_app_new_layout, R.id.tab_plus_layout, R.id.tab_client_layout, R.id.tab_mine_layout, R.id.bottom_layout})
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.tab_ws_layout:
                changeStatus(view.getId());
                hideFragments(transaction);
                if (dynamicFragment == null) {
                    dynamicFragment = new DynamicFragment();
                    transaction.add(R.id.container_layout, dynamicFragment);
                } else {
                    transaction.show(dynamicFragment);
                }
                transaction.commit();
                break;
            case R.id.tab_client_layout:

                changeStatus(view.getId());
                hideFragments(transaction);
                if (clientFragment == null) {
                    clientFragment = new ClientFragment();
                    fragments.add(clientFragment);
                    transaction.add(R.id.container_layout, clientFragment);
                } else {
                    transaction.show(clientFragment);
                }
                transaction.commit();

                break;

            case R.id.tab_plus_layout:
            case R.id.tab_plus_iv:
                changeStatus(view.getId());
                hideFragments(transaction);

                if (voiceFragment == null) {
                    voiceFragment = new VoiceFragment();
                    fragments.add(voiceFragment);
                    transaction.add(R.id.container_layout, voiceFragment);
                } else {
                    transaction.show(voiceFragment);
                }
                transaction.commit();

                break;

            case R.id.tab_app_new_layout:
                changeStatus(view.getId());
                hideFragments(transaction);

                if (manageFragment == null) {
                    manageFragment = new ManageFragment();
                    fragments.add(manageFragment);
                    transaction.add(R.id.container_layout, manageFragment);
                } else {
                    transaction.show(manageFragment);
                }
                transaction.commit();
                break;
            case R.id.tab_mine_layout:
                changeStatus(view.getId());
                hideFragments(transaction);
                if (mineFirstFragment == null) {
                    mineFirstFragment = new MineFragment();
                    fragments.add(mineFirstFragment);
                    transaction.add(R.id.container_layout, mineFirstFragment);
                } else {
                    transaction.show(mineFirstFragment);
                }
                transaction.commit();
                break;

        }
    }


    /**
     * Change navigation status
     *
     * @param resId
     */
    private void changeStatus(int resId) {

        resetStatus();
        switch (resId) {
            case R.id.tab_ws_layout:
                setStatus(tabWsIv, tabWsTv, R.drawable.tabbar_workbench_select);
                break;
            case R.id.tab_client_layout:
                setStatus(tabClientIv, tabClientTv, R.drawable.tabbar_client_select);
                break;
            case R.id.tab_plus_layout:

                break;
            case R.id.tab_app_new_layout:
                setStatus(tabAppIv, tabAppTv, R.drawable.tabbar_application_select);
                break;
            case R.id.tab_mine_layout:
                setStatus(tabMineIv, tabMineTv, R.drawable.tabbar_me_select);
                break;
        }
    }


    /**
     * 保存屏幕高度、宽度
     */
    private void obtainDisplayHeight() {

        AppConfig.setIntConfig("HEIGHT", Utils.getDeviceHeightPixels(this));
        AppConfig.setIntConfig("WIDTH", Utils.getDeviceWidthPixels(this));

    }


    /**
     * Set navigation status
     *
     * @param imageView
     * @param textView
     * @param resId
     */
    private void setStatus(ImageView imageView, TextView textView, int resId) {


        imageView.setImageResource(resId);
        textView.setTextColor(getResources().getColor(R.color.colorBlue));

    }

    /**
     * Reset navigation status
     */
    private void resetStatus() {
        tabWsIv.setImageResource(R.drawable.tabbar_workbench);
        tabWsTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        tabClientIv.setImageResource(R.drawable.tabbar_client);
        tabClientTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        tabAppIv.setImageResource(R.drawable.tabbar_application);
        tabAppTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        tabMineIv.setImageResource(R.drawable.tabbar_me);
        tabMineTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
    }

    /**
     * Hide exist fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {

        for (Fragment fragment : fragments) {
            if (fragment != null) {
                if (fragment.isVisible()) {
                    transaction.hide(fragment);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private long mExitTime;

    /**
     * 按返回键程序后台运行
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (middleContainerLayout.getVisibility() == View.VISIBLE) {
                middleContainerLayout.setVisibility(View.GONE);
                return true;
            }

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(AppConfig.getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                Process.killProcess(Process.myPid());
                System.exit(0);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
