package com.rongfeng.speedclient.voice.model;

/**
 * AUTHOR: Alex
 * DATE: 26/11/2016 16:30
 */

public class VoiceNoteModel {

    private String noteId;//笔记ID
    private String noteContent;//内容
    private String createTime;//创建时间

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
