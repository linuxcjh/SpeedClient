package com.rongfeng.speedclient.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * AUTHOR: Alex
 * DATE: 15/7/2016 17:11
 */
public class CommonPaginationPresenter extends CommonPresenter {

    /**
     * Page number
     */
    public int page;

    /**
     * Refresh status
     */
    public boolean isRefresh;

    /**
     * 接口返回值包含list
     */
    public Object resultModel = new Object();

    /**
     * 分页列表(接口返回值list)
     */
    public List<Object> data = new ArrayList<>();

    public ICommonPaginationAction iCommonPaginationAction;


    public CommonPaginationPresenter(ICommonPaginationAction iCommonPaginationAction) {
        super(iCommonPaginationAction);
        this.iCommonPaginationAction = iCommonPaginationAction;

    }


    @Override
    public void onResponse(String methodName, Object object, int status) {

        if (object != null) {
            switch (methodName) {
//                case XxbService.SEARCHCUSTOMERLISTCOUNT:
//                    resultModel = object;
//                    disposeData(((ClientReceivedTotalModel) object).getCustomerList(), methodName,status);
//                    break;
                default: //直接返回list
                    disposeData(object, methodName,status);
                    break;

            }
        }

    }


    /**
     * 处理分页数据
     *
     * @param object
     * @param methodName
     */
    public void disposeData(Object object, String methodName,int status) {


        List<Object> resultData = (List<Object>) object;
        if (isRefresh) {
            data.clear();
        }
        data.addAll((List<Object>) object);
        iCommonPaginationAction.obtainData(data, methodName,status);
        if (isRefresh) {
            //下拉刷新完成
            iCommonPaginationAction.refreshComplete();
        } else {
            //上拉加载完成
            iCommonPaginationAction.onLoadComplete();
        }
        if (data.size() > 0 && resultData.size() == 0) {//没有更多数据
            iCommonPaginationAction.noMoreData();
        }
    }
}
