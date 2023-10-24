package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class CustomerActivity extends AppCompatActivity {
    Button btnAdd,btnDelete;
    ListView lvCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        lvCustomers = findViewById(R.id.lvCustomers);
    }
}