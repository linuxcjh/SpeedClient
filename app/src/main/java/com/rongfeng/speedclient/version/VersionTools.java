package com.rongfeng.speedclient.version;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.io.File;

/**
 * 版本控制工具
 */
public class VersionTools {

    public static String getAppSavePath(Context context) {

        return AppTools.getFileSavePath(context) + "/xxb.apk";
    }

    /**
     * @param context
     */
    public static void installAPKFile(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(context.getFilesDir().getPath(), UpdateService.FILE_NAME)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 4.0以后的系统必须有这句，否则安装完成直接退出不显示安装成功界面
        context.startActivity(intent);

    }

    /**
     * 方法名: getVersionCode
     * <p>
     * 功能说明： 返回当前应用的版本号
     * </p>
     *
     * @return
     */
    public static int getVersionCode() {
        int verCode = 0;
        try {
            verCode = AppConfig.getContext().getPackageManager()
                    .getPackageInfo(AppConfig.getContext().getPackageName(), 0).versionCode;


        } catch (NameNotFoundException e) {
        }
        return verCode;
    }


}
