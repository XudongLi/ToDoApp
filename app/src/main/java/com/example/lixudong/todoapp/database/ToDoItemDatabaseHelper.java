package com.example.lixudong.todoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoItemDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "ToDoAppDataBase";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_ITEM= "ToDoItems";

    // Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_TITLE = "title";
    private static final String KEY_ITEM_TEXT = "text";
    private static final String KEY_ITEM_STATUS = "status";

    public ToDoItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
}
