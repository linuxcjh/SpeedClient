package com.rongfeng.speedclient.voice;

import android.os.Bundle;
import android.widget.ImageView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 笔记
 */
public class VoiceNoteActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_note_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

    }


    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }
}
