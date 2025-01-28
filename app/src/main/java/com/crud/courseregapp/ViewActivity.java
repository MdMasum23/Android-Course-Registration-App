package com.crud.courseregapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ListView lst1;
    ArrayList<String> titles = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lst1 = findViewById(R.id.lst1);

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try (SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null)) {

            Cursor c = db.rawQuery("SELECT * FROM records", null);

            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int courseIndex = c.getColumnIndex("course");
            int feeIndex = c.getColumnIndex("fee");

            titles.clear();
            ArrayList<student> students = new ArrayList<>();

            if (c.moveToFirst()) {
                do {
                    student stu = new student();
                    stu.id = c.getString(idIndex);
                    stu.name = c.getString(nameIndex);
                    stu.course = c.getString(courseIndex);
                    stu.fee = c.getString(feeIndex);
                    students.add(stu);

                    String display = stu.id + " | " + stu.name + " | " + stu.course + " | " + stu.fee;
                    titles.add(display);
                } while (c.moveToNext());
            }

            c.close();

            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
            lst1.setAdapter(arrayAdapter);

            lst1.setOnItemClickListener((parent, view, position, id) -> {
                student stu = students.get(position);
                Intent i = new Intent(ViewActivity.this, EditDeleteActivity.class);
                i.putExtra("id", stu.id);
                i.putExtra("name", stu.name);
                i.putExtra("course", stu.course);
                i.putExtra("fee", stu.fee);
                startActivity(i);
            });

        } catch (Exception e) {
            Toast.makeText(this, "Error loading data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
