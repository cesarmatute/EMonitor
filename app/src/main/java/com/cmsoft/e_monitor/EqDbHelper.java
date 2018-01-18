package com.cmsoft.e_monitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cesar on 17/01/18.
 */

public class EqDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "earthquakes.db";
    private static final int DATABASE_VERSION = 1;


    public EqDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ERATHQUAKES_DATABASE = "CREATE TABLE " + DATABASE_NAME + " (" +
                "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "magnitude" + " REAL NOT NULL," +
                "place" + " TEXT NOT NULL," +
                "longitude" + " TEXT NOT NULL," +
                "latitude" + " TEXT NOT NULL," +
                "timestamp" + " TEXT NOT NULL," +
                ")";

        sqLiteDatabase.execSQL(ERATHQUAKES_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
