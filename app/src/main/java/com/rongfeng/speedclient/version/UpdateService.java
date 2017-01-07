package com.rongfeng.speedclient.version;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.home.MainTableActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class UpdateService extends Service {
    private static String urlDown;
    private static final int DOWN_OK = 1; // 下载完成
    private static final int DOWN_ERROR = 0;
    private static final int UPDATE_PROGRESS = 2;


    private NotificationManager notificationManager;
    private Notification notification;
    private Notification.Builder builder;

    private Intent updateIntent;
    private PendingIntent pendingIntent;

    private int notification_id = 0;
    long totalSize = 0;// 文件总大小
    int tem;

    public static String FILE_NAME = "xxb.apk"; //保存文件名

    /***
     * 更新UI
     */
    final Handler handler = new Handler() {
        @SuppressWarnings("deprecation")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_OK:
                    tem = 0;
                    // 下载完成，点击安装
                    Intent installApkIntent = getFileIntent();
                    pendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installApkIntent, 0);
                    notification.contentIntent = pendingIntent;
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    contentView.setTextViewText(R.id.notificationTitle, "下载成功，点击安装");
                    contentView.setViewVisibility(R.id.notificationProgress, View.GONE);
                    notificationManager.notify(notification_id, notification);
                    stopService(updateIntent);
                    break;
                case DOWN_ERROR:
                    tem = 0;
                    builder.setContentIntent(pendingIntent);
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    contentView.setTextViewText(R.id.notificationTitle, "下载失败");
                    notificationManager.notify(notification_id, notification);
                    stopService(updateIntent);

                    break;
                case UPDATE_PROGRESS:
                    int progress = (int) msg.obj;

                    contentView.setTextViewText(R.id.notificationPercent, progress + "%");
                    contentView.setProgressBar(R.id.notificationProgress, 100, progress, false);
                    notificationManager.notify(notification_id, notification);

                    break;
                default:
                    tem = 0;
                    stopService(updateIntent);
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            try {
                urlDown = intent.getStringExtra("downUrl");
                createNotification();// 创建通知
                downloadUpdateFile(urlDown); // 开始下载
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void downloadUpdateFile(final String urlDown) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                FileOutputStream fos = null;
                URLConnection conn;
                try {
                    URL url = new URL(urlDown);
                    conn = url.openConnection();
                    conn.connect();
                    totalSize = conn.getContentLength();
                    is = conn.getInputStream();
                    fos = openFileOutput(FILE_NAME, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
                    byte b[] = new byte[1024];
                    int len;
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                        updateProgress(len);
                    }
                    fos.flush();
                    VersionTools.installAPKFile(getApplicationContext());
                    handler.sendEmptyMessage(DOWN_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(DOWN_ERROR);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        } else {
                            handler.sendEmptyMessage(DOWN_ERROR);
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();
    }

    /***
     * 创建通知栏
     */
    RemoteViews contentView;

    @SuppressWarnings("deprecation")
    public void createNotification() {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /***
         * 在这里我们用自定的view来显示Notification
         */
        contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setTextViewText(R.id.notificationTitle, "正在下载");
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

        updateIntent = new Intent(this, MainTableActivity.class);
        updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        builder = new Notification.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("开始下载");
        builder.setContentIntent(pendingIntent);
        builder.setContent(contentView);

        notification = builder.getNotification();
        notificationManager.notify(notification_id, notification);
    }

    /**
     * 更新进度
     *
     * @param len
     * @return
     */
    int i = 1;

    private void updateProgress(int len) {
        tem = tem + len;
        int progress = (int) (100 * tem / totalSize);
        if (progress > 0 && progress % i == 0) {
//            handler.sendMessage(handler.obtainMessage(UPDATE_PROGRESS, progress));
            handler.sendMessageDelayed(handler.obtainMessage(UPDATE_PROGRESS, progress), 300);
            i++;
        }
    }

    public static Intent getFileIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(AppConfig.getContext().getFilesDir().getPath(), UpdateService.FILE_NAME)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 4.0以后的系统必须有这句，否则安装完成直接退出不显示安装成功界面
        return intent;
    }

}