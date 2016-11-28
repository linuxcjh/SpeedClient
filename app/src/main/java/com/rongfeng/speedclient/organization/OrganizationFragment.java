package com.rongfeng.speedclient.organization;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationFragment extends BackHandledFragment {

    @Bind(R.id.search_layout)
    RelativeLayout searchLayout;
    @Bind(R.id.client_listView)
    XRecyclerView clientListView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    private boolean hadIntercept;
    private Handler mHandler;


    public static OrganizationFragment newInstance() {
        Bundle args = new Bundle();

        OrganizationFragment fragment = new OrganizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_organization_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.search_layout)
    public void onClick() {
        mHandler.sendEmptyMessage(Constant.SHOW_SEARCH_VIEW_INDEX);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected boolean onBackPressed() {
//        Toast.makeText(getActivity(), "Click From MyFragment== " + flag, Toast.LENGTH_SHORT).show();
        return false;
//		if(hadIntercept){
//			return false;
//		}else{
//			Toast.makeText(getActivity(), "Click From MyFragment", Toast.LENGTH_SHORT).show();
//			hadIntercept = true;
//			return true;
//		}
    }
}
