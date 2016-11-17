package com.rongfeng.speedclient.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientAddrDetailsAdapter;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.ConstantPermission;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.permisson.PermissionsActivity;
import com.rongfeng.speedclient.permisson.PermissionsChecker;
import com.rongfeng.speedclient.utils.DensityUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 选择客户位置
 */
public class ClientDotOverlayMapNewActivity extends BaseActivity implements
        AMapLocationListener, AdapterView.OnItemClickListener {

    @Bind(R.id.cancel_ib)
    ImageButton cancelIb;
    @Bind(R.id.commit_tv)
    TextView commitTv;
    @Bind(R.id.bmapView)
    MapView mapView;
    @Bind(R.id.prompt)
    TextView prompt;
    @Bind(R.id.search_layout)
    RelativeLayout searchLayout;
    @Bind(R.id.container_layout)
    RelativeLayout containerLayout;

    private double longitude;// 经度
    private double latitude;// 纬度
    private String locationString;// 客户位置

    public AMap aMap;
    private GeocodeSearch geocoderSearch;

    private boolean isFirst; //第一次不用反地理编码

    @Bind(R.id.list_view)
    ListView listView;
    private ClientAddrDetailsAdapter mAdapter;
    private List<PoiItem> lists;
    private ProgressDialog progressDialog;
    private PoiSearch.Query query;
    private PoiItem poiInfo;

    private String queryKey = "地名地址信息|道路附属设施|建筑";
    private String currentCityCode = "";

    private boolean scrollFlag;// 标记是否滑动

    private boolean isUp;

    private int lastVisibleItemPosition = 0;// 标记上次滑动位置

    final LinearLayout.LayoutParams paramsUp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(AppConfig.getContext(), 100));
    final LinearLayout.LayoutParams paramsDown = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(AppConfig.getContext(), 200));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_map_dot_overlay_new_activity);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        iniMap();
        initData();

    }


    protected void initData() {

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

                if (isFirst) {
                    LatLonPoint point = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    RegeocodeQuery query = new RegeocodeQuery(point, 0, GeocodeSearch.AMAP);
                    geocoderSearch.getFromLocationAsyn(query);
                }

                isFirst = true;

            }
        });

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                longitude = regeocodeResult.getRegeocodeQuery().getPoint().getLongitude();// 经度
                latitude = regeocodeResult.getRegeocodeQuery().getPoint().getLatitude();// 纬度
                locationString = regeocodeResult.getRegeocodeAddress().getFormatAddress();// 客户位置
                prompt.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                search(regeocodeResult.getRegeocodeAddress().getCityCode(), latitude, longitude);

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });


    }

    public void iniMap() {

        progressDialog = new ProgressDialog(this);

        if (aMap == null) {
            aMap = mapView.getMap();
            String location = AppConfig.getStringConfig("location", "");
            if (!TextUtils.isEmpty(location)) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(location.split(":")[0]), Double.parseDouble(location.split(":")[1])), 17));
            } else {
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            }
        }

        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_LOCATION)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(this, ConstantPermission.PERMISSIONS_LOCATION);
        } else {
            AppTools.startLbsLocation(this, true);
        }

        mAdapter = new ClientAddrDetailsAdapter(this, R.layout.attendance_addr_details_item, lists);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnTouchListener(new View.OnTouchListener() {
            float mLastY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getRawY();
                        float deltaY = y - mLastY;

                        if (deltaY > 5 && scrollFlag) {// 向下
                            mHandler.sendEmptyMessage(1);
                        } else if (deltaY < -5) {// 向上
                            mHandler.sendEmptyMessage(0);

                        }

                        break;

                }
                return false;
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            /**
             * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
             * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
             */

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if (firstVisibleItem == 0) {
                    scrollFlag = true;

                } else {
                    scrollFlag = false;
                }

            }
        });

    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    if (!isUp) {
                        containerLayout.setLayoutParams(paramsUp);
                        isUp = true;
                    }
                    break;
                case 1:
                    if (isUp) {
                        containerLayout.setLayoutParams(paramsDown);
                        isUp = false;
                    }

                    break;
            }


        }
    };

    /**
     * 搜索兴趣点
     */
    private void search(String cityCode, double latitude, double longitude) {

        query = new PoiSearch.Query(queryKey, "", cityCode);
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,
                longitude), 1000));//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                progressDialog.dismiss();
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

        mAdapter.setShow(position);
        mAdapter.setData(lists);
        poiInfo = lists.get(position);
        isFirst = false;
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(poiInfo.getLatLonPoint().getLatitude(), poiInfo.getLatLonPoint().getLongitude()), 17));

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                longitude = aMapLocation.getLongitude();// 经度
                latitude = aMapLocation.getLatitude();// 纬度
                locationString = aMapLocation.getAddress();// 客户位置

                prompt.setText(aMapLocation.getAddress());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 17));

                poiInfo = new PoiItem("1001", new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), aMapLocation.getPoiName(), aMapLocation.getAddress());
                poiInfo.setProvinceName(aMapLocation.getProvince());
                poiInfo.setProvinceCode(aMapLocation.getProvince());
                poiInfo.setCityName(aMapLocation.getCity());
                poiInfo.setCityCode(aMapLocation.getCityCode());
                poiInfo.setAdCode(aMapLocation.getAdCode());
                poiInfo.setAdName(aMapLocation.getDistrict());
                AppConfig.setStringConfig("location", aMapLocation.getLatitude() + ":" + aMapLocation.getLongitude());

            }
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppTools.stopLbsLocation();
        mapView.onDestroy();

    }


    @OnClick({R.id.cancel_ib, R.id.commit_tv, R.id.search_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_ib:
                finish();
                break;
            case R.id.search_layout:
                Intent intentSearch = new Intent(this, ClientDotOverlayMapNewSearchActivity.class);
                startActivityForResult(intentSearch, 0x11);
                break;
            case R.id.commit_tv:
                Intent intent = new Intent();
                intent.putExtra("poiInfo", poiInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == 0x11) {
            poiInfo = (PoiItem) data.getParcelableExtra("poiInfo");
            if (poiInfo != null) {
                prompt.setText(poiInfo.getProvinceName() + " " + poiInfo.getCityName() + " " + poiInfo.getAdName() + poiInfo.getSnippet());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(poiInfo.getLatLonPoint().getLatitude(), poiInfo.getLatLonPoint().getLongitude()), 17));
                search(poiInfo.getCityCode(), poiInfo.getLatLonPoint().getLatitude(), poiInfo.getLatLonPoint().getLongitude());
            }

        }
        //拒绝定位权限退出
        if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            AppTools.startLbsLocation(this, true);
        }
    }

}
