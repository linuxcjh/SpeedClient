package com.rongfeng.speedclient.home;

import android.app.Notification;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientFragment;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.dynamic.DynamicFragment;
import com.rongfeng.speedclient.login.Enterprise;
import com.rongfeng.speedclient.login.LoginModel;
import com.rongfeng.speedclient.login.User;
import com.rongfeng.speedclient.manage.ManageFragment;
import com.rongfeng.speedclient.mine.MineFragment;
import com.rongfeng.speedclient.permisson.PermissionsChecker;
import com.rongfeng.speedclient.push.PushUtils;
import com.rongfeng.speedclient.voice.VoiceFragment;
import com.rongfeng.speedclient.voice.VoiceRecord;
import com.rongfeng.speedclient.wave.WaveView;

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

    public static final int VOICE_RECORD_START_INDEX = 0;//录音开始
    public static final int VOICE_RECORD_END_INDEX = 1;//录音结束


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
    @Bind(R.id.tab_app_new_layout)
    RelativeLayout tabAppNewLayout;

    @Bind(R.id.time_second_tv)
    TextView timeSecondTv;
    //    @Bind(R.id.wave_view)
    public WaveView waveView;
    @Bind(R.id.wave_layout)
    public LinearLayout waveLayout;
    @Bind(R.id.app_layout)
    LinearLayout appLayout;

    MineFragment mineFirstFragment;
    DynamicFragment dynamicFragment;
    ClientFragment clientFragment;
    ManageFragment manageFragment;
    VoiceFragment voiceFragment;

    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<>();

    public VoiceRecord voiceRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main_tab_layout);
        ButterKnife.bind(this);
        invokeLogin();
        startService(new Intent(this, UpdateClientInfoService.class));//启动数据服务
        AppTools.clearPictureCache();
        initPush();
        init();
        obtainDisplayHeight();
        defaultSelect();
        AppTools.showIntro(MainTableActivity.this, tabClientLayout, Constant.TAB_CLIENT_TIPS_TAG, "快去导入客户吧\n导入客户后既可通过语音识别\n快速跟进客户");

//        AppTools.showIntro(MainTableActivity.this, tabPlusIv, Constant.TAB_VOICE_TIPS_TAG, "长安语音按钮可以快速语音输入\n公司客户尽量说出公司全称");
    }


    private void invokeLogin() {
        if (!TextUtils.isEmpty(AppTools.getUser().getUserId())) {
            LoginModel model = new LoginModel();
            model.setUserAccount(AppConfig.getStringConfig("userName", ""));
            commonPresenter.invokeInterfaceObtainData(XxbService.LOGINPHONE, model, new TypeToken<Enterprise>() {
            });
        }
    }

    private void init() {

        fragmentManager = getSupportFragmentManager();
        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_LOGIN)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(this, ConstantPermission.PERMISSIONS_LOGIN);
        }

        tabPlusIv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (waveView == null) {
                            waveView = new WaveView(MainTableActivity.this);
                            waveLayout.addView(waveView);
                        } else {
                            if (waveView != null && waveView.renderThread != null) {
                                waveView.renderThread.setRun(true);
                            }
                        }

                        voiceRecord = new VoiceRecord(MainTableActivity.this, waveView, timeSecondTv, mHandler);
                        waveLayout.setVisibility(View.VISIBLE);

                        displayVoiceFragment();
                        tabPlusIv.setImageResource(R.drawable.tabbar_voice_active);
                        voiceRecord.startPlay();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        waveLayout.setVisibility(View.GONE);
                        if (waveView != null && waveView.renderThread != null) {
                            waveView.renderThread.setRun(false);
                        }
                        tabPlusIv.setImageResource(R.drawable.tabbar_voice);
                        voiceRecord.mHandler.sendEmptyMessage(VOICE_RECORD_END_INDEX);
                        break;
                }
                return true;
            }
        });
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

    private void initPush() {
        Resources resource = this.getResources();
        String pkgName = this.getPackageName();
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                PushUtils.getMetaValue(this, "api_key"));
        // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
        // PushManager.enableLbs(getApplicationContext());

        // Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
        // 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
        // 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
                resource.getIdentifier(
                        "notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier(
                "simple_notification_icon", "drawable", pkgName));
        cBuilder.setNotificationSound(Uri.withAppendedPath(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
        // 推送高级设置，通知栏样式设置为下面的ID
        PushManager.setNotificationBuilder(this, 1, cBuilder);

    }

    @OnClick({R.id.tab_plus_iv, R.id.tab_ws_layout, R.id.tab_app_new_layout, R.id.tab_client_layout, R.id.tab_mine_layout, R.id.bottom_layout})
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
     * 显示语音Fragment
     */
    private void displayVoiceFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        changeStatus(R.id.tab_plus_iv);
        hideFragments(transaction);

        if (voiceFragment == null) {
            voiceFragment = new VoiceFragment();
            fragments.add(voiceFragment);
            transaction.add(R.id.container_layout, voiceFragment);
        } else {
            transaction.show(voiceFragment);
        }
        transaction.commit();
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == VoiceRecord.VOICE_RECORD_SEND_RESULT_INDEX) {
                if (voiceFragment != null && voiceFragment.contentEt != null) {
                    voiceFragment.contentEt.setText(voiceFragment.contentEt.getText().toString() +
                            (String) msg.obj);
                    voiceFragment.contentEt.setSelection(voiceFragment.contentEt.getText().toString().length());
                }
            }
        }
    };

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

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.LOGINPHONE:
                Enterprise model = (Enterprise) data;
                AppTools.saveEnterpriseModel(model);
                List<User> arr = model.getArr();
                if (arr != null) {
                    int len = arr.size();
                    if (len > 1) {
                    } else if (len == 1) {
                        if (!AppTools.verifyLoginInfo(model.getArr().get(0).getIsLose(), false)) {
                        } else if (!AppTools.verifyLoginNoEffect(model.getArr().get(0).getIsEffect(), false)) {
                        } else {
                            AppTools.saveUserModel(model.getArr().get(0));
                        }

                    }
                }
                break;
        }
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        voiceRecord.stopPlay();
    }


}
