package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

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

        Log.d("Checkpoint", "In BookingFormActivity");

        spCustomers = findViewById(R.id.spCustomers);
        etTravelerCount = findViewById(R.id.etTravelerCount);
        spPackages = findViewById(R.id.spPackages);
        etTripStart = findViewById(R.id.etTripStart);
        etTripEnd = findViewById(R.id.etTripEnd);
        spTripTypes = findViewById(R.id.spTripTypes);
        spClass = findViewById(R.id.spClass);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);

        // Load all necessary dropdown menu items
        loadCustomers();
        loadPackages();
        loadTripTypes();
        loadClasses();

        // Automatically adds dashes between year, month and day as user types
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

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        // Handling booking submission
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = "";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    currentDate = LocalDateTime.now().toString();
                }
                // get all input
                Customer selectedCustomer = (Customer) spCustomers.getSelectedItem();
                int travelerCount = Integer.parseInt(etTravelerCount.getText().toString());
                Packages selectedPackage = (Packages) spPackages.getSelectedItem();
                String tripStart = etTripStart.getText().toString();
                tripStart = formatDateString(tripStart);
                String tripEnd = etTripEnd.getText().toString();
                tripEnd = formatDateString(tripEnd);
                TripTypes selectedTripType = (TripTypes) spTripTypes.getSelectedItem();
                Classes selectedClass = (Classes) spClass.getSelectedItem();
                String regionId;

                // JSON String for Bookings object
                String bookingJson = "{" +
                        "'bookingDate': '" + currentDate + "Z" + "', " +
                        "'bookingNo': '" + generateRandomString() + "', " +
                        "'travelerCount': " + travelerCount + ", " +
                        "'customerId': " + selectedCustomer.getCustomerId() + ", " +
                        "'tripTypeId': '" + selectedTripType.getTripTypeId() + "'" +
                        "}";
                try {
                    // convert the string to JSON
                    JSONObject bookingObject = new JSONObject(bookingJson);
                    Log.d("Booking JSON", bookingJson);
                    Log.d("Booking Json", bookingObject.toString());
                    postBooking(bookingObject);

                } catch (JSONException e) {
                    Log.e("Booking JSON", "Invalid Json string");
                }

                // Assign region based on package selection
                if (selectedPackage.getPackageId() == 1) {
                    regionId = "SA";
                }
                else if (selectedPackage.getPackageId() == 2) {
                    regionId = "NA";
                }
                else if (selectedPackage.getPackageId() == 3) {
                    regionId = "ASIA";
                }
                else {
                    regionId = "EU";
                }

                try {
                    Thread.sleep(2000);
                    // For one booking with n travelers, n number of booking details are created
                    // so we need to loop through creating booking details for each traveler
                    for (int i=0; i<travelerCount; i++) {
                        getLastBooking(tripStart, tripEnd, selectedPackage, regionId, selectedClass);
                        Toast.makeText(getApplicationContext(),"Booking created successfully!",Toast.LENGTH_SHORT).show();
                        resetFields();
                    }
                } catch (JSONException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private String buildBookingDetailsJson(String tripStart, String tripEnd, Packages selectedPackage,
                                           Bookings lastBooking, String regionId, Classes selectedClass) {
        String bookingDetailsJson = "{" +
                "'itineraryNo': " + 0 + ", " +
                "'tripStart': '" + tripStart + "', " +
                "'tripEnd': '" + tripEnd + "', " +
                "'description': '" + selectedPackage.getPkgDesc() + "', " +
                "'destination': '" + " " + "', " +
                "'basePrice': " + selectedPackage.getPkgBasePrice() + ", " +
                "'agencyCommission': " + selectedPackage.getPkgAgencyCommission() + ", " +
                "'bookingId': " + lastBooking.getBookingId() + ", " +
                "'regionId': '" + regionId + "', " +
                "'classId': '" + selectedClass.getClassId() + "', " +
                "'feeId': '" + "BK" + "', " +
                "'productSupplierId': " + null + "" +
                "}";
        return bookingDetailsJson;
    }

    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public String formatDateString(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = inputFormat.parse(inputDate);

            // Format the date with leading zeros for month and day
            String formattedDate = outputFormat.format(date);

            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parsing errors here
            return inputDate; // Return the original input date as a fallback
        }
    }

    public void resetFields() {
        spCustomers.setSelection(0);
        etTravelerCount.getText().clear();
        spPackages.setSelection(0);
        etTripStart.getText().clear();
        etTripEnd.getText().clear();
        spTripTypes.setSelection(0);
        spClass.setSelection(0);
    }

    // Handles loading in all customers onto drop down menu (spinner)
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

    // Handles loading in all packages onto drop down menu
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

    // Handles loading in all trip types onto drop down menu
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

    // Handles loading in all classes onto drop down menu
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

    // This function passes the latest created bookings to booking details JSON builder so
    // that the booking details are associated with the proper booking id
    // We call postBookingDetails here to ensure that we're creating the current booking first,
    // then fetching it to create its booking details.
    private void getLastBooking(String tripStart, String tripEnd, Packages selectedPackage,
                                String regionId, Classes selectedClass) throws JSONException {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getlastbooking";
        RequestQueue queue = Volley.newRequestQueue(this);
        Bookings lastBooking = new Bookings();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int bookingId = response.getInt("bookingId");
                            Log.d("test",bookingId+"");
                            lastBooking.setBookingId(bookingId);
                            String bookingDetailsJson = buildBookingDetailsJson(tripStart, tripEnd, selectedPackage, lastBooking,
                                    regionId, selectedClass);
                            Log.d("test", bookingDetailsJson);
                            JSONObject bookingDetailsObject = new JSONObject(bookingDetailsJson);
                            postBookingDetails(bookingDetailsObject);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error1", error.toString());
            }
        });
        queue.add(request);

    }

    private void postBooking(JSONObject bookingJson) {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/postbooking";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, bookingJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("In postBooking","Success?: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        }
        );
        queue.add(request);
    }

    private void postBookingDetails(JSONObject bookingDetailsJson) {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/postbookingdetail";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, bookingDetailsJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("In postBookingDetail","Success?: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error2", error.getMessage());
            }
        }
        );
        queue.add(request);
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