package com.example.studenttimetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimetableDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "stdb.db";
    private static final int DATABASE_VERSION = 2;

    public TimetableDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Define your table creation query here
        String createTableQuery = "CREATE TABLE stdb (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id TEXT, " +
                "time TEXT, " +
                "day TEXT, " +
                "subject TEXT,"+
                "info TEXT);";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
        if (oldVersion < 2) {
            // Example: Alter table to add "info" column
            db.execSQL("ALTER TABLE stdb ADD COLUMN info TEXT");
        }
    }


    // Implement CRUD operations here
    // ...
}
