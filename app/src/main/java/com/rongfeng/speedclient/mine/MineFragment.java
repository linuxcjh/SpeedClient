package com.rongfeng.speedclient.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.organization.OrganizationActivity;
import com.rongfeng.speedclient.schedule.ScheduleActivity;
import com.rongfeng.speedclient.voice.VoiceNoteActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * 2016/1/13
 */
public class MineFragment extends BaseFragment {


    @Bind(R.id.contact_avatar_iv)
    ImageView contactAvatarIv;
    @Bind(R.id.re_layout)
    RelativeLayout reLayout;
    @Bind(R.id.contact_gender_iv)
    ImageView contactGenderIv;
    @Bind(R.id.sign_layout)
    RelativeLayout signLayout;
    @Bind(R.id.mine_first_name)
    TextView mineFirstName;
    @Bind(R.id.note_layout)
    LinearLayout noteLayout;
    @Bind(R.id.connect_layout)
    LinearLayout connectLayout;
    @Bind(R.id.remind_layout)
    LinearLayout remindLayout;
    @Bind(R.id.mine_first_switch_bt)
    Button mineFirstSwitchBt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mineFirstName.setText(AppTools.getUser().getUserName());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.note_layout, R.id.connect_layout, R.id.remind_layout, R.id.mine_first_switch_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_layout:
                startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
                break;
            case R.id.connect_layout:
                startActivity(new Intent(getActivity(), OrganizationActivity.class));

                break;
            case R.id.remind_layout:
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
                break;
            case R.id.mine_first_switch_bt:
                AppConfig.setStringConfig("userModel", "");
                AppConfig.setStringConfig("login", "0"); //启动页面

                getActivity().finish();
                break;
        }
    }
}
