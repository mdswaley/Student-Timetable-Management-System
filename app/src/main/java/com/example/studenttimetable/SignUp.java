package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private SignUpDb signUpDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        signUpDb = new SignUpDb(this);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Save user data to SQLite database
                    addUserToDatabase(username, email, password);

                    Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // Optionally, you can redirect the user to the login page or perform other actions.
                }

            }
        });
    }
    private void addUserToDatabase(String username, String email, String password) {
        SQLiteDatabase db = signUpDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SignUpDb.COLUMN_USERNAME, username);
        values.put(SignUpDb.COLUMN_EMAIL, email);
        values.put(SignUpDb.COLUMN_PASSWORD, password);

        db.insert(SignUpDb.TABLE_USERS, null, values);

        db.close();
    }
}