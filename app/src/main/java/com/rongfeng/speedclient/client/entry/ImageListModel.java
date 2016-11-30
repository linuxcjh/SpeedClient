package com.rongfeng.speedclient.client.entry;

import java.io.Serializable;

/**
 * Created by 唐磊 on 2015/11/26.
 * 接收图片实体类
 */
public class ImageListModel implements Serializable{


   private String fileUrl;//原图http路径
   private String minUrl;//缩略图http路径
    private String fileId;//

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMinUrl() {
        return minUrl;
    }

    public void setMinUrl(String minUrl) {
        this.minUrl = minUrl;
    }
}
