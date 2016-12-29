package com.rongfeng.speedclient.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 唐磊 on 2015/11/5.
 * 对话框
 */
public class AddProductDialog extends Dialog implements View.OnClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.message)
    public EditText message;
    @Bind(R.id.cancel)
    public TextView cancel;
    @Bind(R.id.confirm)
    TextView confirm;
    @Bind(R.id.line)
    View line;
    private Context context;
    private Handler handler;
    private Window dialogWindow;
    private boolean isCancel;
    private boolean isConfirm;


    public AddProductDialog(Context context, Handler handler) {
        super(context, R.style.selection_dialog_theme);
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_dialog_layout);
        dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(android.R.style.Animation_Toast);
        ButterKnife.bind(this);
    }


    /**
     * 设置标题
     *
     * @param str
     */
    public AddProductDialog setTitle(String str) {
        if (title != null) title.setText(str);
        return this;
    }

    /**
     * 设置message
     *
     * @param str
     */
    public AddProductDialog setMessage(String str) {
        if (message != null) {
            message.setText(str);
        }
        return this;
    }

    public AddProductDialog setConfirm(String str) {
        if (confirm != null) {
            confirm.setText(str);
        }
        return this;
    }

    public AddProductDialog buildDialog() {
        super.show();
        if (isCancel) {
            cancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        if (isConfirm) {
            confirm.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getDeviceWidthPixels(context);
        dialogWindow.setAttributes(lp);
        return this;
    }

    @OnClick({R.id.cancel, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel://取消
                this.dismiss();
                break;
            case R.id.confirm:
                if (!TextUtils.isEmpty(message.getText().toString())) {
                    handler.sendMessage(handler.obtainMessage(Constant.CONFIRMDIALOG, message.getText().toString().trim()));
                    this.dismiss();
                } else {
                    AppTools.getToast("请输入内容");
                }

                break;
        }
    }

    private Object object;

    /**
     * 添加返回值
     *
     * @param obj
     */
    public void setMsgObject(Object obj) {
        this.object = obj;
    }

    public AddProductDialog setCancel(boolean cancel) {
        isCancel = cancel;
        return this;
    }

    public AddProductDialog setConfirm(boolean isConfirm) {
        this.isConfirm = isConfirm;
        return this;
    }

    /**
     * 这是确定按钮文字
     *
     * @param confirmText
     */
    public AddProductDialog setConfirmText(String confirmText) {
        if (!TextUtils.isEmpty(confirmText)) {
            confirm.setText(confirmText);
        }
        return this;
    }

    /**
     * 这是取消按钮文字
     *
     * @param cancelText
     */
    public AddProductDialog setCancelText(String cancelText) {
        if (!TextUtils.isEmpty(cancelText)) {
            cancel.setText(cancelText);
        }
        return this;
    }


}
