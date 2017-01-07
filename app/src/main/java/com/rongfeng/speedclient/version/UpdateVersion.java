/**
 * UpdateVersion.java [V 1.0.0]
 * com.mingzhi.samattendance.framework.utils.UpdateVersion
 * ChenJianhui  create at 2013-9-5 下午4:06:25
 */
package com.rongfeng.speedclient.version;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppConfig;

/**
 * @file : UpdateVersion.java [V 1.0.0]
 * @author: 陈建辉
 * @time : CREAT AT 2013-9-5 下午4:06:25
 * 【版本升级】
 */
public class UpdateVersion {

    /*
     * @param 0 后台静默检测
     *
     * @param 1 前台显示检测
     */
    private int updateType = 0;

    private Context context;
    private VersionModel versionModel;
    private Handler mHandler;
    private int updateFlag;
    private String downUrl;
    private String versionContent;

    public UpdateVersion(Context context, VersionModel versionModel,
                         int updateType) {
        this.context = context;
        this.versionModel = versionModel;
        this.updateType = updateType;
    }

    public UpdateVersion(Context context, Handler mHandler) {
        this.mHandler = mHandler;
        this.context = context;
    }


    /**
     * 判断是否更新
     *
     * @return
     */
    private boolean judgeIsUpdate() {
        int betaVersion = 0;
        int serverVersion = 0;
        int limitVersion = 0;
        try {
            serverVersion = TextUtils.isEmpty(versionModel.getVersionNum())?0: Integer.parseInt(versionModel.getVersionNum().trim());
            limitVersion = TextUtils.isEmpty(versionModel.getLimitVersion())?0: Integer.parseInt(versionModel.getLimitVersion().trim());
            betaVersion = TextUtils.isEmpty(versionModel.getBetaVersion())?0: Integer.parseInt(versionModel.getBetaVersion().trim());
        } catch (Exception e) {
        }

        int locationVersion = VersionTools.getVersionCode();

        try {
            if (locationVersion < limitVersion) {

                AppConfig.setBooleanConfig(Constant.IS_UPDATE, true);
                updateFlag = 1;
                if ("1".equals(versionModel.getIsPermissionUpdate().trim())) {
                    String url = versionModel.getBetaUrl();
                    if (TextUtils.isEmpty(url)) {
                        downUrl = versionModel.getVersionDownUrl();
                        versionContent = versionModel.getVersionContent();
                    } else {
                        downUrl = url;
                        versionContent = versionModel.getBetaContent();
                    }

                } else if ("0".equals(versionModel.getIsPermissionUpdate().trim())) {
                    downUrl = versionModel.getVersionDownUrl();
                    versionContent = versionModel.getVersionContent();
                }
                return true;
            }

            if ("1".equals(versionModel.getIsPermissionUpdate().trim())) {
                AppConfig.setBooleanConfig(Constant.IS_UPDATE, true);
                updateFlag = 0;
                if (betaVersion == 0) {
                    if (serverVersion > locationVersion) {
                        downUrl = versionModel.getVersionDownUrl();
                        versionContent = versionModel.getVersionContent();
                        return true;
                    }
                }
                if (betaVersion > locationVersion) {
                    String url = versionModel.getBetaUrl();
                    downUrl = url;
                    versionContent = versionModel.getBetaContent();
                    return true;
                }

            } else if ("0".equals(versionModel.getIsPermissionUpdate().trim())) {
                if (serverVersion > locationVersion) {
                    AppConfig.setBooleanConfig(Constant.IS_UPDATE, true);
                    updateFlag = 0;
                    downUrl = versionModel.getVersionDownUrl();
                    versionContent = versionModel.getVersionContent();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void showIsUpdate() {
        if (!judgeIsUpdate()) {
            if (updateType == 0) {
                return;
            }
            showNotUpdateDialog();
        } else {
            showUpdateDialog();
        }
    }

    /**
     * 显示不更新dialog提示
     */
    private void showNotUpdateDialog() {
        Builder dialog = new Builder(context);
        dialog.setTitle("更新检测");
        dialog.setMessage("已是最新版本！");
        dialog.setNegativeButton("确定", null);
        dialog.create().show();

    }

    /**
     * 显示是否更新提示
     */
    private void showUpdateDialog() {
        String content = "";
        if (1 == updateFlag) {
            content = versionModel.getLimitTips();
            if (!TextUtils.isEmpty(content) && content.contains("\\n")) {
                content = content.replace("\\n", "\n");
            }

        } else if (0 == updateFlag) {
            content = versionContent;
            if (!TextUtils.isEmpty(content) && content.contains("\\n")) {
                content = content.replace("\\n", "\n");
            }

        }
        UpdateVersionDialog dialog = new UpdateVersionDialog(context, handler, content, updateFlag);
        dialog.buildDialog().setTitle("发现新版本！").setConfirm("立即更新").setCancel("下次再说").setMessage(content);
    }

    /**
     * 新版本下载
     */
    private void downloadApplication(Context context, String url) {
        Builder builder = new Builder(context);
        AlertDialog dialog = builder.create();
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("正在下载最新版本……");
        DownLoadAppThread appThread = new DownLoadAppThread(url, context,
                dialog);
        appThread.start();
    }


    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case Constant.NOTICE_DOWNLOAD:
//                    downloadApplication(context, downUrl);

                    Intent updateIntent =new Intent(context, UpdateService.class);
                    updateIntent.putExtra("app_name",R.string.app_name);
                    updateIntent.putExtra("downUrl",downUrl);
                    context.startService(updateIntent);
                    break;
            }
        }

        ;
    };
}
