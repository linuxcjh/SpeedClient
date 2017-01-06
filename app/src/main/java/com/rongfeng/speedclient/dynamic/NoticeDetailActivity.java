package com.rongfeng.speedclient.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日志详情
 * <p/>
 * Alex
 */
public class NoticeDetailActivity extends BaseActivity {


    @Bind(R.id.note_tv)
    TextView noteTv;
    @Bind(R.id.root_layout)
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail_layout);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {

        noteTv.setText(getIntent().getStringExtra("content"));
    }



    @OnClick({R.id.note_tv, R.id.root_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_tv:
                finish();
                break;
            case R.id.root_layout:
                finish();
                break;
        }
    }
}
