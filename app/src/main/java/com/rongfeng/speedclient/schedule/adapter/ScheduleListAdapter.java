package com.rongfeng.speedclient.schedule.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.components.SwipeView;
import com.rongfeng.speedclient.schedule.ScheduleActivity;
import com.rongfeng.speedclient.schedule.ScheduleDetailsActivity;
import com.rongfeng.speedclient.schedule.model.ReceiveScheduleItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ScheduleListAdapter extends QuickAdapter<ReceiveScheduleItemModel> implements SwipeView.OnSwipeStatusChangeListener {

    private List<ReceiveScheduleItemModel> data;
    private String dateString;

    public ScheduleListAdapter(Context context, int layoutResId, List<ReceiveScheduleItemModel> data) {
        super(context, layoutResId, data);
        this.data = data;
    }


    @Override
    protected void convert(BaseAdapterHelper helper, final ReceiveScheduleItemModel item, final int position) {
        helper.setText(R.id.content, item.getContent());
        helper.setText(R.id.startTime, item.getStartTime());
        helper.setText(R.id.endTime, item.getEndTime());
        helper.setText(R.id.customerName, item.getCustomerName());
        ImageView imageView=  helper.getView(R.id.flag1);

        SwipeView swipeView = helper.getView(R.id.swipeview);
        swipeView.setOnSwipeStatusChangeListener(this);
        helper.getView(R.id.content_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExistUnClosed()) {
                    closeAllSwipeView();
                }
                Intent intent = new Intent(context, ScheduleDetailsActivity.class);
                intent.putExtra("dateString", dateString);
                intent.putExtra("scheduleId", item.getScheduleId());
                ((ScheduleActivity)context).startActivityForResult(intent,1001);
            }
        });

        helper.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((ScheduleActivity)context).deleteItem(position);
            }
        });

        if("1".equals(item.getScheduleState())){
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.schedule_mark_finished));
        }else{
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.schedule_default));
        }
    }

    public ArrayList<SwipeView> unClosedSwipeViews = new ArrayList<SwipeView>();

    /**
     * 是否存在未关闭的条目
     */
    public boolean isExistUnClosed() {
        return unClosedSwipeViews.size() > 0;
    }

    @Override
    public void onClose(SwipeView swipeView) {
        unClosedSwipeViews.remove(swipeView);
    }

    @Override
    public void onOpen(SwipeView swipeView) {
        if (!unClosedSwipeViews.contains(swipeView)) {
            closeAllSwipeView();
            unClosedSwipeViews.add(swipeView);
        }
    }

    @Override
    public void onSwiping(SwipeView swipeView) {
        if (!unClosedSwipeViews.contains(swipeView)) {
            closeAllSwipeView();
        }
    }

    public void closeAllSwipeView() {
        for (SwipeView swipeView : unClosedSwipeViews) {
            swipeView.close();
        }
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
