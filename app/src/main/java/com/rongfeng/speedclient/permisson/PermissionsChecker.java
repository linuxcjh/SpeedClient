package com.rongfeng.speedclient.permisson;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.utils.AppConfig;


/**
 * 检查权限
 */
public class PermissionsChecker {

    public PermissionsChecker() {
    }

    private volatile static PermissionsChecker permissionsChecker;

    public static PermissionsChecker getPermissionsChecker() {
        if (permissionsChecker == null) {
            synchronized (PermissionsChecker.class) {
                if (permissionsChecker == null) {
                    permissionsChecker = new PermissionsChecker();
                }
            }
        }
        return permissionsChecker;
    }

    // 判断权限集合
    public boolean lacksPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(AppConfig.getContext(), permission) ==
                PackageManager.PERMISSION_DENIED;
    }


    /**
     * 启动权限请求
     *
     * @param activity
     * @param permissions
     */
    public void startPermissionsActivity(Activity activity, String[] permissions) {
        PermissionsActivity.startActivityForResult(activity, ConstantPermission.PERMISSION_REQUEST_CODE, permissions);
    }

}
