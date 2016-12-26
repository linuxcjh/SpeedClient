package com.rongfeng.speedclient.schedule;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.mine.ClientSelectActivity;
import com.rongfeng.speedclient.utils.DensityUtil;
import com.rongfeng.speedclient.utils.FlowLayout;
import com.rongfeng.speedclient.voice.model.AddRemindModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 唐磊 on 2015/12/9.
 */
public class ScheduleAddEditActivity extends BaseActivity {


    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.commit_tv)
    SingleClickBt complete;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;
    @Bind(R.id.client_name_tv)
    TextView clientNameTv;
    @Bind(R.id.client_name_layout)
    LinearLayout clientNameLayout;
    private List<BaseDataModel> dataLabel = new ArrayList<>();
    private AddRemindModel addRemindModel = new AddRemindModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_add_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        addRemindModel.setRemindDate(getIntent().getStringExtra("dateString"));
        dataLabel.add(new BaseDataModel("0", "+ 关联客户"));
        generationLabels(this, dataLabel, flowLayoutLayout);
    }

    private void invoke() {
        addRemindModel.setRemindContent(contentEt.getText().toString());
        addRemindModel.setRemindHour(timeTv.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTSKREMIND, addRemindModel, new TypeToken<List<BaseDataModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);

        if (status == 1) {
            AppTools.getToast("添加成功");
            finish();
        }
    }

    @OnClick({R.id.cancel, R.id.commit_tv, R.id.time_tv, R.id.client_name_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.commit_tv:
                if (!TextUtils.isEmpty(contentEt.getText().toString())) {
                    invoke();
                } else {
                    AppTools.getToast("请填写内容");
                }
                break;

            case R.id.time_tv:
                AppTools.obtainTime(this, timeTv);
                break;
            case R.id.client_name_layout:
                startActivityForResult(new Intent(ScheduleAddEditActivity.this, ClientSelectActivity.class), 0x11);
                break;
        }
    }


    /**
     * label
     *
     * @param context
     * @param datas
     * @param flowLayout
     */
    public void generationLabels(final Context context, final List<BaseDataModel> datas, final FlowLayout flowLayout) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.height = DensityUtil.dip2px(context, 40);
        flowLayout.removeAllViews();

        for (int i = 0; i < datas.size(); i++) {
            final View view = LayoutInflater.from(context).inflate(R.layout.main_lable_edit_view, null);

            final TextView textView = (TextView) view.findViewById(R.id.label_tv);
            textView.setText(datas.get(i).getDictionaryName());
            view.setTag(datas.get(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseDataModel m = (BaseDataModel) v.getTag();
                    switch (m.getDictionaryId()) {
                        case "0":
                            startActivityForResult(new Intent(ScheduleAddEditActivity.this, ClientSelectActivity.class), 0x11);
                            clientNameLayout.setVisibility(View.VISIBLE);
                            upLabel(m.getDictionaryId());
                            break;

                    }
                }
            });

            view.setLayoutParams(lp);
            flowLayout.addView(view);
        }

    }

    /**
     * 更新label
     *
     * @param id
     */
    private void upLabel(String id) {

        for (int i = 0; i < dataLabel.size(); i++) {
            if (dataLabel.get(i).getDictionaryId().equals(id)) {
                dataLabel.remove(i);
                break;
            }
        }
        generationLabels(this, dataLabel, flowLayoutLayout);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            AddClientTransModel m = (AddClientTransModel) data.getSerializableExtra("model");
            addRemindModel.setCsrId(m.getCsrId());
            clientNameTv.setText(m.getCustomerName());
        }
    }
}
