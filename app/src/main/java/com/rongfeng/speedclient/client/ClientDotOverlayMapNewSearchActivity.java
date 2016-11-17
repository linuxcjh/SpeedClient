package com.rongfeng.speedclient.client;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.AttendanceAddrDetailsAdapter;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 搜索客户位置
 */
public class ClientDotOverlayMapNewSearchActivity extends BaseActivity implements
        AdapterView.OnItemClickListener {

    @Bind(R.id.cancel_ib)
    ImageButton cancelIb;
    @Bind(R.id.left_icon)
    ImageView leftIcon;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.clear_bt)
    ImageView clearBt;
    @Bind(R.id.search_bt)
    Button searchBt;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.list_view)
    ListView listView;
    private AttendanceAddrDetailsAdapter mAdapter;
    private List<PoiItem> lists = new ArrayList<>();
    private ProgressDialog progressDialog;
    private PoiSearch.Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_map_dot_overlay_new_search_activity);
        ButterKnife.bind(this);
        iniMap();
        initData();

    }


    protected void initData() {
        progressDialog = new ProgressDialog(this);


    }

    public void iniMap() {

        openKeyboard(new Handler(), 200);
        mAdapter = new AttendanceAddrDetailsAdapter(this, R.layout.attendance_addr_details_item, lists);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

    }


    /**
     * 搜索兴趣点
     */
    private void search(String queryKey) {
        progressDialog.show();
        query = new PoiSearch.Query(queryKey, "", "");
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(this, query);
//        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,
//                longitude), 1000));//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                AppTools.hideKeyboard(searchEt);
                progressDialog.dismiss();
                lists.clear();
                lists = poiResult.getPois();
                mAdapter.setData(lists);

            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });//设置数据返回的监听器
        poiSearch.searchPOIAsyn();//开始搜索
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) view.findViewById(R.id.selected);
        imageView.setVisibility(View.VISIBLE);
        PoiItem poiInfo = lists.get(position);
        Intent intent = new Intent();
        intent.putExtra("poiInfo", poiInfo);
        setResult(RESULT_OK, intent);
        finish();

    }


    @OnClick({R.id.cancel_ib, R.id.search_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_ib:
                finish();
                break;
            case R.id.search_bt:
                if (!TextUtils.isEmpty(searchEt.getText().toString())) {
                    search(searchEt.getText().toString());
                } else {
                    AppTools.getToast("请输入地址关键字");
                }
                break;
        }
    }

    /**
     * 弹出键盘
     *
     * @param mHandler
     * @param s
     */
    private void openKeyboard(Handler mHandler, int s) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, s);
    }
}
