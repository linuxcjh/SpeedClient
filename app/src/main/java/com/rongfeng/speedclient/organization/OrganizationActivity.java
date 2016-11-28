package com.rongfeng.speedclient.organization;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.common.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationActivity extends FragmentActivity implements BackHandledInterface {

    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.container_layout)
    FrameLayout containerLayout;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.title_tv)
    TextView titleTv;
    private BackHandledFragment mBackHandedFragment;
    private boolean hadIntercept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_layout);
        ButterKnife.bind(this);
        setFragment();
    }


    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.ADD_FRAGMENT_REPEAT_INDEX:
                    setFragment();

                    break;
                case Constant.SHOW_SEARCH_VIEW_INDEX:
                    setPopSearch();
                    break;
            }
        }

    };

    private void setPopSearch() {
        SearchNewPopupWindow searchPopupWindow = new SearchNewPopupWindow(this, Utils.getDecorViewHeightPixels(this) - 1);
        searchPopupWindow.getPopupWindow().showAsDropDown(view);
//        searchPopupWindow.setListData(fragment.list);
    }


    private void setFragment() {
        OrganizationFragment fragment = OrganizationFragment.newInstance();
        fragment.setHandler(mHandler);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_layout, fragment);
        ft.addToBackStack("tag");
        ft.commit();
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                super.onBackPressed();
                finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                onBackPressed();
                break;
            case R.id.commit_tv:
                break;
        }
    }
}
