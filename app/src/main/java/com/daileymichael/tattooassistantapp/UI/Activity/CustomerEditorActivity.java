package com.daileymichael.tattooassistantapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.Models.Appointment;
import com.daileymichael.tattooassistantapp.Models.Customer;

import com.daileymichael.tattooassistantapp.R;
import com.daileymichael.tattooassistantapp.UI.Fragments.CustomerDetailFragment;

import java.util.List;

/**
 *
 */
public class CustomerEditorActivity extends AppCompatActivity implements View.OnClickListener{
    private Customer mModifiedCustomer;
    private Spinner customerSpinner;
    public Database db;

    /**
     * This method sets the contentView and toolbar & spinner action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_editor);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customerSpinner = findViewById(R.id.customer_spinner);
        List<Customer> customerList = db.customerDAO.getCustomers();
        ArrayAdapter<Customer> courseDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, customerList);
        courseDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(courseDataAdapter);

        Intent intent = getIntent();

        int modifiedCourseId = intent.getIntExtra(CustomerDetailFragment.ARG_CUSTOMER_ID, -1);

        if (modifiedCourseId != -1) {
            mModifiedCustomer = db.customerDAO.getCustomerById(modifiedCourseId);

            String customerName = mModifiedCustomer.getName();
            String customerPhone = mModifiedCustomer.getPhone();
            String customerEmail = mModifiedCustomer.getEmail();
            EditText editCustomerName = findViewById(R.id.customer_name);
            editCustomerName.setText(customerName);
            EditText editCustomerPhone = findViewById(R.id.customer_phone);
            editCustomerPhone.setText(customerPhone);
            EditText editCustomerEmail = findViewById(R.id.customer_email);
            editCustomerEmail.setText(customerEmail);

            findViewById(R.id.customer_delete).setVisibility(View.VISIBLE);

        }
    }

    /**
     * This method closes the database when called
     */
    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * @param view
     */
    public void onDelete(View view) {
        
        if(db.appointmentDAO.getAppointmentByCustomer(mModifiedCustomer.getId()).size() != 0) {
            Toast.makeText(this, "Please delete this customer's appointments first!", Toast.LENGTH_SHORT).show();
        } else {
        boolean removed = db.customerDAO.removeCustomer(mModifiedCustomer);

        if (removed) {
            Toast.makeText(this, "You've deleted the customer", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    } }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText editCustomerName = findViewById(R.id.customer_name);
        EditText editCustomerPhone = findViewById(R.id.customer_phone);
        EditText editCustomerEmail = findViewById(R.id.customer_email);
        String customerName = editCustomerName.getText().toString();
        String customerPhone = editCustomerPhone.getText().toString();
        String customerEmail = editCustomerEmail.getText().toString();
        Customer customerSelected = (Customer) customerSpinner.getSelectedItem();

        int customerId = db.customerDAO.getCustomerCount();
        int modifiedCustomerId = -1;

        if (mModifiedCustomer != null) {
            modifiedCustomerId = mModifiedCustomer.getId();
            customerId = modifiedCustomerId;
        }
        Customer newCustomer = new Customer(customerId, customerName, customerPhone, customerEmail);

        boolean soSave = false;
        boolean isValid = newCustomer.isValid();

        if (isValid && modifiedCustomerId == -1) {

            soSave = db.customerDAO.addCustomer(newCustomer);
        } else if (isValid) {

            soSave = db.customerDAO.updateCustomer(newCustomer);
        } else {
            Toast.makeText(this, "Please enter all fields for customer information.", Toast.LENGTH_SHORT).show();
        }
        if (soSave) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    /**
     * This method enables for the date picker to display and to set in text field
     * @param v
     */
    @Override
    public void onClick(View v) {

    }
}
