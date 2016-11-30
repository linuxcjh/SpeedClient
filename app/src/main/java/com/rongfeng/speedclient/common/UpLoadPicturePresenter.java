package com.rongfeng.speedclient.common;

import android.app.Activity;

import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.entity.PicInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 【图片上传】
 * AUTHOR: Alex
 * DATE: 22/10/2015 10:57
 */
public class UpLoadPicturePresenter extends BasePresenter {


    public Activity mActivity;
    /**
     * 上传图片返回ID
     */
    public List<PicInfoModel> picIds = new ArrayList<>();

    /**
     * 本地图片路径
     */
    public List<String> paths = new ArrayList<>();


    public IUpLoadPictureAction iUpLoadPictureAction;

    public UpLoadPicturePresenter(IUpLoadPictureAction iUpLoadPictureAction) {
        this.iUpLoadPictureAction = iUpLoadPictureAction;
    }

    public UpLoadPicturePresenter(Activity mActivity, IUpLoadPictureAction iUpLoadPictureAction) {
        this.mActivity = mActivity;
        this.iUpLoadPictureAction = iUpLoadPictureAction;
    }

    @Override
    public void onResponse(String methodName, Object object, int status) {

        switch (methodName) {
            case XxbService.UPLOADFILE://图片上传
                if (object != null) {
                    PicInfoModel modelRe = (PicInfoModel) object;
                    picIds.add(modelRe);
                    iUpLoadPictureAction.obtainFileId(picIds.size());
                }
                break;
        }
    }
}
