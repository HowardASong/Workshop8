package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog; 
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CustomerDetailsActivity extends AppCompatActivity {
    Button btnSave, btnDelete;
    EditText etCustomerId, etCustFirstName, etCustLastName, etCustAddress, etCustCity, etCustProv, etCustPostal, etCustCountry, etCustHomePhone, etCustBusPhone, etCustEmail, etAgentId;
    RequestQueue requestQueue; // Declare a RequestQueue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        etCustomerId = findViewById(R.id.etCustomerId);
        etCustFirstName = findViewById(R.id.etCustFirstName);
        etCustLastName = findViewById(R.id.etCustLastName);
        etCustAddress = findViewById(R.id.etCustAddress);
        etCustCity = findViewById(R.id.etCustCity);
        etCustProv = findViewById(R.id.etCustProv);
        etCustPostal = findViewById(R.id.etCustPostal);
        etCustCountry = findViewById(R.id.etCustCountry);
        etCustHomePhone = findViewById(R.id.etCustHomePhone);
        etCustBusPhone = findViewById(R.id.etCustBusPhone);
        etCustEmail = findViewById(R.id.etCustEmail);
        etAgentId = findViewById(R.id.etAgentId);

        requestQueue = Volley.newRequestQueue(this); // Initialize the RequestQueue

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to handle saving or updating customer data here
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer(); // Call the deleteCustomer method when the "Delete" button is clicked
            }
        });
    }

    private void deleteCustomer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this customer?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User confirmed the deletion, proceed to delete the customer
                int customerId = Integer.parseInt(etCustomerId.getText().toString());

                // URL of API
                String deleteUrl = " " + customerId;

                StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, deleteUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Show a success message
                                Toast.makeText(CustomerDetailsActivity.this, "Customer successfully deleted", Toast.LENGTH_SHORT).show();
                                // Navigate back to the customer list activity
                                Intent intent = new Intent(CustomerDetailsActivity.this, CustomerActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // HShow an error message
                                String errorMessage = "An error occurred. Please try again later.";
                                Toast.makeText(CustomerDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                requestQueue.add(deleteRequest);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User canceled the deletion
                Toast.makeText(CustomerDetailsActivity.this, "Deletion canceled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}
