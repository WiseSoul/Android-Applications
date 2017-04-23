package com.example.movieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Witch on 4/17/2017.
 */

class LoginSystemDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Accounts Database";
    private static final int DB_VERSION = 2;

    LoginSystemDatabaseHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ACCOUNTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "EMAIL TEXT, "
                + "PASSWORD TEXT, "
                + "PREFERENCE TEXT, "
                + "BIRTHDAY TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE SIGNED_IN (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "CHECKBOX TEXT);");
    }
}
