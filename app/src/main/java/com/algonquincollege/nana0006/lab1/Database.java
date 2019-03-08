package com.algonquincollege.nana0006.lab1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MessageTable.db";
    public static final String TABLE_MESSAGE = "MessageTable";
    public static final int DATABASE_VERSION = 1;


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_MINE = "mine";


    private static final String DATABASE_CREATE = "create table "
            + TABLE_MESSAGE +
            "( " + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_MESSAGE
            + " text not null, " + COLUMN_MINE + " integer not null );";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }
}
