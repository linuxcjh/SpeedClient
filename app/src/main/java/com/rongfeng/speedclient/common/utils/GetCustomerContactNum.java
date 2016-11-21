package com.rongfeng.speedclient.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.rongfeng.speedclient.entity.ContactDetail;

import java.util.ArrayList;

public class GetCustomerContactNum {
    private Activity activity;
    private Handler handler;
    private int sendTag;

    public GetCustomerContactNum(Activity activity, Handler handler, int sendTag) {
        this.activity = activity;
        this.handler = handler;
        this.sendTag = sendTag;
    }

    private ContactDetail getCustomerPhone(Intent data) {
        ContactDetail contact = new ContactDetail();
        ContentResolver cr = activity.getContentResolver();
        Cursor cursor = cr.query(data.getData(), null, null, null, null);
        while (cursor.moveToNext()) {
            // 取得联系人名字
            int nameFieldColumnIndex = cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            contact.setName(cursor.getString(nameFieldColumnIndex));

            // 取得联系人ID
            int id = cursor.getInt(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                    new String[]{Integer.toString(id)}, null);// 再类ContactsContract.CommonDataKinds.Phone中根据查询相应id联系人的所有电话；

            // 取得电话号码(可能存在多个号码)
            ArrayList<String> contactList = new ArrayList<String>();
            while (phone.moveToNext()) {
                String strPhoneNumber = phone
                        .getString(phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(strPhoneNumber);
            }
            contact.setPhones(contactList);
            phone.close();
        }


        cursor.close();
        return contact;
    }

    public void getPhone(Intent data) {

        try {
            ContactDetail detail = getCustomerPhone(data);
                if (detail.getPhones().size() == 1) {
                    detail.setOnlyPhone(detail.getPhones().get(0));
                    handler.sendMessage(handler.obtainMessage(sendTag, detail));
                }
                if (detail.getPhones().size() > 1) {
                    creatSelectPhones(detail);
                }
        } catch (Exception e) {


//            if (e.getMessage().contains("android.permission.READ_CONTACTS")) {
            Toast.makeText(AppConfig.getContext(), "请开启读取联系人权限", Toast.LENGTH_SHORT).show();
            AppTools.showNoSetDlg(activity, "是否打开读取联系人权限？");
//            }
        }
    }

    private void creatSelectPhones(final ContactDetail detail) {
        final String[] phones = new String[detail.getPhones().size()];
        for (int i = 0; i < detail.getPhones().size(); i++) {
            phones[i] = detail.getPhones().get(i);
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(detail.getName());
//		dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.setSingleChoiceItems(phones, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        detail.setOnlyPhone(phones[which]);
                        handler.sendMessage(handler.obtainMessage(sendTag,
                                detail));
                        dialog.dismiss();
                    }
                });
        dialog.setNegativeButton("取消", null);
        dialog.show();


    }
}