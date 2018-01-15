package com.example.admin.safetyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 6/22/2017.
 */

public class SqliteDatabase extends SQLiteOpenHelper {
    public static String createQ="CREATE TABLE exam"+ "(" +"couseId VARCHAR PRIMARY KEY"+","+"courseName VARCHAR"+","+"date VARCHAR"+ ","+"time VARCHAR"+","+"venue VARCHAR"+","+"semester VARCHAR"+","+"department VARCHAR"+","+"course VARCHAR"+","+"indivigilatorId VARCHAR"+")";
    public static final String DATABASE_NAME = "safety.db";
    public static final int DATABASE_VERSION = 1;


    public SqliteDatabase(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("creation","database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("on create","of SqliteDatabase");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
