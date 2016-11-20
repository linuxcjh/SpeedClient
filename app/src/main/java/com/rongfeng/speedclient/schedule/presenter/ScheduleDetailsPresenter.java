package com.rongfeng.speedclient.schedule.presenter;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.schedule.action.IScheduleDetailsAction;
import com.rongfeng.speedclient.schedule.model.ReceiveScheduleDetailsModel;
import com.rongfeng.speedclient.schedule.model.RequestScheduleDetailsModel;

/**
 * Created by 唐磊 on 2015/12/28.
 */
public class ScheduleDetailsPresenter extends BasePresenter {

    private IScheduleDetailsAction iScheduleDetailsAction;

    public ScheduleDetailsPresenter(IScheduleDetailsAction iScheduleDetailsAction) {
        this.iScheduleDetailsAction = iScheduleDetailsAction;
    }

    @Override
    public void onResponse(String methodName, Object object, int status) {

        if (status==1){

            switch (methodName) {
            		case XxbService.GETSCHEDULEINFOWITHID:
                        iScheduleDetailsAction.callBack((ReceiveScheduleDetailsModel) object);
            			break;
            		default:
            			break;
            		}
        }
    }


    public void getScheduleInfoWithId(String schedulId){
        RequestScheduleDetailsModel model=new RequestScheduleDetailsModel();
        model.setScheduleId(schedulId);
        commonApi(XxbService.GETSCHEDULEINFOWITHID, AppTools.toMap(model),new TypeToken<ReceiveScheduleDetailsModel>(){});
    }
}
