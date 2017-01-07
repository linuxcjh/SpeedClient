package com.rongfeng.speedclient.version;

/**
 * 
 *
 * @author: 陈建辉
 * 
 * @time : CREAT AT 2013-9-5 下午2:46:50
 * 
 * @TODO : 【版本更新数据】
 * 
 */
public class VersionModel {

	private String versionNum; // 版本号
	private String versionName; // 版本名称
	private String versionDownUrl; // 更新URL
	private String createTime;// 版本更新日期
	private String versionContent;//更新内容
	private String isPermissionUpdate;//是否有更新权限
	private String limitVersion;
	private String limitTips;
	private String betaVersion;// 灰度版本号
	private String betaContent;// 灰度版本提示信息
	private String betaUrl;// 灰度版本下载URL


	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionDownUrl() {
		return versionDownUrl;
	}

	public void setVersionDownUrl(String versionDownUrl) {
		this.versionDownUrl = versionDownUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVersionContent() {
		return versionContent;
	}

	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}

	public String getIsPermissionUpdate() {
		return isPermissionUpdate;
	}

	public void setIsPermissionUpdate(String isPermissionUpdate) {
		this.isPermissionUpdate = isPermissionUpdate;
	}

	public String getLimitVersion() {
		return limitVersion;
	}

	public void setLimitVersion(String limitVersion) {
		this.limitVersion = limitVersion;
	}

	public String getLimitTips() {
		return limitTips;
	}

	public void setLimitTips(String limitTips) {
		this.limitTips = limitTips;
	}

	public String getBetaVersion() {
		return betaVersion;
	}

	public void setBetaVersion(String betaVersion) {
		this.betaVersion = betaVersion;
	}

	public String getBetaContent() {
		return betaContent;
	}

	public void setBetaContent(String betaContent) {
		this.betaContent = betaContent;
	}

	public String getBetaUrl() {
		return betaUrl;
	}

	public void setBetaUrl(String betaUrl) {
		this.betaUrl = betaUrl;
	}
}
