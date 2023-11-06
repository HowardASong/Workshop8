package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class BookingActivity extends AppCompatActivity {

    ListView lvBook;
    Spinner spinCustomer;
    RequestQueue requestQueue;
    Button btnCreate;
    List<Customer> customersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        requestQueue = Volley.newRequestQueue(this);
        lvBook = findViewById(R.id.lvBook);
        spinCustomer = findViewById(R.id.spinCustomer);
        btnCreate = findViewById(R.id.btnCreate);
        customersList = new ArrayList<>();

        //get customers
        Executors.newSingleThreadExecutor().execute(new GetAllCustomers());
        //button to create a new booking
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent for booking form activity
                Intent intent = new Intent(BookingActivity.this, BookingFormActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //on resume will refresh the listview if the selection is not position 0
        if (spinCustomer.getSelectedItemPosition() > 0) {
            //get the customer id from the selected position
            int customerId = getSelectedCustomerId(spinCustomer.getSelectedItemPosition());
            //call for the getselectedbooking method with customerid
            Executors.newSingleThreadExecutor().execute(new GetSelectedBooking(customerId));
        }
    }
    //get all customers method
    class GetAllCustomers implements Runnable {
        @Override
        public void run() {
            StringBuffer buffer = new StringBuffer();
            String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getallcustomers";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");
                    try {
                        JSONArray customersArray = new JSONArray(response);
                        final JSONArray finalCustomersArray = customersArray;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    List<String> customerNames = new ArrayList<>();
                                    customerNames.add("Select a customer"); // Add "Select a customer" as the first item.
                                    for (int i = 0; i < finalCustomersArray.length(); i++) {
                                        JSONObject customer = finalCustomersArray.getJSONObject(i);
                                        // Create a Customer object and populate it
                                        Customer cust = new Customer();
                                        cust.setCustomerId(customer.getInt("customerId"));
                                        cust.setCustFirstName(customer.getString("custFirstName"));
                                        cust.setCustLastName(customer.getString("custLastName"));
                                        cust.setCustAddress(customer.getString("custAddress"));
                                        cust.setCustCity(customer.getString("custCity"));
                                        cust.setCustProv(customer.getString("custProv"));
                                        cust.setCustPostal(customer.getString("custPostal"));
                                        cust.setCustCountry(customer.getString("custCountry"));
                                        cust.setCustHomePhone(customer.getString("custHomePhone"));
                                        cust.setCustBusPhone(customer.getString("custBusPhone"));
                                        cust.setCustEmail(customer.getString("custEmail"));
                                        int agentId = -1; // Default value in case "agentId" is missing or null
                                        if (customer.has("agentId") && !customer.isNull("agentId")) {
                                            cust.setAgentId(customer.getInt("agentId"));
                                        } else {
                                            cust.setAgentId(agentId);
                                        }
                                        customersList.add(cust);
                                        String customerName = cust.getCustFirstName() + " " + cust.getCustLastName();
                                        customerNames.add(customerName);
                                    }

                                    // Populate the Spinner with customer names
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_spinner_item, customerNames);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinCustomer.setAdapter(adapter);

                                    // Add a listener to the Spinner to handle item selection
                                    spinCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (position > 0) {
                                                if (position > 0) {
                                                    // Position 0 is "Select a customer"
                                                    int customerId = getSelectedCustomerId(position);
                                                    // updates listview with the customerid
                                                    Executors.newSingleThreadExecutor().execute(new GetSelectedBooking(customerId));
                                                } else { // Clear the ListView when "Select a customer" is selected
                                                    lvBook.setAdapter(null);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null && error.getMessage() != null) {
                        VolleyLog.wtf(error.getMessage(), "utf-8");
                    } else {
                        Log.e("ERROR", "ERROR MESSAGE NULL");
                    }
                }
            });

            requestQueue.add(stringRequest);
        }
    }
    //method to get selected custoemr from the position on the spinner
    private int getSelectedCustomerId(int selectedPosition) {
        if (selectedPosition > 0) {
            Customer selectedCustomer = customersList.get(selectedPosition - 1);
            return selectedCustomer.getCustomerId();
        }
        return -1;
    }
    //method to get the selectedbooking with the customer id and update the listview
    class GetSelectedBooking implements Runnable {
        private int customerId;

        public GetSelectedBooking(int customerId) {
            this.customerId = customerId;
        }
        @Override
        public void run() {
            if (customersList.size() > 0) {
                String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getbookings/" + customerId;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray bookingsArray = new JSONArray(response);
                            List<Bookings> bookingsList = new ArrayList<>();
                            for (int i = 0; i < bookingsArray.length(); i++) {
                                JSONObject booking = bookingsArray.getJSONObject(i);
                                Bookings bookingItem = new Bookings();
                                bookingItem.setBookingId(booking.getInt("bookingId"));
                                bookingsList.add(bookingItem);
                            }
                            BookingsAdapter adapter = new BookingsAdapter(BookingActivity.this, bookingsList);
                            lvBook.setAdapter(adapter); //set listview as the adapter
                            //listview listener
                            lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (position >= 0 && position < bookingsList.size()) {
                                        Bookings selectedBooking = bookingsList.get(position);
                                        int bookingId = selectedBooking.getBookingId();
                                        //starts the booking details activity
                                        Intent intent = new Intent(BookingActivity.this, BookingDetailsActivity.class);
                                        intent.putExtra(BookingDetailsActivity.EXTRA_BOOKING_ID, bookingId);
                                        startActivity(intent);
                                    }
                                }
                            });

                        } catch (JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.wtf(error.getMessage(), "utf-8");
                    }
                });

                requestQueue.add(stringRequest);
            } else {
                Log.e("ERROR", "No customers available");
            }
        }
    }
}
