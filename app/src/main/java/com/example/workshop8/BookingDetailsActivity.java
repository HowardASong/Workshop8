    package com.example.workshop8;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
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

    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class BookingDetailsActivity extends AppCompatActivity {
        public static final String EXTRA_BOOKING_ID = "bookingID";
        Button btnDeleteBooking;
        EditText etBookingDetailId, etStartDate, etEndDate, etDescription, etDestination, etPrice, etBookingId, etRegion, etFee, etClass;

        //method to extract date with a format
        private String extractDatePart(String dateWithTime) {
            // Define a regex pattern to match the date format "MMM dd, yyyy"
            Pattern pattern = Pattern.compile("([A-Za-z]{3} \\d{1,2}, \\d{4})");
            // Create a Matcher object and match it against the dateWithTime string
            Matcher matcher = pattern.matcher(dateWithTime);

            if (matcher.find()) {
                // Extract and return the matched date part
                return matcher.group(1);
            } else {
                // Handle invalid date format
                return dateWithTime; // Return the original string
            }
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_booking_details);
            btnDeleteBooking = findViewById(R.id.btnDeleteBooking);
            etBookingDetailId = findViewById(R.id.etBookDetailId);
            etStartDate = findViewById(R.id.etStartDate);
            etEndDate = findViewById(R.id.etEndDate);
            etDescription = findViewById(R.id.etDescription);
            etPrice = findViewById(R.id.etPrice);
            etDestination = findViewById(R.id.etDestination);
            etBookingId = findViewById(R.id.etBookingId);
            etRegion = findViewById(R.id.etRegion);
            etFee = findViewById(R.id.etFee);
            etClass = findViewById(R.id.etClass);
            btnDeleteBooking.setVisibility(View.INVISIBLE); // set invisible by default
            int bookingId = getIntent().getIntExtra(EXTRA_BOOKING_ID, -1);

            etBookingDetailId.setFocusable(false);
            etBookingDetailId.setFocusableInTouchMode(false);
            etStartDate.setFocusable(false);
            etStartDate.setFocusableInTouchMode(false);
            etEndDate.setFocusable(false);
            etEndDate.setFocusableInTouchMode(false);
            etDescription.setFocusable(false);
            etDescription.setFocusableInTouchMode(false);
            etDestination.setFocusable(false);
            etDestination.setFocusableInTouchMode(false);
            etBookingId.setFocusable(false);
            etBookingId.setFocusableInTouchMode(false);
            etRegion.setFocusable(false);
            etRegion.setFocusableInTouchMode(false);
            etFee.setFocusable(false);
            etFee.setFocusableInTouchMode(false);
            etClass.setFocusable(false);
            etClass.setFocusableInTouchMode(false);
            etPrice.setFocusable(false);
            etPrice.setFocusableInTouchMode(false);

            btnDeleteBooking.setVisibility(View.INVISIBLE);
            //if bookingId returns an id and not -1 (none)
            if (bookingId != -1) {
                //api url
                String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getbookingdetail/" + bookingId;

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && !response.isEmpty()) {
                            try {
                                JSONArray bookingDetailsArray = new JSONArray(response); //new JSON OBJECT
                                if (bookingDetailsArray.length() > 0) { //if object returns some data
                                    JSONObject bookingDetailsJson = bookingDetailsArray.getJSONObject(0); //select first object in the array
                                    //define the variables with data
                                    int bookingDetailId = bookingDetailsJson.getInt("bookingDetailId");
                                    String startDate = bookingDetailsJson.optString("tripStart", "No data available");
                                    String endDate = bookingDetailsJson.optString("tripEnd", "No data available");
                                    String description = bookingDetailsJson.optString("description", "No data available");
                                    String destination = bookingDetailsJson.optString("destination", "No data available");
                                    int price = bookingDetailsJson.optInt("basePrice", 0);
                                    String region = bookingDetailsJson.optString("regionId", "No data available");
                                    String fee = bookingDetailsJson.optString("feeId", "No data available");
                                    String travelClass = bookingDetailsJson.optString("classId", "No data available");
                                    int bookingId = bookingDetailsJson.optInt("bookingId", 0);
                                    //extract the date with the pattern match
                                    startDate = extractDatePart(startDate);
                                    endDate = extractDatePart(endDate);

                                    etBookingDetailId.setText(String.valueOf(bookingDetailId));
                                    etStartDate.setText(startDate);
                                    etEndDate.setText(endDate);
                                    etDescription.setText(description);
                                    etDestination.setText(destination);
                                    etPrice.setText(String.valueOf(price));
                                    etRegion.setText(region);
                                    etFee.setText(fee);
                                    etClass.setText(travelClass);
                                    etBookingId.setText(String.valueOf(bookingId));
                                } else { //if no data was returned then fill every slot with not available
                                    etBookingDetailId.setText("Not Available");
                                    etStartDate.setText("Not Available");
                                    etEndDate.setText("Not Available");
                                    etDescription.setText("Not Available");
                                    etDestination.setText("Not Available");
                                    etPrice.setText("Not Available");
                                    etRegion.setText("Not Available");
                                    etFee.setText("Not Available");
                                    etClass.setText("Not Available");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //delete booking button visible
                            btnDeleteBooking.setVisibility(View.VISIBLE);
                        } else {
                            etStartDate.setText("No data available");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.wtf(error.getMessage(), "utf-8");
                    }
                });
                requestQueue.add(stringRequest);
                //delete button listener
                btnDeleteBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //call for the delete method with the bookingId
                        deleteBooking(bookingId);
                    }
                });

            } else {
                Log.d("ERROR", "No booking Id");
            }
        }
        private void deleteBooking(int bookingId) {
            //api url
            String deleteUrl = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/deletebooking/" + bookingId;

            StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, deleteUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //toast pop up when successful
                            Toast.makeText(BookingDetailsActivity.this, "Booking deleted successfully", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK); //set result to OK
                            finish();//close the activity
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.wtf(error.getMessage(), "utf-8");
                            //toast popup when failed
                            Toast.makeText(BookingDetailsActivity.this, "Failed to delete booking", Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(deleteRequest); //return
        }
    }