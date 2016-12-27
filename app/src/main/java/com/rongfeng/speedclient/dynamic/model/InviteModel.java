package com.rongfeng.speedclient.dynamic.model;

/**
 * AUTHOR: Alex
 * DATE: 27/12/2016 11:26
 */

public class InviteModel {

    private String inviteeUserId;
    private String inviteeUserName;
    private String inviteeUserPhone;
    private String isForbidden;

    public String getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(String isForbidden) {
        this.isForbidden = isForbidden;
    }

    public String getInviteeUserId() {
        return inviteeUserId;
    }

    public void setInviteeUserId(String inviteeUserId) {
        this.inviteeUserId = inviteeUserId;
    }

    public String getInviteeUserName() {
        return inviteeUserName;
    }

    public void setInviteeUserName(String inviteeUserName) {
        this.inviteeUserName = inviteeUserName;
    }

    public String getInviteeUserPhone() {
        return inviteeUserPhone;
    }

    public void setInviteeUserPhone(String inviteeUserPhone) {
        this.inviteeUserPhone = inviteeUserPhone;
    }
}
