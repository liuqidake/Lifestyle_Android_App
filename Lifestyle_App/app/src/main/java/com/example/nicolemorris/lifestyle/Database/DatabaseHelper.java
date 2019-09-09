package com.example.nicolemorris.lifestyle.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lifestyle.dp";
    public static final String PROFILE_TABLE = "profile_table";
    public static final List<String> PROFILE_TABLE_COLS = new ArrayList<>(Arrays.asList("ID","NAME","AGE","CITY",
            "STATE", "HEIGHT", "WEIGHT", "SEX"));

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+PROFILE_TABLE+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME, AGE INTEGER, " +
                "CITY, STATE, HEIGHT, WEIGHT, SEX INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PROFILE_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name, String age, String city, String state, String height, String weight, String sex){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            int ageInt = Integer.parseInt(age);
            int sexInt = sex.equals("Male") ? 1 : 0;
            contentValues.put(PROFILE_TABLE_COLS.get(0), name);
            contentValues.put(PROFILE_TABLE_COLS.get(1), ageInt);
            contentValues.put(PROFILE_TABLE_COLS.get(2), city);
            contentValues.put(PROFILE_TABLE_COLS.get(3), state);
            contentValues.put(PROFILE_TABLE_COLS.get(4), height);
            contentValues.put(PROFILE_TABLE_COLS.get(5), weight);
            contentValues.put(PROFILE_TABLE_COLS.get(6), sexInt);
            long res = db.insert(PROFILE_TABLE, null, contentValues);
            return res == -1 ? false : true;
        }catch(Exception e){
            return false;
        }
    }

}
