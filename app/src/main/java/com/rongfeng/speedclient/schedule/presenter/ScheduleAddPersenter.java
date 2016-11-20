package com.rongfeng.speedclient.schedule.presenter;


import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.entity.PicInfoModel;
import com.rongfeng.speedclient.schedule.action.IScheduleAddAction;
import com.rongfeng.speedclient.schedule.model.RequestAddScheduleModel;

import java.util.List;

/**
 * Created by 唐磊 on 2015/12/25.
 */
public class ScheduleAddPersenter extends BasePresenter {

    private IScheduleAddAction iScheduleAddAction;

    public ScheduleAddPersenter(IScheduleAddAction iScheduleAddAction) {
        this.iScheduleAddAction = iScheduleAddAction;
    }

    @Override
    public void onResponse(String methodName, Object object, int status) {
          if(status==1){
              switch (methodName) {
                  case XxbService.UPLOADFILE:
                      PicInfoModel picInfoModel = (PicInfoModel) object;
                      if (picInfoModel != null) {
                          iScheduleAddAction.callBackUploadImage(picInfoModel.getFileId());
                      }
                      break;

              		case XxbService.INSERTSCHEDULE:
                        iScheduleAddAction.callBackAdd();
              			break;
                  case XxbService.UPDATESCHEDULE:
                      iScheduleAddAction.callBackEdit();
                      break;
              		}
          }
    }

    /**
     * 上传图片
     * @param paths
     */
    public void uploadImage(List<String> paths){
        uploadFile(paths);
    }

    /**
     * 添加日程
     */
    public void insertSchedule(RequestAddScheduleModel model){
        commonApi(XxbService.INSERTSCHEDULE, AppTools.toMap(model));
    }

    /**
     * 编辑日程
     * @param model
     */
    public void updateSchedule(RequestAddScheduleModel model){
        commonApi(XxbService.UPDATESCHEDULE, AppTools.toMap(model));
    }
}
