package com.rongfeng.speedclient.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.voice.VoiceNoteActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AUTHOR: Alex
 * DATE: 26/10/2015 23:04
 */
public class SearchPopupWindow {


    @Bind(R.id.cancel_iv)
    ImageView cancelIv;
    private View view;
    public PopupWindow mPopupWindow;
    private Context mContext;
    private int mDisplayHeight;


    public SearchPopupWindow(Context context, int displayHeight) {
        this(context, displayHeight, null);
    }

    public SearchPopupWindow(Context context, int displayHeight, Handler handler) {
        this.mContext = context;
        this.mDisplayHeight = displayHeight;
    }


    public PopupWindow getPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.pop_shortcut_layout, null);
        ButterKnife.bind(this, view);

        mPopupWindow = new PopupWindow(view, ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth(), mDisplayHeight);
//        mPopupWindow = new PopupWindow(view);

        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return mPopupWindow;
    }


    @OnClick(R.id.cancel_iv)
    public void onClick() {
//        mPopupWindow.dismiss();
        mContext.startActivity(new Intent(mContext, VoiceNoteActivity.class));

    }
}


