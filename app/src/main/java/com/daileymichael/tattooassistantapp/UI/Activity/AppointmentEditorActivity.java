package com.daileymichael.tattooassistantapp.UI.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.Models.Appointment;
import com.daileymichael.tattooassistantapp.Models.Customer;
import com.daileymichael.tattooassistantapp.R;
import com.daileymichael.tattooassistantapp.UI.Fragments.AppointmentDetailFragment;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 *
 */
public class AppointmentEditorActivity extends AppCompatActivity implements View.OnClickListener{
    private Appointment mModifiedAppointment;
    private Spinner customerSpinner;
    public Database db;
    Button btnNewCustomer;
    Button btnStartDatePicker;
    Button btnStartTimePicker;
    Button btnEndTimePicker;
    private int sYear, sMonth, sDay;
    private int sHour, sMinutes;
    private int mHour, mMinutes;
    private String mAm_pm;
    private String sAm_pm;
    EditText editStartDate;
    EditText editStartTime;
    EditText editEndTime;
    EditText editPrice;
    EditText editDescription;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");



    /**
     * This method sets the contentView and toolbar & s action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_editor);
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

        int modifiedAppointmentId = intent.getIntExtra(AppointmentDetailFragment.ARG_APPOINTMENT_ID, -1);
        btnNewCustomer = findViewById(R.id.new_appointment_customer);
        editStartDate = findViewById(R.id.appointment_start_date);
        btnStartDatePicker = findViewById(R.id.appointment_start_date_button);
        editStartTime = findViewById(R.id.appointment_start_time);
        btnStartTimePicker = findViewById(R.id.appointment_start_time_button);
        editEndTime = findViewById(R.id.appointment_end_time);
        btnEndTimePicker = findViewById(R.id.appointment_end_time_button);
        editPrice = findViewById(R.id.appointment_price);
        editDescription = findViewById(R.id.appointment_description);

        btnStartDatePicker.setOnClickListener(this);
        btnStartTimePicker.setOnClickListener(this);
        btnEndTimePicker.setOnClickListener(this);
        btnNewCustomer.setOnClickListener(this);

        if (modifiedAppointmentId != -1) {
            mModifiedAppointment = db.appointmentDAO.getAppointmentById(modifiedAppointmentId);

            String aTitle = mModifiedAppointment.getTitle();
            String aStartDate = String.format(mModifiedAppointment.getStartDate(), dateFormat);
            String aStartTime = String.format(mModifiedAppointment.getStartTime(), timeFormat);
            String aEndTime = String.format(mModifiedAppointment.getEndTime(), timeFormat);
            String aPrice = mModifiedAppointment.getPrice();
            String aDescription = mModifiedAppointment.getDescription();

            EditText editTitle = findViewById(R.id.appointment_title);
            editTitle.setText(aTitle);
            EditText editPrice = findViewById(R.id.appointment_price);
            editPrice.setText(aPrice);
            EditText editDescription = findViewById(R.id.appointment_description);
            editDescription.setText(aDescription);

            editStartDate = findViewById(R.id.appointment_start_date);
            editStartDate.setText(aStartDate);
            editStartTime = findViewById(R.id.appointment_start_time);
            editStartTime.setText(aStartTime);
            editEndTime = findViewById(R.id.appointment_end_time);
            editEndTime.setText(aEndTime);

            findViewById(R.id.appointment_delete).setVisibility(View.VISIBLE);
            btnStartDatePicker.setOnClickListener(this);
            btnStartTimePicker.setOnClickListener(this);
            btnEndTimePicker.setOnClickListener(this);
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
     * This method removes the selected assessment when called
     * @param view
     */
    public void onDelete(View view) {
        boolean removed = db.appointmentDAO.removeAppointment(mModifiedAppointment);
        if (removed) {
            Intent intent = new Intent(this, AppointmentListActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this, "You have deleted an Appointment!", Toast.LENGTH_SHORT).show();
    }


    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.appointment_title);
        EditText editStartDate = findViewById(R.id.appointment_start_date);
        EditText editStartTime = findViewById(R.id.appointment_start_time);
        EditText editEndTime = findViewById(R.id.appointment_end_time);
        EditText editPrice = findViewById(R.id.appointment_price);
        EditText editDescription = findViewById(R.id.appointment_description);
        String aTitle = editTitle.getText().toString();
        String aStartDate = String.format(editStartDate.getText().toString(), dateFormat);
        String aStartTime = String.format(editStartTime.getText().toString(), timeFormat);
        String aEndTime = String.format(editEndTime.getText().toString(), timeFormat);
        String aPrice = editPrice.getText().toString();
        String aDescription = editDescription.getText().toString();
        Customer aCustomer = (Customer) customerSpinner.getSelectedItem();

        int aId = db.appointmentDAO.getAppointmentCount();
        int modifiedAppointmentId = -1;

        if (mModifiedAppointment != null) {
            modifiedAppointmentId = mModifiedAppointment.getId();
            aId = modifiedAppointmentId;
        }
        Appointment newAppointment = new Appointment(aId, aTitle, aStartDate, aStartTime, aEndTime, aDescription, aPrice, aCustomer.getId());

        boolean soSave = false;
        boolean isValid = newAppointment.isValid();

        if (isValid && modifiedAppointmentId == -1) {
            soSave = db.appointmentDAO.addAppointment(newAppointment);
            Toast.makeText(getApplicationContext(), "Created a new appointment", Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub

        } else if (isValid) {
            soSave = db.appointmentDAO.updateAppointment(newAppointment);
            Toast.makeText(getApplicationContext(), "Updated an existing appointment", Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub

        }
        if (soSave) {
            Intent intent = new Intent(this, AppointmentListActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Appointment has been saved", Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub

        } else {
            Toast.makeText(getApplicationContext(), "Please enter all the appointment details!", Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub
        }
        finish();
    }

    /**
     * This method enables for the date picker to display and to set in text field
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnStartDatePicker) {
            final Calendar c = Calendar.getInstance();
            sYear = c.get(Calendar.YEAR);
            sMonth = c.get(Calendar.MONTH);
            sDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> editStartDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year), sYear, sMonth, sDay);
            datePickerDialog.show();
        }

        if (v == btnEndTimePicker) {

            final Calendar c = Calendar.getInstance() ;
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinutes = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (TimePicker view, int hourOfDay, int minutes) -> {
                        boolean isPM = (hourOfDay >= 12);
                        editEndTime.setText(((hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12) + ":" + minutes + " " + (isPM ? "PM" : "AM"));
                    }, mHour, mMinutes, false);
            timePickerDialog.show();
        }
        if (v == btnStartTimePicker) {

            final Calendar c = Calendar.getInstance() ;
            sHour = c.get(Calendar.HOUR_OF_DAY);
            sMinutes = c.get(Calendar.MINUTE);

            if(c.get(Calendar.AM_PM) == Calendar.AM) {
                sAm_pm = "AM";
            } else if (c.get(Calendar.AM_PM) == Calendar.PM) {
                sAm_pm = "PM";
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minutes) -> {
                        boolean isPM = (hourOfDay >= 12);
                        editStartTime.setText(((hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12) + ":" + minutes + " " + (isPM ? "PM" : "AM"));
                    }, sHour, sMinutes, false);
            timePickerDialog.show();
        }
        if (v == btnNewCustomer) {
            Intent intent = new Intent(this, CustomerEditorActivity.class);
            startActivity(intent);
        }
    }
}
