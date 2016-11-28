package com.rongfeng.speedclient.organization;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 26/10/2015 23:04
 */
public class SearchNewPopupWindow {


    private EditText searchEt;
    private ImageView clearBt;
    private Button cancelBt;
    private View view;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private int mDisplayHeight;
    private ListView listView;
    private List<SelectModel> listData;
    private List<SelectModel> listSearch;
//    private SearchListAdapter adapter;


    public SearchNewPopupWindow(Context context, int displayHeight) {
        this.mContext = context;
        this.mDisplayHeight = displayHeight;
    }


    public PopupWindow getPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.search_new_layout, null);
        initView();
        initListener();
        mPopupWindow = new PopupWindow(view, ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth(), mDisplayHeight);
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return mPopupWindow;
    }


    private void initView() {
        searchEt = (EditText) view.findViewById(R.id.search_et);
        clearBt = (ImageView) view.findViewById(R.id.clear_bt);
        cancelBt = (Button) view.findViewById(R.id.cancel_bt);
        listView = (ListView) view.findViewById(R.id.listView);
        searchEt.requestFocus();

    }

    private void initListener() {

        clearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPopupWindow.dismiss();
            }
        });


        searchEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
//                    search(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    clearBt.setVisibility(View.VISIBLE);
                } else {
                    clearBt.setVisibility(View.GONE);
                }
            }
        });
    }

//    private void search(String keyWord) {
//        if (listData != null && listData.size() > 0) {
//            listSearch = new ArrayList<>();
//            for (SelectModel model : listData) {
//                String name = model.getName();
//                if (name.contains(keyWord)) {
//                    listSearch.add(model);
//                }
//            }
//            adapter = new SearchListAdapter(mContext,R.layout.address_book_item, listSearch);
//            listView.setAdapter(adapter);
//        }
//
//    }


    public void setListData(List<SelectModel> listData) {
        this.listData = listData;
    }


//    class SearchListAdapter extends QuickAdapter<SelectModel> implements View.OnClickListener {
//
//        public SearchListAdapter(Context context, int layoutResId, List<SelectModel> data) {
//            super(context, layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseAdapterHelper helper, final SelectModel item, int position) {
//            ColleagueModel model= (ColleagueModel) item;
//            helper.setText(R.id.name,model.getName());
//            helper.setText(R.id.department,model.getDepartmentName());
//            helper.setText(R.id.position, TextUtils.isEmpty(model.getUserPosition())?"职员":model.getUserPosition());
//            ImageView msg= helper.getView(R.id.msg);
//            ImageView call= helper.getView(R.id.call);
//
//            msg.setOnClickListener(this);
//            call.setOnClickListener(this);
//            msg.setTag(model);
//            call.setTag(model);
//            String str=null;
//            if(!TextUtils.isEmpty(model.getUserName().trim())){
//                str=model.getUserName().trim().substring(0,1);
//            }
//            AvatarImageView image= (AvatarImageView) helper.getView(R.id.image);
//            image.setContentText(str);
//            Glide.with(context).load(model.getUserImageUrl()).transform(new GlideRoundTransform(context)).into(image);
//
//        }
//
//
//        @Override
//        public void onClick(View v) {
//            ColleagueModel model = (ColleagueModel) v.getTag();
//            String phone = model.getUserPhone();
//            switch (v.getId()) {
//                case R.id.msg:
//                    AppTools.sendSMS(context, phone);
//                    break;
//                case R.id.call:
//                    mHandler.sendMessage(mHandler.obtainMessage(0, phone));
//                    MyDialog dialog = new MyDialog(context, mHandler);
//                    dialog.buildDialog().setTitle("拨打电话").setMessage(model.getUserName() + " " + phone);
//                    break;
//            }
////            mPopupWindow.dismiss();
//        }
//    }

    Handler mHandler = new Handler() {
        String phone;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.CONFIRMDIALOG:
                    AppTools.callPhone(mContext, phone);
                    break;
                case 0:
                    phone = (String) msg.obj;
                    break;
            }
        }
    };
}



