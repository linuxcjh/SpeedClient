package com.rongfeng.speedclient.datanalysis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DataBase
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "note.db";

    public static final String TABLE_NAME = "notes";

    public static final int DATABASE_VERSION = 1;

    public static final String _ID = "_id";

    public static final String CLIENT_NAME = "client_name";
    public static final String CLIENT_ID = "client_id";


    public static final String CONTACT_NAME = "contact_name";
    public static final String CONTACT_PHONE = "contact_phone";
    public static final String CONTACT_ID = "contact_id";


    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY,"
            + CLIENT_ID + " VARCHAR(100),"
            + CLIENT_NAME + " VARCHAR(100),"
            + CONTACT_ID + " VARCHAR(100),"
            + CONTACT_NAME + " VARCHAR(100),"
            + CONTACT_PHONE + " VARCHAR(50)"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SYNC", "create table: " + SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }
}