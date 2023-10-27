package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class CustomerActivity extends AppCompatActivity {
    Button btnAdd;
    ListView lvCustomers;

    RequestQueue requestQueue;
    ArrayAdapter<Customer> customerAdapter;

    private ArrayList<Customer> customerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        btnAdd = findViewById(R.id.btnAdd);
        lvCustomers = findViewById(R.id.lvCustomers);

        requestQueue = Volley.newRequestQueue(this);

        //Executors.newSingleThreadExecutor().execute(new GetCustomers());
        getAllCustomers();
        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                intent.putExtra("selectedCustomer", customerAdapter.getItem(position));
                startActivity(intent);
//                ListViewCustomer cust = (ListViewCustomer) lvCustomers.getAdapter().getItem(position);
//                Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
//                intent.putExtra("listviewcustomer", cust);
//                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerAddActivity.class);
                intent.putExtra("mode", "add");
                startActivity(intent);
            }
        });
    }

    private void getAllCustomers() {
        String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getallcustomers";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
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
                                        custCity, custProv, custPostal, custCountry, custHomePhone, custBusPhone, custEmail, agentId));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        customerAdapter = new ArrayAdapter<Customer>(getApplicationContext(), android.R.layout.simple_list_item_1, customerList);
                        lvCustomers.setAdapter(customerAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

//    class GetCustomers implements Runnable{
//        @Override
//        public void run() {
//            StringBuffer buffer = new StringBuffer();
//            String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getallcustomers";
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//
//                @Override
//                public void onResponse(String response) {
//                    VolleyLog.wtf(response, "utf-8");
//                    ArrayAdapter<ListViewCustomer> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        Log.d("test", jsonArray.toString());
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject cust = jsonArray.getJSONObject(i);
//                            ListViewCustomer customer = new ListViewCustomer(cust.getInt("customerId"), cust.getString("custFirstName"), cust.getString("custLastName"));
//                            adapter.add(customer);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    final ArrayAdapter<ListViewCustomer> finalAdapter = adapter;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            lvCustomers.setAdapter(finalAdapter);
//                        }
//                    });
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.wtf(error.getMessage(), "utf-8");
//                }
//            });
//            requestQueue.add(stringRequest);
//        }
//   }
}