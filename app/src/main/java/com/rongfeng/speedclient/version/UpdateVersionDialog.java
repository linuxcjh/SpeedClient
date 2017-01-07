package com.rongfeng.speedclient.version;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @file : UpdateVersionDialog.java [V 1.0.0]
 * @author: 陈建辉
 * @time : CREAT AT 2013-9-23 上午8:52:28
 * <p/>
 * 【版本升级提示对话框】
 */
public class UpdateVersionDialog extends Dialog {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.confirm)
    TextView confirm;
    /**
     * 提示内容
     */
    private Handler mHandler;
    private int updateFlag;
    private Window dialogWindow;
    private String content;
    private Context context;


    public UpdateVersionDialog(Context context, Handler handler, String content, int updateFlag) {
        super(context, R.style.selection_dialog_theme);
        this.context = context;
        this.content = content;
        this.mHandler = handler;
        this.updateFlag = updateFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog_layout);
        dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(android.R.style.Animation_Dialog);
        ButterKnife.bind(this);
        this.init();
    }

    private void init() {

        message.setText(content);
        if (updateFlag == 1) {
            cancel.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param str
     */
    public UpdateVersionDialog setTitle(String str) {
        if (title != null) title.setText(str);
        return this;
    }

    /**
     * 设置message
     *
     * @param str
     */
    public UpdateVersionDialog setMessage(String str) {
        if (message != null) message.setText(str);
        return this;
    }

    public UpdateVersionDialog setConfirm(String str) {
        if (confirm != null) confirm.setText(str);
        return this;
    }

    public UpdateVersionDialog setCancel(String str) {
        if (cancel != null) cancel.setText(str);
        return this;
    }

    public UpdateVersionDialog buildDialog() {
        super.show();
        android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getDeviceWidthPixels(context);
        dialogWindow.setAttributes(lp);
        return this;
    }

    @OnClick({R.id.cancel, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();

                break;
            case R.id.confirm:
                mHandler.sendMessage(mHandler
                        .obtainMessage(Constant.NOTICE_DOWNLOAD));
                dismiss();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
