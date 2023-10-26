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
    int customerId;
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

        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        listViewCust = (ListViewCustomer) intent.getSerializableExtra("listviewcustomer");

        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getallcustomers" + customerId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                VolleyLog.wtf(response, "utf-8");
                JSONObject cust = null;
                try {
                    cust = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JSONObject finalCust = cust;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            etCustomerId.setText(finalCust.getInt("customerId") + "");
                            etCustFirstName.setText(finalCust.getString("custFirstName"));
                            etCustLastName.setText(finalCust.getString("custLastName"));
                            etCustAddress.setText(finalCust.getString("custAddress"));
                            etCustCity.setText(finalCust.getString("custCity"));
                            etCustProv.setText(finalCust.getString("custProv"));
                            etCustPostal.setText(finalCust.getString("custPostal"));
                            etCustCountry.setText(finalCust.getString("custCountry"));
                            etCustHomePhone.setText(finalCust.getString("custHomePhone"));
                            etCustBusPhone.setText(finalCust.getString("custBusPhone"));
                            etCustEmail.setText(finalCust.getString("custEmail"));
                            etAgentId.setText(finalCust.getInt("agentId") + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.wtf(error.getMessage(), "utf-8");
            }
        });
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
    }

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
                                // Show an error message
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
