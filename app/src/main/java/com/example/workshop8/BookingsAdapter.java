package com.example.workshop8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookingsAdapter extends ArrayAdapter<Bookings> {
    private Context context;
    private List<Bookings> bookingsList;

    public BookingsAdapter(Context context, List<Bookings> bookingsList) {
        super(context, android.R.layout.simple_list_item_1 ,bookingsList);
        this.context = context;
        this.bookingsList = bookingsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Bookings booking = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        if (booking != null) {
            textView.setText("Booking ID: " + booking.getBookingId());
        }

        return convertView;
    }
}