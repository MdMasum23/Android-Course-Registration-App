package com.crud.courseregapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.course);
        ed3 = findViewById(R.id.fee);

        b1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.bt2);

        b2.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ViewActivity.class);
            startActivity(i);
        });

        b1.setOnClickListener(v -> insert());
    }

    public void insert() {
        String name = ed1.getText().toString().trim();
        String course = ed2.getText().toString().trim();
        String fee = ed3.getText().toString().trim();

        if (name.isEmpty() || course.isEmpty() || fee.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        try (SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null)) {
            db.execSQL("CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, course VARCHAR, fee VARCHAR)");

            String sql = "INSERT INTO records(name, course, fee) VALUES(?, ?, ?)";
            db.execSQL(sql, new Object[]{name, course, fee});
            Toast.makeText(this, "Record added successfully", Toast.LENGTH_LONG).show();

            clearFields();
        } catch (Exception ex) {
            Toast.makeText(this, "Record insertion failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void clearFields() {
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
        ed1.requestFocus();
    }
}
