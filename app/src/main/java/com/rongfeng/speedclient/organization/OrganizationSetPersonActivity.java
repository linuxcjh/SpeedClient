package com.rongfeng.speedclient.organization;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.SingleClickBt;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 人员设置
 */
public class OrganizationSetPersonActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.select_department_tv)
    TextView selectDepartmentTv;
    @Bind(R.id.input_position_tv)
    EditText inputPositionTv;
    @Bind(R.id.product_layout)
    LinearLayout productLayout;
    @Bind(R.id.select_cb)
    CheckBox selectCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_set_person_layout);
        ButterKnife.bind(this);
        initViews();

    }


    private void initViews() {

        selectCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                } else {

                }
            }
        });


    }


    @OnClick({R.id.cancel_tv, R.id.commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:


                break;
        }
    }
}
