package com.rongfeng.speedclient.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseQuickAdapter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.List;

/**
 * Created by 唐磊 on 2015/11/5.
 * 选择对话框
 */
public class SelectionAddClientDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {


    private Context context;
    private int layoutResId;
    private TextView searchBt, addBt;
    private Window dialogWindow;
    private Handler handler;
    private ListView listView;
    private LinearLayout rootLayout;
    private List<BaseDataModel> data;
    private int SELECT_SIGN;// 返回标识
    private int height;//动态设置高度


    public SelectionAddClientDialog(Context context, int layoutResId, Handler handler, int selectSign) {
        this(context, layoutResId, handler, selectSign, 0);
    }

    public SelectionAddClientDialog(Context context, int layoutResId, Handler handler, int selectSign, int height) {
        super(context, R.style.selection_dialog_theme);
        this.context = context;
        this.layoutResId = layoutResId;
        this.handler = handler;
        this.SELECT_SIGN = selectSign;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.selection_dialog_anim);
        init();
    }

    private void init() {
        try {
            listView = (ListView) findViewById(R.id.listView);
            searchBt = (TextView) findViewById(R.id.search_client_tv);
            addBt = (TextView) findViewById(R.id.add_client_tv);
            rootLayout = (LinearLayout) findViewById(R.id.root_layout);
            TextView cancel = (TextView) findViewById(R.id.cancel);//取消
            cancel.setOnClickListener(this);
            searchBt.setOnClickListener(this);
            addBt.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listView != null) {
            setLayoutHeight(height);
            listView.setOnItemClickListener(this);
        }
    }


    /**
     * 设置标题
     *
     * @param str
     */
    public SelectionAddClientDialog setTitle(String str) {
        TextView title = (TextView) findViewById(R.id.title);//标题
        if (title != null) title.setText(str);
        return this;
    }

    /**
     * 设置取消
     *
     * @param str
     */
    public SelectionAddClientDialog setCancelTitle(String str) {
        TextView cancel = (TextView) findViewById(R.id.cancel);//取消
        if (cancel != null) cancel.setText(str);
        return this;
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public SelectionAddClientDialog setAdapter(BaseQuickAdapter adapter) {
        if (listView != null) listView.setAdapter(adapter);
        data = adapter.getData();
        return this;
    }


    public SelectionAddClientDialog buildDialog() {
        super.show();
        android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = Utils.getDeviceWidthPixels(context);
        dialogWindow.setAttributes(lp);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;

            case R.id.search_client_tv:
                handler.sendMessage(handler.obtainMessage(Constant.SEARCH_CLIENT_INDEX));
                dismiss();
                break;
            case R.id.add_client_tv:
                handler.sendMessage(handler.obtainMessage(Constant.ADD_CLIENT_INDEX));
                dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        handler.sendMessage(handler.obtainMessage(SELECT_SIGN, data.get(position)));
        dismiss();
    }

    /**
     * 设置高度
     *
     * @param height
     */
    public void setLayoutHeight(int height) {
        if (height != 0) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = height;
            listView.setLayoutParams(params);
        }

    }


}
