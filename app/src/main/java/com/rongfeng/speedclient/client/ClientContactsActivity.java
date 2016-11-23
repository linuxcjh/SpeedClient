package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientLinkmanAdapter;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.components.MyGridView;
import com.rongfeng.speedclient.schedule.model.LinkmanModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 客户联系人
 */
public class ClientContactsActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @Bind(R.id.grid_view)
    MyGridView gridView;
    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.client_image_view)
    ImageView clientImageView;
    private ClientLinkmanAdapter adapter;
    List<LinkmanModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_contact_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        models.add(new LinkmanModel("新客户", "300个"));
        models.add(new LinkmanModel("老客户", "300个"));
        models.add(new LinkmanModel("商机客户", "300个"));
        models.add(new LinkmanModel("欠款客户", "300个"));
        models.add(new LinkmanModel("客户总数", "300个"));
        models.add(new LinkmanModel("关注客户", "300个"));
        adapter = new ClientLinkmanAdapter(this, models, mHandler);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }
}
