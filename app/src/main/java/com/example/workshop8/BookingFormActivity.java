package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private ArrayList<Packages> packagesList = new ArrayList<>();
    private ArrayList<TripTypes> tripTypesList = new ArrayList<>();
    private ArrayList<Classes> classesList = new ArrayList<>();

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
        loadPackages();
        loadTripTypes();
        loadClasses();

        etTripStart.addTextChangedListener(new TextWatcher() {
            int prevL = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = etTripStart.getText().toString().length();
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 4 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        etTripEnd.addTextChangedListener(new TextWatcher() {
            int prevL = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = etTripEnd.getText().toString().length();
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 4 || length == 7)) {
                    editable.append("-");
                }
            }
        });
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

    private void loadPackages() {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getallpackages";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0; i<response.length(); i++) {
                            try {
                                JSONObject pkgObject = response.getJSONObject(i);
                                int packageId = pkgObject.getInt("packageId");
                                String pkgName = pkgObject.getString("pkgName");
                                String pkgStartDate = pkgObject.getString("pkgStartDate");
                                String pkgEndDate = pkgObject.getString("pkgEndDate");
                                String pkgDesc = pkgObject.getString("pkgDesc");
                                double pkgBasePrice = pkgObject.getDouble("pkgBasePrice");
                                double pkgAgencyCommission = pkgObject.getDouble("pkgAgencyCommission");
                                packagesList.add(new Packages(packageId, pkgName, pkgStartDate, pkgEndDate,
                                        pkgDesc, pkgBasePrice, pkgAgencyCommission));

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        ArrayAdapter<Packages> packagesAdapter = new ArrayAdapter<Packages>(getApplicationContext(), android.R.layout.simple_spinner_item, packagesList);
                        packagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spPackages.setAdapter(packagesAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void loadTripTypes() {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getalltriptypes";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0; i<response.length(); i++) {
                            try {
                                JSONObject ttObject = response.getJSONObject(i);
                                String tripTypeId = ttObject.getString("tripTypeId");
                                String ttName = ttObject.getString("ttName");
                                tripTypesList.add(new TripTypes(tripTypeId, ttName));

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        ArrayAdapter<TripTypes> ttAdapter = new ArrayAdapter<TripTypes>(getApplicationContext(), android.R.layout.simple_spinner_item, tripTypesList);
                        ttAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spTripTypes.setAdapter(ttAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void loadClasses() {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getallclasses";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0; i<response.length(); i++) {
                            try {
                                JSONObject classObject = response.getJSONObject(i);
                                String classId = classObject.getString("classId");
                                String className = classObject.getString("className");
                                classesList.add(new Classes(classId, className));

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        ArrayAdapter<Classes> classesAdapter = new ArrayAdapter<Classes>(getApplicationContext(), android.R.layout.simple_spinner_item, classesList);
                        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spClass.setAdapter(classesAdapter);
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