package com.rongfeng.speedclient.version;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author Administrator 版本更新下载
 */
public class DownLoadAppThread extends Thread {

	private String urlDown;
	private Context context;
	private int tem;
	private TextView tv;
	private ProgressBar progressBar;
	private AlertDialog dialog;
	private int appLength;

	public DownLoadAppThread(String urlDown, Context context, AlertDialog dialog) {
		super();
		this.urlDown = urlDown;
		this.context = context;
		this.dialog = dialog;
		View view = LayoutInflater.from(context).inflate(R.layout.version_progressbar,null);
		tv = (TextView) view.findViewById(R.id.progress_textView1);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		progressBar.setMax(100);
		progressBar.setBackgroundColor(Color.BLUE);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		// dialog.setCancelable(false);//返回键不能取消对话框
		dialog.show();
	}

	@Override
	public void run() {
		InputStream is = null;
		FileOutputStream fos = null;
		URLConnection conn;
		File file = null;
		String path = null;
		try {
			URL url = new URL(urlDown);
			conn = url.openConnection();
			conn.connect();
			appLength = conn.getContentLength();
			is = conn.getInputStream();
			path = VersionTools.getAppSavePath(context);
			file = new File(path);
			fos = context.openFileOutput("xxb.apk", Context.MODE_WORLD_READABLE| Context.MODE_WORLD_WRITEABLE);

			byte b[] = new byte[1024];
			int len;
			while ((len = is.read(b)) != -1) {
				fos.write(b, 0, len);
				updateProgress(len);
			}
			fos.flush();
			sendBroadcast();
		} catch (Exception e) {
			e.printStackTrace();
			if (file.exists()) {
				file.delete();
			}
			dialog.dismiss();
			tem = 0;
			handler.sendEmptyMessage(0);

		} finally {
			try {

				is.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

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
		int progress = 100 * tem / appLength;
		if (progress > 0 && progress % i == 0) {
			Message message = new Message();
			message.arg1 = progress;
			message.what = 1;
			handler.sendMessage(message);
			i++;
		}
	}

	/**
	 * 下载完成后通知安装app
	 */
	private void sendBroadcast() {
		dialog.dismiss();
		i = 1;
		tem = 0;
		Intent intent = new Intent(Constant.BROADCAST_DISAPPEAR_SIGN_DOWNLOAD_APP);
		context.sendBroadcast(intent);
	}

	/**
	 * 更新精度条handler
	 */
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				int progress = msg.arg1;
				progressBar.setProgress(progress);
				tv.setText("已下载  " + String.valueOf(progress) + "%");
				break;
			default:
				Toast.makeText(context, "下载失败...", Toast.LENGTH_LONG).show();
				break;
			}

		}
	};
}
