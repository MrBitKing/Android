package com.algonquincollege.nana0006.lab1;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DatabaseHelper {

    // Database fields
    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] allColumns = {Database.COLUMN_ID,
            Database.COLUMN_MESSAGE, Database.COLUMN_MINE};

    public DatabaseHelper(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void read() throws SQLException {     //////check ////
        database = dbHelper.getReadableDatabase();
    }

    public void write() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Message createMessage(String message, int mine) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_MESSAGE, message);
        values.put(Database.COLUMN_MINE, mine);

        long insertId = database.insert(Database.TABLE_MESSAGE, null,
                values);

        Cursor cursor = database.query(Database.TABLE_MESSAGE,
                allColumns, Database.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        Message newMessage = cursorToMessage(cursor);
        cursor.close();
        return newMessage;
    }

    public void deleteMessage(Message message) {
        long id = message.getId();
        System.out.println("Message deleted with id: " + id);
        database.delete(Database.TABLE_MESSAGE, Database.COLUMN_ID
                + " = " + id, null);
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<Message>();

        Cursor cursor = database.query(Database.TABLE_MESSAGE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    public Message getMessage(int position) {
        Message message = new Message();
        int temp = 0;

        Cursor cursor = database.query(Database.TABLE_MESSAGE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            message = cursorToMessage(cursor);
            message.setContent(message.getContent());
            message.setIsMine(message.getIsMine());
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return message;
    }


    private Message cursorToMessage(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getInt(0));
        message.setContent(cursor.getString(1));
        message.setIsMine(cursor.getInt(2));

        Log.d(TAG, "message Id " + message.getId());
        Log.d(TAG, "Message content " + message.getContent());
        Log.d(TAG, "Is mine ? " + message.getIsMine());

        Log.d(TAG, ("number of column " + cursor.getColumnCount()));
        Log.d(TAG, ("version " + Database.DATABASE_VERSION));
//        Log.d(TAG, ("column name "+cursor.getColumnName(cursor.getPosition())));
        //  Log.d(TAG, ("cursor Id " + cursor.toString()));

        return message;
    }
}

