package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editPersonName = findViewById(R.id.editPersonName);

        editPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Handle before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Handle text changes
                String searchText = charSequence.toString();
                // Perform search based on searchText
                // Update your UI accordingly
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Handle after text changes
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
}
