package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

public class CustomerDetailsActivity extends AppCompatActivity {
    Button btnSave, btnDelete;
    EditText etCustomerId, etCustFirstName, etCustLastName, etCustAddress, etCustCity, etCustProv, etCustPostal, etCustCountry, etCustHomePhone, etCustBusPhone, etCustEmail, etAgentId;
    RequestQueue requestQueue;
    Customer selectedCustomer;
    ListViewCustomer listViewCust;



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
        etCustomerId.setFocusable(false);
        etCustomerId.setFocusableInTouchMode(false);

        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        selectedCustomer = (Customer) intent.getSerializableExtra("selectedCustomer");

        etCustomerId.setText(selectedCustomer.getCustomerId() + "");
        etCustFirstName.setText(selectedCustomer.getCustFirstName());
        etCustLastName.setText(selectedCustomer.getCustLastName());
        etCustAddress.setText(selectedCustomer.getCustAddress());
        etCustCity.setText(selectedCustomer.getCustCity());
        etCustProv.setText(selectedCustomer.getCustProv());
        etCustPostal.setText(selectedCustomer.getCustPostal());
        etCustCountry.setText(selectedCustomer.getCustCountry());
        etCustHomePhone.setText(selectedCustomer.getCustHomePhone());
        etCustBusPhone.setText(selectedCustomer.getCustBusPhone());
        etCustEmail.setText(selectedCustomer.getCustEmail());
        etAgentId.setText(selectedCustomer.getAgentId() + "");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomer();
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


    private void saveCustomer() {
        String customerJsonString = "{" +
                "'customerId': " + Integer.parseInt(etCustomerId.getText().toString()) + ", " +
                "'custFirstName': '" + etCustFirstName.getText().toString() + "', " +
                "'custLastName': '" + etCustLastName.getText().toString() + "', " +
                "'custAddress': '" + etCustAddress.getText().toString() + "', " +
                "'custCity': '" + etCustCity.getText().toString() + "', " +
                "'custProv': '" + etCustProv.getText().toString() + "', " +
                "'custPostal': '" + etCustPostal.getText().toString() + "', " +
                "'custCountry': '" + etCustCountry.getText().toString() + "', " +
                "'custHomePhone': '" + etCustHomePhone.getText().toString() + "', " +
                "'custBusPhone': '" + etCustBusPhone.getText().toString() + "', " +
                "'custEmail': '" + etCustEmail.getText().toString() + "', " +
                "'agentId': '" + Integer.parseInt(etAgentId.getText().toString()) + "'}" ;
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/customers/updatecustomer";
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject customerObject;
        try {
            customerObject = new JSONObject(customerJsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, customerObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(CustomerDetailsActivity.this, "Customer successfully updated", Toast.LENGTH_SHORT).show();
                        // Navigate back to the customer list activity
                        Intent intent = new Intent(CustomerDetailsActivity.this, CustomerActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "An error occurred. Please try again later.";
                Toast.makeText(CustomerDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(request);
    }

    // Author: Nancy Chau
    // Deleting a customer
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
                String deleteUrl = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/customers/deletecustomer/" + customerId;

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
                                // Show an error message
                                String errorMessage = "An error occurred. Please try again later.";
                                Toast.makeText(CustomerDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                requestQueue.add(deleteRequest);
            }
        });

        // Author: Nancy Chau
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
