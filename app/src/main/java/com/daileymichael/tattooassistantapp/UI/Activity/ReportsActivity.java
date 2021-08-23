package com.daileymichael.tattooassistantapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.R;

import java.util.ArrayList;
import java.util.List;


public class ReportsActivity extends AppCompatActivity {
    private Spinner monthSpinner;
    private Button cancelButton;
    private Button runButton;
    public Database db;
    private TextView totalMonthlyCountTV;
    private TextView totalYearCountTV;
    private int totalYearCount;
    private int selectedMonthIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        db = new Database(this);
        db.open();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
        populateSpinner();


        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMonth = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Selected : " + selectedMonth, Toast.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculateMonthlyStats();
    }

    public void onClick(View v) {
        if (v == runButton) {

            if (monthSpinner.getSelectedItem() != null) {
                calculateMonthlyStats();

            }

            else {
                Toast.makeText(this, "Please select a month", Toast.LENGTH_SHORT).show();

            }


        }

        if (v == cancelButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    public void populateSpinner() {
        List<String> monthSpinnerArray = new ArrayList<String>();
        monthSpinnerArray.add("January");
        monthSpinnerArray.add("February");
        monthSpinnerArray.add("March");
        monthSpinnerArray.add("April");
        monthSpinnerArray.add("May");
        monthSpinnerArray.add("June");
        monthSpinnerArray.add("July");
        monthSpinnerArray.add("August");
        monthSpinnerArray.add("September");
        monthSpinnerArray.add("October");
        monthSpinnerArray.add("November");
        monthSpinnerArray.add("December");

        monthSpinner = findViewById(R.id.month_spinner);

        ArrayAdapter<String> monthSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthSpinnerArray);

        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monthSpinner.setAdapter(monthSpinnerAdapter);
    }

    private void initWidgets() {

        runButton = findViewById(R.id.run_button);
        runButton.setOnClickListener(this::onClick);

        cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this::onClick);

        totalMonthlyCountTV = findViewById(R.id.monthly_appointments_report_TV);
        totalYearCountTV = findViewById(R.id.yearly_appointments_report_TV);

        totalYearCount = db.appointmentDAO.getAppointments().size();

        totalYearCountTV.setText(String.valueOf(totalYearCount));

    }

    //method for the run button, grabbing month in the spinner and using it to call the db for requested information
    private void calculateMonthlyStats() {

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int selectedMonthCount;

                String monthString;

                switch(position) {

                    case 0 : //for January
                        monthString = "1";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in January");

                        Toast.makeText(ReportsActivity.this, "January has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 1 : //for February

                        monthString = "2";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in February");

                        Toast.makeText(ReportsActivity.this, "February has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 2 : //for March

                        monthString = "3";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in March");

                        Toast.makeText(ReportsActivity.this, "March has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 3 : //for April

                        monthString = "4";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in April");

                        Toast.makeText(ReportsActivity.this, "April has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 4 : //for May

                        monthString = "5";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in May");

                        Toast.makeText(ReportsActivity.this, "May has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 5 : //for June

                        monthString = "6";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in June");

                        Toast.makeText(ReportsActivity.this, "June has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 6 : //for July

                        monthString = "7";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in July");

                        Toast.makeText(ReportsActivity.this, "July has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 7 : //for August

                        monthString = "8";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in August");

                        Toast.makeText(ReportsActivity.this, "August has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 8 : //for September

                        monthString = "9";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in September");

                        Toast.makeText(ReportsActivity.this, "September has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 9 : //for October

                        monthString = "10";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in October");

                        Toast.makeText(ReportsActivity.this, "October has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 10 : //for November

                        monthString = "11";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in November");

                        Toast.makeText(ReportsActivity.this, "November has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;

                    case 11 : //for December

                        monthString = "12";
                        selectedMonthCount = db.appointmentDAO.getMonthlyAppointmentCount(monthString);

                        totalMonthlyCountTV.setText(selectedMonthCount + " Appointments in December");

                        Toast.makeText(ReportsActivity.this, "December has " + selectedMonthCount + " appointments", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(ReportsActivity.this, "Please select a different month!", Toast.LENGTH_SHORT).show();

            }
        });

    }

}

