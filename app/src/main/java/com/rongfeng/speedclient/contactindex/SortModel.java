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

    public String isForbidden; //是否激活( 0正常使用2未激活)

    public boolean isSelected;//是否被选中


}