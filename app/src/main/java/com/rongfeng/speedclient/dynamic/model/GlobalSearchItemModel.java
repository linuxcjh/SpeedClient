package com.rongfeng.speedclient.dynamic.model;

/**
 * AUTHOR: Alex
 * DATE: 4/1/2017 15:03
 */

public class GlobalSearchItemModel {

    private String id;
    private String title;
    private String globalType;//1 客户2 联系人3商4成交5跟进6日志

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGlobalType() {
        return globalType;
    }

    public void setGlobalType(String globalType) {
        this.globalType = globalType;
    }
}
