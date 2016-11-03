package com.rongfeng.speedclient.utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * AUTHOR: Alex
 * DATE: 3/11/2016 14:06
 */

public class AppTools {


    public static String convertPinYin(String content) {
        //把汉字转串化拼音串
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < content.length(); j++) {
            char c = content.charAt(j);
            String[] vals;
            try {
                vals = PinyinHelper.toHanyuPinyinStringArray(c, format);

                if (vals == null) {
                    stringBuffer.append("null");
                    continue;
                }
                if (!TextUtils.isEmpty(vals[0]) && vals[0].length() > 1) {
                    vals[0] = vals[0].substring(0, vals[0].length() - 1);
                }
                stringBuffer.append(vals[0]);

            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        String key = stringBuffer.toString();
        return key;
    }

}
