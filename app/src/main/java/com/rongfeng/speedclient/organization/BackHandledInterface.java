package com.rongfeng.speedclient.organization;

import com.rongfeng.speedclient.organization.model.OrganizationReceivedModel;

public interface BackHandledInterface {

	public abstract void setSelectedFragment(BackHandledFragment selectedFragment);
	public abstract void editDepartment(OrganizationReceivedModel organizationInfoModel);

}
