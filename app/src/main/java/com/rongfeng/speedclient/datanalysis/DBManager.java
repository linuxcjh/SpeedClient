package com.rongfeng.speedclient.datanalysis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 30/10/2016 14:18
 */

public class DBManager {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DatabaseHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     *
     * @param persons
     */
    public void addClient(List<ClientModel> persons) {
        db.beginTransaction();  //开始事务
        try {
            for (ClientModel person : persons) {
                db.execSQL("INSERT INTO notes VALUES(null,?,?,?,?,?,?,?)", new Object[]{person.client_id, person.client_name, person.client_info, person.client_update_time, person.contact_id, person.contact_name, person.contact_phone});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * add person
     *
     * @param person
     */
    public void addClient(ClientModel person) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO notes VALUES(null,?,?,?,?,?,?,?)", new Object[]{person.client_id, person.client_name, person.client_info, person.client_update_time, person.contact_id, person.contact_name, person.contact_phone});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 更新客户名称分词结果数据
     *
     * @param person
     */
    public void updateClient(ClientModel person) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("UPDATE  notes set "
                    + DatabaseHelper.CLIENT_NAME + "='" + person.getClient_name()
                    + "'," + DatabaseHelper.CLIENT_INFO + "='" + person.getClient_info()
                    + "'," + DatabaseHelper.CLIENT_UPDATE_TIME + "='" + person.getClient_update_time()
                    + "' WHERE " + DatabaseHelper.CLIENT_ID + "='" + person.getClient_id()
                    + "'");
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 更新客户联系人数据
     *
     * @param person
     */
    public void updateClientContact(ClientModel person) {

        db.beginTransaction();  //开始事务
        try {
            db.execSQL("UPDATE  notes set " + DatabaseHelper.CLIENT_NAME + "='" + person.getClient_name()
                    + "'," + DatabaseHelper.CLIENT_UPDATE_TIME + "='" + person.getClient_update_time()
                    + "'," + DatabaseHelper.CONTACT_NAME + "='" + person.getContact_name()
                    + "' WHERE " + DatabaseHelper.CLIENT_ID + "='" + person.getClient_id()
                    + "'");
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 删除表数据
     */
    public void truncate() {
        db.beginTransaction();  //开始事务
        try {
//            delete from TableName;  //清空数据
//            update sqlite_sequence SET seq = 0 where name ='TableName';//自增长ID为0
            db.execSQL("DELETE FROM  notes;");
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * update person's age
     *
     * @param person
     */
    public void updateAge(ClientModel person) {
        ContentValues cv = new ContentValues();
//        cv.put("age", person.age);
//        db.update("person", cv, "name = ?", new String[]{person.name});
    }

    /**
     * delete old person
     *
     * @param person
     */
    public void deleteOldPerson(ClientModel person) {
//        db.delete("person", "age >= ?", new String[]{String.valueOf(person.age)});
    }

    /**
     * query all persons, return list
     *
     * @return List<Person>
     */
    public List<ClientModel> query() {
        ArrayList<ClientModel> persons = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            ClientModel person = new ClientModel();
            person.client_id = c.getString(c.getColumnIndex("client_id"));
            person.client_name = c.getString(c.getColumnIndex("client_name"));
            person.client_info = c.getString(c.getColumnIndex("client_info"));
            person.client_update_time = c.getString(c.getColumnIndex("client_update_time"));

            person.contact_id = c.getString(c.getColumnIndex("contact_id"));
            person.contact_name = c.getString(c.getColumnIndex("contact_name"));
            person.contact_phone = c.getString(c.getColumnIndex("contact_phone"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM notes", null);
        return c;
    }


    /**
     * 根据id查找指定客户
     *
     * @param clientId
     * @return
     */
    public ClientModel queryTheClient(String clientId) {
        Cursor c = db.rawQuery("SELECT * FROM notes WHERE " + DatabaseHelper.CLIENT_ID + "='" + clientId + "'", null);
        ClientModel person = new ClientModel();
        while (c.moveToNext()) {
            person.client_id = c.getString(c.getColumnIndex("client_id"));
            person.client_name = c.getString(c.getColumnIndex("client_name"));
            person.client_info = c.getString(c.getColumnIndex("client_info"));
            person.client_update_time = c.getString(c.getColumnIndex("client_update_time"));

            person.contact_id = c.getString(c.getColumnIndex("contact_id"));
            person.contact_name = c.getString(c.getColumnIndex("contact_name"));
            person.contact_phone = c.getString(c.getColumnIndex("contact_phone"));
        }
        c.close();

        return person;
    }

    /**
     * 判断是否存在
     *
     * @param clientId
     * @return
     */
    public boolean queryTheClientCursor(String clientId) {
        Cursor c = db.rawQuery("SELECT * FROM notes WHERE " + DatabaseHelper.CLIENT_ID + "='" + clientId + "'", null);
        if (c != null && c.getCount() > 0) {
            return true;
        }
        return false;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

}
