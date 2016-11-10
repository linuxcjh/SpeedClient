package com.rongfeng.speedclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordsActivity extends AppCompatActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.commit_tv)
    TextView commitTv;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_words);
        ButterKnife.bind(this);
        initData();


    }

    /**
     * 初始化数据
     */
    private void initData() {


    }


    @OnClick(R.id.cancel_tv)
    public void onClick() {
        this.finish();
    }
}
