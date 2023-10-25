package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

        spCustomers = findViewById(R.id.spCustomers);
        etTravelerCount = findViewById(R.id.etTravelerCount);
        spPackages = findViewById(R.id.spPackages);
        etTripStart = findViewById(R.id.etTripStart);
        etTripEnd = findViewById(R.id.etTripEnd);
        spTripTypes = findViewById(R.id.spTripTypes);
        spClass = findViewById(R.id.spClass);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
    }
}