package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CustomerActivity extends AppCompatActivity {
    Button btnAdd;
    ListView lvCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        btnAdd = findViewById(R.id.btnAdd);
        lvCustomers = findViewById(R.id.lvCustomers);

        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext() , CustomerDetailsActivity.class);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private class CustomerDisplay implements Runnable(){

        @Override
        public void run() {
            try {
                URL url = new URL("http://localhost:8080/Workshop7-1.0-SNAPSHOT/customers/getallcustomers");
                InputStream is = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                runOnUiThread(new Runnable() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}