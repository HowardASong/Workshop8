// Author: Nancy Chau
package com.example.workshop8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Controls the keyboard
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        EditText editPersonName = findViewById(R.id.editSearchBar);

        // Hide the keyboard initially
        inputMethodManager.hideSoftInputFromWindow(editPersonName.getWindowToken(), 0);

        editPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Handle before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Handle text changes
                String searchText = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Handle after text changes
            }
        });

        // Sets listener to show the keyboard when it's clicked
        editPersonName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Show the keyboard
                    inputMethodManager.showSoftInput(editPersonName, InputMethodManager.SHOW_FORCED);
                }
            }
        });

        // Bottom Navigation Handler
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navbarview);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {// Start the HomeActivity
                    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.menu_booking) {// Start the BookingsActivity
                    Intent bookingsIntent = new Intent(MainActivity.this, BookingActivity.class);
                    startActivity(bookingsIntent);
                    return true;
                } else if (itemId == R.id.menu_customer) {// Start the CustomerActivity
                    Intent customersIntent = new Intent(MainActivity.this, CustomerActivity.class);
                    startActivity(customersIntent);
                    //case R.id.menu_support:
                    // Start the SupportActivity
                    //Intent supportIntent = new Intent(SupportActivity.this, SupportActivity.class);
                    //startActivity(SupportActivity);
                    return false;
                }
                return false;
            }
        });
    }
/*        // Find the Sign In button by its ID (TO BE IMPLEMENTED LATER)
        Button signInButton = findViewById(R.id.main_signup_btn);

        // Set an OnClickListener for the Sign In button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registrationIntent);
            }
        });*/
}
