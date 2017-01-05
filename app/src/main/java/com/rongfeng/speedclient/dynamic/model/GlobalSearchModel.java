package com.rongfeng.speedclient.dynamic.model;

import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 4/1/2017 15:03
 */

public class GlobalSearchModel {

    private List<GlobalSearchItemModel> csrJsonArray;
    private List<GlobalSearchItemModel> csrcontactJsonArray;//联系人
    private List<GlobalSearchItemModel> csrbusinessJsonArray;//商机
    private List<GlobalSearchItemModel> csrconJsonArray;//成交
    private List<GlobalSearchItemModel> followUpJsonArray;//跟进
    private List<GlobalSearchItemModel> noteJsonArray;//日志

    public List<GlobalSearchItemModel> getCsrJsonArray() {
        return csrJsonArray;
    }

    public void setCsrJsonArray(List<GlobalSearchItemModel> csrJsonArray) {
        this.csrJsonArray = csrJsonArray;
    }

    public List<GlobalSearchItemModel> getCsrcontactJsonArray() {
        return csrcontactJsonArray;
    }

    public void setCsrcontactJsonArray(List<GlobalSearchItemModel> csrcontactJsonArray) {
        this.csrcontactJsonArray = csrcontactJsonArray;
    }

    public List<GlobalSearchItemModel> getCsrbusinessJsonArray() {
        return csrbusinessJsonArray;
    }

    public void setCsrbusinessJsonArray(List<GlobalSearchItemModel> csrbusinessJsonArray) {
        this.csrbusinessJsonArray = csrbusinessJsonArray;
    }

    public List<GlobalSearchItemModel> getCsrconJsonArray() {
        return csrconJsonArray;
    }

    public void setCsrconJsonArray(List<GlobalSearchItemModel> csrconJsonArray) {
        this.csrconJsonArray = csrconJsonArray;
    }

    public List<GlobalSearchItemModel> getFollowUpJsonArray() {
        return followUpJsonArray;
    }

    public void setFollowUpJsonArray(List<GlobalSearchItemModel> followUpJsonArray) {
        this.followUpJsonArray = followUpJsonArray;
    }

    public List<GlobalSearchItemModel> getNoteJsonArray() {
        return noteJsonArray;
    }

    public void setNoteJsonArray(List<GlobalSearchItemModel> noteJsonArray) {
        this.noteJsonArray = noteJsonArray;
    }
}
