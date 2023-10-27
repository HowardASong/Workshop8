package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerAddActivity extends AppCompatActivity {

    Button btnSave, btnCancel;
    EditText etCustFirstName, etCustLastName, etCustAddress, etCustCity, etCustProv, etCustPostal, etCustCountry, etCustHomePhone, etCustBusPhone, etCustEmail, etAgentId;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);

        btnSave = findViewById(R.id.btnSave1);
        btnCancel = findViewById(R.id.btnCancel);

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCustomer();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Cancel Entry");
                builder.setMessage("Do you want to go back");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void saveCustomer() {
        String customerJsonString = "{" +
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
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/customers/addcustomer";
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject customerObject;
        try {
            customerObject = new JSONObject(customerJsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, customerObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(CustomerAddActivity.this, "Customer successfully added", Toast.LENGTH_SHORT).show();
                        // Navigate back to the customer list activity
                        Intent intent = new Intent(CustomerAddActivity.this, CustomerActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "An error occurred. Please try again later.";
                Toast.makeText(CustomerAddActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(request);
    }


}