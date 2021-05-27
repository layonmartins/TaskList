package com.layon.myapplication.tasklist.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NAME_DB = "DB_TASK";
    public static String TABLE_TASK = "Tasks";

    public DbHelper(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("layon.f", "onCreate()");

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " name TEXT NOT NULL ) ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Success when create the table");
        } catch (Exception e) {
            Log.i("INFO DB", "ERROR when create the table " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("layon.f", "onUpgrade()");

        String sql = "DROP TABLE IF EXISTS " + TABLE_TASK + " ;";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Success when update the App");
        } catch (Exception e) {
            Log.i("INFO DB", "ERROR when update the App " + e.getMessage());
        }

    }
}
