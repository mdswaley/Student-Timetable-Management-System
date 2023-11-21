package com.example.studenttimetable;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    private EditText useremailText, passwordText;
    private Button signinButton,signup;
    private SignUpDb signUpDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        useremailText = findViewById(R.id.userhint);
        passwordText = findViewById(R.id.passhint);
        signinButton = findViewById(R.id.submit);
        signup=findViewById(R.id.signUp);
        signUpDb = new SignUpDb(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginPage.this,SignUp.class);
                startActivity(intent);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = useremailText.getText().toString();
                String password = passwordText.getText().toString();

                // Check if the entered username and password match the expected values
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(email.equals("mdswaley@gmail.com") && password.equals("123")){
                        Toast.makeText(LoginPage.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this,TeacherHome.class);
                        startActivity(intent);
                    }else {
                        if (isValidUser(email, password)) {
                            // Login successful
                            Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginPage.this,StudentHome.class);
                            startActivity(intent);
                            // You can redirect to the main activity or perform other actions.
                        } else {
                            // Login failed
                            Toast.makeText(LoginPage.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private boolean isValidUser(String email, String password) {
        SQLiteDatabase db = signUpDb.getReadableDatabase();

        String selection = SignUpDb.COLUMN_EMAIL + " = ? AND " + SignUpDb.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                SignUpDb.TABLE_USERS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isValid;
    }
}

