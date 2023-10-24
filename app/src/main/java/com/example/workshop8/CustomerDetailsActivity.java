package com.example.workshop8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomerDetailsActivity extends AppCompatActivity {
    Button btnSave, btnDelete;
    EditText etCustomerId, etCustFirstName, etCustLastName, etCustAddress, etCustCity, etCustProv, etCustPostal, etCustCountry, etCustHomePhone, etCustBusPhone, etCustEmail, etAgentId;

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




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCustomerId.setText("");
                etCustFirstName.setText("");
                etCustLastName.setText("");
                etCustAddress.setText("");
                etCustCity.setText("");
                etCustProv.setText("");
                etCustPostal.setText("");
                etCustCountry.setText("");
                etCustHomePhone.setText("");
                etCustBusPhone.setText("");
                etCustEmail.setText("");
                etAgentId.setText("");
            }
        });

    }


}