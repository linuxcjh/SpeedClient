package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientLinkmanAdapter;
import com.rongfeng.speedclient.client.entry.ContactPersonModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.components.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 客户联系人
 */
public class ClientContactsActivity extends BaseActivity {


    @Bind(R.id.grid_view)
    MyGridView gridView;
    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.client_image_view)
    ImageView clientImageView;
    @Bind(R.id.client_name_tv)
    TextView clientNameTv;
    @Bind(R.id.contact_num_tv)
    TextView contactNumTv;
    private ClientLinkmanAdapter adapter;
    List<ContactPersonModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_contact_layout);
        ButterKnife.bind(this);

        initViews();
        invoke();

    }

    private void invoke() {
        transDataModel.setCsrId(getIntent().getStringExtra("customerId"));
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCONTACTBYCSRID, transDataModel, new TypeToken<List<ContactPersonModel>>() {
        });
    }


    private void initViews() {
        clientNameTv.setText(getIntent().getStringExtra("customerName"));

        if (TextUtils.isEmpty(getIntent().getStringExtra("clientType")) && getIntent().getStringExtra("clientType").equals("1")) {
            clientImageView.setImageResource(R.drawable.custshow_company);
        } else {
            clientImageView.setImageResource(R.drawable.custshow_personal);
        }

        adapter = new ClientLinkmanAdapter(this, models, mHandler);
        gridView.setAdapter(adapter);
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.SEARCHCSRCONTACTBYCSRID:
                models = (List<ContactPersonModel>) data;
                adapter.setData(models);
                contactNumTv.setText("该客户下的联系人(" + models.size() + ")");
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.BIND_CONTACT_SIGN:
                    Intent intent = new Intent(ClientContactsActivity.this, ClientAddContactUpLoadActivity.class);
                    intent.putExtra("customerId", getIntent().getStringExtra("customerId"));
                    startActivityForResult(intent, Constant.ADD_CONTACT_REQUEST_CODE);

                    break;
                case Constant.UNBIND_CONTACT_SIGN:
                    int position = (int) msg.obj;
//                    clientLinkmanPresenter.position = position;
//                    clientLinkmanPresenter.isRefresh = true;
//                    clientLinkmanPresenter.unConnectContactsToCustomer(clientLinkmanPresenter.list.get(position));
                    break;
            }
        }
    };


    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            invoke();
        }
    }

}
