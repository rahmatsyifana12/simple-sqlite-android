package com.example.sqliteandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, phone, dob;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);

        insert = findViewById(R.id.insertBtn);
        update = findViewById(R.id.updateBtn);
        delete = findViewById(R.id.deleteBtn);
        view = findViewById(R.id.viewBtn);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameTxt = username.getText().toString();
                String phoneTxt = phone.getText().toString();
                String dobTxt = dob.getText().toString();

                boolean insertSuccess = DB.insertUserData(usernameTxt, phoneTxt, dobTxt);
                if (insertSuccess) {
                    Toast.makeText(MainActivity.this, "INSERT SUCCESS", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "INSERT FAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameTxt = username.getText().toString();
                String phoneTxt = phone.getText().toString();
                String dobTxt = dob.getText().toString();

                boolean updateSuccess = DB.updateUserData(usernameTxt, phoneTxt, dobTxt);
                if (updateSuccess) {
                    Toast.makeText(MainActivity.this, "UPDATE SUCCESS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "UPDATE FAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameTxt = username.getText().toString();

                boolean deleteSuccess = DB.deleteUser(usernameTxt);
                if (deleteSuccess) {
                    Toast.makeText(MainActivity.this, "DELETE SUCCESS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "DELETE FAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor usersCursor = DB.getUsers();
                if (usersCursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "DATA IS EMPTY", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (usersCursor.moveToNext()) {
                    buffer.append("Username: " + usersCursor.getString(0) + "\n");
                    buffer.append("Phone: " + usersCursor.getString(1) + "\n");
                    buffer.append("Date of Birth: " + usersCursor.getString(2) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("USER DATA");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}