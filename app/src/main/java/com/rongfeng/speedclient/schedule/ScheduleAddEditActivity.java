package com.rongfeng.speedclient.schedule;

import android.app.ActionBar;
import android.content.Context;
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
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
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
    @Bind(R.id.one_tv)
    TextView oneTv;
    @Bind(R.id.one_select_tv)
    TextView oneSelectTv;
    @Bind(R.id.two_tv)
    TextView twoTv;
    @Bind(R.id.two_select_tv)
    TextView twoSelectTv;
    @Bind(R.id.three_tv)
    TextView threeTv;
    @Bind(R.id.three_select_tv)
    TextView threeSelectTv;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;
    @Bind(R.id.start_time_tv)
    TextView startTimeTv;
    @Bind(R.id.start_time_layout)
    LinearLayout startTimeLayout;
    @Bind(R.id.end_time_tv)
    TextView endTimeTv;
    @Bind(R.id.end_time_layout)
    LinearLayout endTimeLayout;
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
        addRemindModel.setRemindType("1");
        addRemindModel.setRemindDate(getIntent().getStringExtra("dateString"));
        dataLabel.add(new BaseDataModel("0", "+ 起止时间"));
        generationLabels(this, dataLabel, flowLayoutLayout);
    }

    private void invoke() {
        addRemindModel.setRemindContent(contentEt.getText().toString());
        addRemindModel.setStartHour(startTimeTv.getText().toString());
        addRemindModel.setEndHour(endTimeTv.getText().toString());
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


    @OnClick({R.id.cancel, R.id.commit_tv, R.id.one_tv, R.id.two_tv, R.id.three_tv, R.id.start_time_layout, R.id.end_time_layout})
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
            case R.id.one_tv:
                resetTopView();
                oneSelectTv.setVisibility(View.VISIBLE);
                addRemindModel.setRemindType("1");

                break;
            case R.id.two_tv:
                resetTopView();
                twoSelectTv.setVisibility(View.VISIBLE);
                addRemindModel.setRemindType("3");

                break;
            case R.id.three_tv:
                resetTopView();
                threeSelectTv.setVisibility(View.VISIBLE);
                addRemindModel.setRemindType("7");

                break;
            case R.id.start_time_layout:
                AppTools.obtainTime(this, startTimeTv);
                break;
            case R.id.end_time_layout:
                AppTools.obtainTime(this, endTimeTv);

                break;
        }
    }

    private void resetTopView() {
        oneSelectTv.setVisibility(View.GONE);
        twoSelectTv.setVisibility(View.GONE);
        threeSelectTv.setVisibility(View.GONE);

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
                            startTimeLayout.setVisibility(View.VISIBLE);
                            endTimeLayout.setVisibility(View.VISIBLE);
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
}
