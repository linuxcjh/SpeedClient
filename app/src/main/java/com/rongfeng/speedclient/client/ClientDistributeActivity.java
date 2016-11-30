package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.RecievedClientTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客户分布
 * AUTHOR: Alex
 * DATE: 15/12/2015 15:41
 */

public class ClientDistributeActivity extends BaseActivity {

    @Bind(R.id.back_bt)
    ImageView backBt;
    @Bind(R.id.mapView)
    MapView mapView;


    public LatLng currentLatLng;//当前位置

    public Marker marker;
    public String address;

    public AMap aMap;
    private RecievedClientTransModel recievedClientTransModel = new RecievedClientTransModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_map_distribute_overlay_activity);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        iniMap();
        initData();

    }


    public void initData() {

        recievedClientTransModel = (RecievedClientTransModel) getIntent().getSerializableExtra("model");

        if (TextUtils.isEmpty(recievedClientTransModel.getLatitude()) && TextUtils.isEmpty(recievedClientTransModel.getLongitude())) {
            AppTools.getToast("暂无地址");
        } else {
            if (recievedClientTransModel.getCustomerAddress().length() > 15) {
                address = recievedClientTransModel.getCustomerAddress().substring(0, 14) + "\n" + recievedClientTransModel.getCustomerAddress().substring(14, recievedClientTransModel.getCustomerAddress().length());
            } else {
                address = recievedClientTransModel.getCustomerAddress();
            }

            currentLatLng = new LatLng(Double.parseDouble(recievedClientTransModel.getLatitude()), Double.parseDouble(recievedClientTransModel.getLongitude()));
            marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(currentLatLng).title("当前位置").icon(BitmapDescriptorFactory.fromResource(R.drawable.map_location))
                    .snippet(address));

            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));
            marker.showInfoWindow();
        }


    }

    public void iniMap() {

        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }


    }

    private void setUpMap() {

        // 设置点击marker事件监听器
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                } else {
                    marker.showInfoWindow();
                }
                return false;
            }
        });

        // 设置点击infoWindow事件监听器
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });

        // 设置自定义InfoWindow样式
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.map_popu_layout, null);
                render(marker, view);
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.map_popu_layout, null);
                render(marker, view);
                return view;
            }
        });

    }

    /**
     * 自定义infoWindow窗口
     */
    public void render(Marker marker, View view) {
        TextView addr = (TextView) view.findViewById(R.id.addr);
        addr.setText(marker.getSnippet());

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

    @OnClick(R.id.back_bt)
    public void onClick() {
        finish();

    }
}
