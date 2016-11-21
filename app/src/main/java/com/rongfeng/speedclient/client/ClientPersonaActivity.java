package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientPersonaAdapter;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.components.MyGridView;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 客户画像
 */
public class ClientPersonaActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.contact_num_tv)
    TextView contactNumTv;
    @Bind(R.id.contact_layout)
    LinearLayout contactLayout;
    @Bind(R.id.grid_view)
    MyGridView gridView;
    @Bind(R.id.shortcut_contract_tv)
    TextView shortcutContractTv;
    @Bind(R.id.shortcut_bus_tv)
    TextView shortcutBusTv;
    @Bind(R.id.shortcut_record_tv)
    TextView shortcutRecordTv;
    @Bind(R.id.shortcut_layout)
    RelativeLayout shortcutLayout;
    @Bind(R.id.plus_ib)
    ImageButton plusIb;
    private ClientPersonaAdapter adapter;
    List<BaseDataModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_persona_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        models.add(new BaseDataModel("新客户", "300个"));
        models.add(new BaseDataModel("老客户", "300个"));
        models.add(new BaseDataModel("商机客户", "300个"));
        models.add(new BaseDataModel("欠款客户", "300个"));
        models.add(new BaseDataModel("客户总数", "300个"));
        models.add(new BaseDataModel("关注客户", "300个"));
        adapter = new ClientPersonaAdapter(this, R.layout.client_persona_item, models);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @OnClick({R.id.cancel_tv, R.id.contact_layout, R.id.shortcut_contract_tv, R.id.shortcut_bus_tv, R.id.shortcut_record_tv, R.id.shortcut_layout, R.id.plus_ib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.contact_layout:
                startActivity(new Intent(this, ClientContactsActivity.class));
                break;
            case R.id.shortcut_contract_tv:
                startActivity(new Intent(this, ClientRecordsActivity.class));

                break;
            case R.id.shortcut_bus_tv:
                break;
            case R.id.shortcut_record_tv:
                break;
            case R.id.shortcut_layout:
                break;
            case R.id.plus_ib:
                break;
        }
    }
}
