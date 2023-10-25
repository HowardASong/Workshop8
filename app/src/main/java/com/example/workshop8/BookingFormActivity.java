package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;

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

    private ArrayList<Customer> customerList = new ArrayList<>();

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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i=0; i<response.length(); i++) {
                        try {
                            JSONObject custObject = response.getJSONObject(i);
                            int customerId = custObject.getInt("customerId");
                            String custFirstName = custObject.getString("custFirstName");
                            String custLastName = custObject.getString("custLastName");
                            String custAddress = custObject.getString("custAddress");
                            String custCity = custObject.getString("custCity");
                            String custProv = custObject.getString("custProv");
                            String custPostal = custObject.getString("custPostal");
                            String custCountry = custObject.getString("custCountry");
                            String custHomePhone = custObject.getString("custHomePhone");
                            String custBusPhone = custObject.getString("custBusPhone");
                            String custEmail = custObject.getString("custEmail");
                            int agentId = -1; // Default value in case "agentId" is missing or null
                            if (custObject.has("agentId") && !custObject.isNull("agentId")) {
                                agentId = custObject.getInt("agentId");
                            }
                            customerList.add(new Customer(customerId, custFirstName, custLastName, custAddress,
                                    custCity, custProv, custPostal, custCountry,custHomePhone, custBusPhone, custEmail, agentId));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    ArrayAdapter<Customer> customerAdapter = new ArrayAdapter<Customer>(getApplicationContext(), android.R.layout.simple_spinner_item, customerList);
                    customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCustomers.setAdapter(customerAdapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error.toString());
                }
        });
        queue.add(jsonArrayRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //loadCustomers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadCustomers();
    }
}