package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayDataActivity extends AppCompatActivity {
    private TimetableDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        dbHelper = new TimetableDbHelper(this);
        // Retrieve and display data here
        loadAndDisplayData();
    }

    private void loadAndDisplayData() {
        // Retrieve data from the database
        ArrayList<String> dataList = getDataList();

        // Update UI elements with the fetched data
        updateUI(dataList);
    }

    private ArrayList<String> getDataList() {
        // Fetch data from the database or any other source
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> dataList = new ArrayList<>();

        try {
            String[] projection = {"id", "time", "day","info", "subject"};
            Cursor cursor = db.query("stdb", projection, null, null, null, null, null);

            while (cursor.moveToNext()) {
                // Get data from the cursor and add it to the list
                String data = "\nCODE:\n" + cursor.getString(cursor.getColumnIndex("id")) +
                        "\nTIME:\n" + cursor.getString(cursor.getColumnIndex("time")) +
                        "\nDAY:\n" + cursor.getString(cursor.getColumnIndex("day")) +
                        "\nNEW SUB:\n" + cursor.getString(cursor.getColumnIndex("subject"))+
                        "\nINFO:\n"+cursor.getString(cursor.getColumnIndex("info"));

                dataList.add(data);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Exception: " + e.getMessage());
        } finally {
            db.close();
        }

        return dataList;
    }

    private void updateUI(ArrayList<String> dataList) {
        // Update TextViews or other UI elements with the fetched data
        LinearLayout layout = findViewById(R.id.showDataLayout);
        for (String data : dataList) {
            TextView textView = new TextView(this);
            textView.setText(data);
            textView.setTextSize(25);
            layout.addView(textView);
        }

    }

}