package com.rongfeng.speedclient.contactindex;

import java.io.Serializable;

public class SortModel extends Contact implements Serializable {

    public SortModel() {
    }

    public SortModel(String name, String number, String sortKey) {
        super(name, number, sortKey);
    }

    public String sortLetters; //显示数据拼音的首字母

    public SortToken sortToken = new SortToken();

    public int position;

    public boolean isExist;
}
