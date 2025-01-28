package com.crud.courseregapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditDeleteActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4;
    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        // Initialize views
        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.course);
        ed3 = findViewById(R.id.fee);
        ed4 = findViewById(R.id.id);
        b1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.bt2);
        b3 = findViewById(R.id.bt3);

        // Get data from the previous Intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String course = intent.getStringExtra("course");
        String fee = intent.getStringExtra("fee");

        // Set the values to the EditTexts
        ed4.setText(id);
        ed1.setText(name);
        ed2.setText(course);
        ed3.setText(fee);

        // Button actions
        b2.setOnClickListener(v -> deleteRecord());
        b3.setOnClickListener(v -> navigateBack());
        b1.setOnClickListener(v -> updateRecord());
    }

    // Delete the record from the database
    private void deleteRecord() {
        try {
            String id = ed4.getText().toString();
            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);
            String sql = "DELETE FROM records WHERE id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, id);
            statement.execute();
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_LONG).show();
            clearFields();
        } catch (Exception ex) {
            Toast.makeText(this, "Error Deleting Record", Toast.LENGTH_LONG).show();
        }
    }

    // Update the record in the database
    private void updateRecord() {
        try {
            String name = ed1.getText().toString();
            String course = ed2.getText().toString();
            String fee = ed3.getText().toString();
            String id = ed4.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);

            String sql = "UPDATE records SET name = ?, course = ?, fee = ? WHERE id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, name);
            statement.bindString(2, course);
            statement.bindString(3, fee);
            statement.bindString(4, id);
            statement.execute();
            Toast.makeText(this, "Record Updated", Toast.LENGTH_LONG).show();
            clearFields();
        } catch (Exception ex) {
            Toast.makeText(this, "Error Updating Record", Toast.LENGTH_LONG).show();
        }
    }

    // Clear the fields
    private void clearFields() {
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
        ed1.requestFocus();
    }

    // Navigate back to ViewActivity
    private void navigateBack() {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }
}
