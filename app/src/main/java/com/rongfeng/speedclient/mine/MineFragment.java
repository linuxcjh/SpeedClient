package com.rongfeng.speedclient.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
