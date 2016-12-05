package com.rongfeng.speedclient.components;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppConfig;

/**
 * @author ghl
 * @category 设置浮层引导页工具类
 */
public class GuideViewDisplayUtil {
    private Activity mActivity;
    private View viewContent;

    public GuideViewDisplayUtil(Activity mActivity, View viewContent) {
        super();
        this.mActivity = mActivity;
        this.viewContent = viewContent;
        setGuideView();
    }

    /**
     * @return 返回最顶层视图
     */
    public View getDeCorView() {
        return (ViewGroup) mActivity.getWindow().getDecorView();
    }

    /**
     * @return 返回内容区域根视图
     */
    public View getRootView() {
        return (ViewGroup) mActivity.findViewById(android.R.id.content);
    }

    /**
     * 设置浮层引导页
     */
    public void setGuideView() {
        View view = getRootView();
        if (view == null) {
            return;
        }
        if (!TextUtils.isEmpty(AppConfig.getStringConfig("Guide", ""))) {
            return;
        }
        final FrameLayout frameLayout = (FrameLayout) view;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        viewContent.setLayoutParams(layoutParams);

        Button confirm = (Button) viewContent.findViewById(R.id.no_more_bt);
        Button cancel = (Button) viewContent.findViewById(R.id.cancel_bt);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfig.setStringConfig("Guide", "1");
                frameLayout.removeView(viewContent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfig.setStringConfig("Guide", "");
                frameLayout.removeView(viewContent);
            }
        });

        frameLayout.addView(viewContent);

    }

    /**
     * 移除浮层引导页
     */
    public void cancelGuideView() {
        if (AppConfig.getStringConfig("Guide", "") != mActivity.getClass().getName()) {
            return;
        }
        FrameLayout view = (FrameLayout) getRootView();
        if (view == null) {
            return;
        }
        view.removeView(viewContent);

    }
}
