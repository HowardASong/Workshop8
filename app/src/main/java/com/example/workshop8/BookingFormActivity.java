package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.StringReader;

public class BookingFormActivity extends AppCompatActivity {

    Spinner spCustomers;
    EditText etTravelerCount;
    Spinner spPackages;
    EditText etTripStart;
    EditText etTripEnd;
    Spinner spTripTypes;
    Spinner spClass;
    Button btnSubmit;
    Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        Log.d("Me", "In BookingFormActivity");

        spCustomers = findViewById(R.id.spCustomers);
        etTravelerCount = findViewById(R.id.etTravelerCount);
        spPackages = findViewById(R.id.spPackages);
        etTripStart = findViewById(R.id.etTripStart);
        etTripEnd = findViewById(R.id.etTripEnd);
        spTripTypes = findViewById(R.id.spTripTypes);
        spClass = findViewById(R.id.spClass);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);

        loadCustomers();
    }

    private void loadCustomers() {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getallcustomers";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response: ", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadCustomers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCustomers();
    }
}