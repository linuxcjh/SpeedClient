package com.rongfeng.speedclient.entity;

import java.util.ArrayList;

/**
 * 从通讯录选择
 */
public class ContactDetail {
	private String name;
	private String onlyPhone;
	private ArrayList<String> phones;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnlyPhone() {
		return onlyPhone;
	}

	public void setOnlyPhone(String onlyPhone) {
		this.onlyPhone = onlyPhone;
	}

	public ArrayList<String> getPhones() {
		return phones;
	}

	public void setPhones(ArrayList<String> phones) {
		this.phones = phones;
	}



}