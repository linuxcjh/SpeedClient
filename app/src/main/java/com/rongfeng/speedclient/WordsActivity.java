package com.rongfeng.speedclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class WordsActivity extends AppCompatActivity {


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


}
