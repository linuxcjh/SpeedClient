package com.rongfeng.speedclient.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientAddBusinessActivity;
import com.rongfeng.speedclient.client.ClientAddContractActivity;
import com.rongfeng.speedclient.client.ClientPersonaActivity;
import com.rongfeng.speedclient.client.ClientRegisterActivity;
import com.rongfeng.speedclient.client.ClientVisitActivity;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.voice.AddScheduleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AUTHOR: Alex
 * DATE: 26/10/2015 23:04
 */
public class SearchPopupWindow {


    @Bind(R.id.cancel_iv)
    ImageView cancelIv;
    @Bind(R.id.client_persona_layout)
    LinearLayout clientPersonaLayout;
    @Bind(R.id.client_record_layout)
    LinearLayout clientRecordLayout;
    @Bind(R.id.client_remind_layout)
    LinearLayout clientRemindLayout;
    @Bind(R.id.add_business_layout)
    RelativeLayout addBusinessLayout;
    @Bind(R.id.add_contract_layout)
    RelativeLayout addContractLayout;
    @Bind(R.id.client_name_tv)
    TextView clientNameTv;
    @Bind(R.id.add_note_layout)
    RelativeLayout addNoteLayout;
    @Bind(R.id.client_layout)
    LinearLayout clientLayout;
    @Bind(R.id.client_add_client_layout)
    LinearLayout clientAddClientLayout;
    @Bind(R.id.client_no_remind_layout)
    LinearLayout clientNoRemindLayout;
    @Bind(R.id.add_no_note_layout)
    RelativeLayout addNoNoteLayout;
    @Bind(R.id.client_no_layout)
    LinearLayout clientNoLayout;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.check_client_layout)
    RelativeLayout checkClientLayout;
    @Bind(R.id.check_business_layout)
    RelativeLayout checkBusinessLayout;
    @Bind(R.id.check_bargain_layout)
    RelativeLayout checkBargainLayout;
    @Bind(R.id.check_reback_layout)
    RelativeLayout checkRebackLayout;
    @Bind(R.id.expandable_text)
    TextView expandableText;
    @Bind(R.id.expand_collapse)
    ImageButton expandCollapse;
    @Bind(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    private View view;
    public PopupWindow mPopupWindow;
    private Context mContext;
    private int mDisplayHeight;

    private String voiceConent;
    private BaseDataModel clientInfoModel = new BaseDataModel();


    private Handler mHandler;


    private boolean isFromNoteRecord;//是否来源于笔记记录

    public SearchPopupWindow(Context context, int displayHeight) {
        this(context, displayHeight, null);
    }

    public SearchPopupWindow(Context context, int displayHeight, Handler handler) {
        this.mContext = context;
        this.mDisplayHeight = displayHeight;
        this.mHandler = handler;
    }


    public PopupWindow getPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.pop_shortcut_layout, null);
        ButterKnife.bind(this, view);

        mPopupWindow = new PopupWindow(view, ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth(), mDisplayHeight);
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return mPopupWindow;
    }

    public void setIsFromNoteRecord(boolean isFromNoteRecord) {
        this.isFromNoteRecord = isFromNoteRecord;

    }


    public void setContent(String content) {
        this.voiceConent = content;
        contentTv.setText("“ " + voiceConent + " ”");
        expandTextView.setText("“ " + voiceConent + " ”");

    }

    /**
     * 如果model 为null说明没识别到客户
     *
     * @param model
     */
    public void setSelectClient(BaseDataModel model) {

        this.clientInfoModel = model;
        if (model != null) {
            clientNameTv.setText(model.getDictionaryName());
//            clientLayout.setVisibility(View.VISIBLE);
//            clientNoLayout.setVisibility(View.GONE);

        } else {
//            clientNoLayout.setVisibility(View.VISIBLE);
//            clientLayout.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.cancel_iv, R.id.client_persona_layout, R.id.client_record_layout, R.id.client_remind_layout, R.id.add_business_layout, R.id.add_contract_layout, R.id.add_note_layout, R.id.client_add_client_layout, R.id.client_no_remind_layout, R.id.add_no_note_layout, R.id.check_client_layout, R.id.check_business_layout, R.id.check_bargain_layout, R.id.check_reback_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_iv:
                mPopupWindow.dismiss();
                break;
            case R.id.check_client_layout:
            case R.id.client_persona_layout:
                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, ClientPersonaActivity.class).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("customerName", clientInfoModel.getDictionaryName()));
                }

                break;
            case R.id.client_record_layout:
                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, ClientVisitActivity.class).putExtra("content", voiceConent).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("customerName", clientInfoModel.getDictionaryName()));
                }
                break;
            case R.id.client_no_remind_layout:
            case R.id.client_remind_layout:
                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, AddScheduleActivity.class).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("content", voiceConent));
                } else {
                    mContext.startActivity(new Intent(mContext, AddScheduleActivity.class).putExtra("content", voiceConent));
                }

                break;
            case R.id.add_business_layout:

                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, ClientAddBusinessActivity.class).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("customerName", clientInfoModel.getDictionaryName()));
                }

                break;
            case R.id.add_contract_layout:
                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, ClientAddContractActivity.class).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("customerName", clientInfoModel.getDictionaryName()));
                }

                break;
            case R.id.add_no_note_layout:
            case R.id.add_note_layout:

//                if (isFromNoteRecord) {
//                    AppTools.getToast("笔记已存在");
//                    return;
//                }
//                mHandler.sendEmptyMessage(3);
                break;
            case R.id.client_add_client_layout:

                mContext.startActivity(new Intent(mContext, ClientRegisterActivity.class).putExtra("voiceConent", voiceConent));

                break;
            case R.id.check_business_layout:
                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, ClientPersonaActivity.class).putExtra("flag", ClientPersonaActivity.CLIENT_BUSINESS_INDEX).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("customerName", clientInfoModel.getDictionaryName()));
                }
                break;
            case R.id.check_bargain_layout:
                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, ClientPersonaActivity.class).putExtra("flag", ClientPersonaActivity.CLIENT_BARGAIN_INDEX).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("customerName", clientInfoModel.getDictionaryName()));
                }
                break;
            case R.id.check_reback_layout:
                if (clientInfoModel != null) {
                    mContext.startActivity(new Intent(mContext, ClientPersonaActivity.class).putExtra("flag", ClientPersonaActivity.CLIENT_DEBT_INDEX).putExtra("customerId", clientInfoModel.getDictionaryId()).putExtra("customerName", clientInfoModel.getDictionaryName()));
                }
                break;
        }
    }

}


