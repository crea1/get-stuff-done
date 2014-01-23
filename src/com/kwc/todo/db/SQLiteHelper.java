package com.kwc.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Marius Kristensen
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_TODOITEM = "todo_item";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DETAILS = "details";
    public static final String COLUMN_CREATED_DATE = "created_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_PARENT_ID = "parent_id";

    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 3;
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table " + TABLE_TODOITEM
            + " (" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_DETAILS + " text, "
            + COLUMN_CREATED_DATE + " text not null, "
            + COLUMN_END_DATE + " text, "
            + COLUMN_PARENT_ID + " integer, "
            + "FOREIGN KEY(" + COLUMN_PARENT_ID + ") REFERENCES " + TABLE_TODOITEM + "(" + COLUMN_ID + ")"
            + ");";



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(SQLiteHelper.class.getName(), "Creating database " + DB_NAME);
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading db from " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOITEM);
        onCreate(db);
    }
}
