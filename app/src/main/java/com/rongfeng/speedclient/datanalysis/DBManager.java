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
    public void add(List<ClientModel> persons) {
        db.beginTransaction();  //开始事务
        try {
            for (ClientModel person : persons) {
                db.execSQL("INSERT INTO notes VALUES(null,?,?,?,?,?)", new Object[]{person.client_id, person.client_name, person.contact_id, person.contact_name, person.contact_phone});
            }
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
            db.execSQL("TRUNCATE TABLE notes");
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
     * close database
     */
    public void closeDB() {
        db.close();
    }

}
