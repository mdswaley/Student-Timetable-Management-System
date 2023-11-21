package com.example.studenttimetable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TableLayout timetableTable;
    private EditText idEditText, timeEditText, dayEditText, subjectEditText, info;
    private Button saveButton,nextPageButton,btnview;
    private ArrayList<String> dataList = new ArrayList<>();


    private TimetableDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TimetableDbHelper(this);

        timetableTable = findViewById(R.id.studentTimeTableLayout);
        idEditText = findViewById(R.id.idedit);
        timeEditText = findViewById(R.id.timeedit);
        dayEditText = findViewById(R.id.dayedit);
        subjectEditText = findViewById(R.id.subedit);
        info=findViewById(R.id.information);
        saveButton = findViewById(R.id.btn);
//        nextPageButton = findViewById(R.id.nextPageButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTimetableEntry();
            }
        });
        btnview=findViewById(R.id.btnView);
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAndDisplayData();
            }
        });


//        nextPageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showData();
//            }
//        });
    }
    private void loadAndDisplayData() {
        // Retrieve data from the database
        ArrayList<String> dataList = getDataList();

        // Update UI elements with the fetched data
        showDataInAlertDialog(dataList);
    }
    private ArrayList<String> getDataList() {
        // Fetch data from the database or any other source
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> dataList = new ArrayList<>();

        try {
            String[] projection = {"_id","id", "time", "day", "info", "subject"};
            Cursor cursor = db.query("stdb", projection, null, null, null, null, null,null);

            while (cursor.moveToNext()) {
                // Get data from the cursor and add it to the list
                String data = "\nID:" + cursor.getString(cursor.getColumnIndex("_id")) +
                        "\nCODE:" + cursor.getString(cursor.getColumnIndex("id")) +
                        "\nTIME:" + cursor.getString(cursor.getColumnIndex("time")) +
                        "\nDAY:" + cursor.getString(cursor.getColumnIndex("day")) +
                        "\nNEW SUB:" + cursor.getString(cursor.getColumnIndex("subject")) +
                        "\nINFO:" + cursor.getString(cursor.getColumnIndex("info"));

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
    private void showDataInAlertDialog(ArrayList<String> dataList) {
        // Create an AlertDialog to display the data
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("History");

        // Create a SpannableStringBuilder to customize text colors
        StringBuilder builderText = new StringBuilder();
        for (String data : dataList) {
            // Add each data with a newline separator
            builderText.append(data).append("\n\n");
        }

        // Apply color to the title
        builder.setMessage(builderText.toString());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when the "OK" button is clicked
            }
        });
        builder.show();
    }
    public void onDeleteAllClick(View view) {
        deleteAllRows();
        Toast.makeText(MainActivity.this,"Delete All History",Toast.LENGTH_SHORT);
    }
    public void onUpdateClick(View view) {
        // Get the id and new subject from your UI elements (EditText, etc.)
        String idToUpdate = idEditText.getText().toString();
        String newSubject = subjectEditText.getText().toString();

        // Call the update method
        updateSubjectForId(idToUpdate, newSubject);
    }

//    private ArrayList<String> getDataList() {
//        // Fetch data from the database or any other source
//        // and return a list of strings
//        // You can reuse the logic from your loadAndDisplayData method
//        // to fetch the data from the database
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        // ... (fetch data logic)
//        db.close();
//
//        return dataList;
//    }
//    private void showData() {
//        Log.d("MainActivity", "showData called");
//        Intent intent = new Intent(this, DisplayDataActivity.class);
//        intent.putStringArrayListExtra("dataList", getDataList());
//        startActivity(intent);
//    }





    private void saveTimetableEntry() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("id", idEditText.getText().toString());
            values.put("time", timeEditText.getText().toString());
            values.put("day", dayEditText.getText().toString());
            values.put("subject", subjectEditText.getText().toString());
            values.put("info", info.getText().toString());

            long newRowId = db.insert("stdb", null, values);

            if (newRowId == -1) {
                // Insert failed
                Log.e("MainActivity", "Error inserting data into the database");
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
            } else {
                // Insert successful
                Log.d("MainActivity", "Data inserted successfully with ID: " + newRowId);
                // Display a toast message indicating that the data has been saved
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Exception: " + e.getMessage());
        } finally {
            db.close();
        }
    }




    private void deleteAllRows() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            // Delete all rows from the table
            db.delete("stdb", null, null);

            // After deleting, you may want to refresh the displayed data

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Exception: " + e.getMessage());
        } finally {
            db.close();
        }
    }
    private void updateSubjectForId(String idToUpdate, String newSubject) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("subject", newSubject);

            // Update the subject for the specified id
            int rowsAffected = db.update("stdb", values, "id=?", new String[]{idToUpdate});

            if (rowsAffected > 0) {
                Log.d("MainActivity", "Subject updated successfully for id: " + idToUpdate);
            } else {
                Log.e("MainActivity", "No rows were updated for id: " + idToUpdate);
            }

            // After updating, you may want to refresh the displayed data
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Exception: " + e.getMessage());
        } finally {
            db.close();
        }
    }



}

