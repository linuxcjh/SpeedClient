package com.rongfeng.speedclient.common;


/**
 * AUTHOR: Alex
 * DATE: 15/7/2016 17:16
 */
public interface ICommonPaginationAction extends ICommonAction {

    /**
     * 无更多数据提示
     */
    void noMoreData();

    /**
     * 下拉刷新完成
     */
    void refreshComplete();

    /**
     * 上拉加载完成
     */
    void onLoadComplete();

}
