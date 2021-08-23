/*
TODO: Add button to create a new appointment
 */

package com.daileymichael.tattooassistantapp.UI.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.Models.Appointment;
import com.daileymichael.tattooassistantapp.R;
import com.daileymichael.tattooassistantapp.UI.Fragments.AppointmentDetailFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarViewActivity extends AppCompatActivity {

    private static final String TAG = "CalendarViewActivity";
    private CalendarView mCalendarView;
    public Database db;
    private boolean mPane;
    long mDate;
    private TextView calendarAppointmentTitleTV;
    private TextView calendarAppointmentStartTimeTV;
    private TextView calendarAppointmentEndTimeTV;
    private ListView calendarAppointmentListView;
    private ArrayAdapter<Appointment> appointmentListAdapter;

    //look into this website for customization https://medium.com/meetu-engineering/create-your-custom-calendar-view-10ff41f39bfe

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //creates an instance of the database and opens it
        db = new Database(this);
        db.open();

        //sets the content view being the xml file
        setContentView(R.layout.activity_calendar_view);

        //finds the calendarView in xml file by id
        mCalendarView = findViewById(R.id.calendarView);

        //sets the listView via the id
        calendarAppointmentListView = (ListView) findViewById(R.id.calendar_appointment_list);

        //sets the variables to the respective list items
        calendarAppointmentTitleTV = findViewById(R.id.calendar_appointment_title);
        calendarAppointmentStartTimeTV = findViewById(R.id.calendar_appointment_start_time);
        calendarAppointmentEndTimeTV = findViewById(R.id.calendar_appointment_end_time);


        //TODO: Cannot seem to get the date to autoload the list/AND/OR refresh when pushing the back button
//        //values for current date loaded
//        int currentDateDay = Calendar.DAY_OF_MONTH;
//        int currentDateMonth = Calendar.MONTH;
//        int currentDateYear = Calendar.YEAR;
//
//        //Current Date StringBuilder
//        StringBuilder currentDateString = new StringBuilder();
//
//        //populate the StringBuilder with CurrentDate
//        currentDateString.append(currentDateMonth+1 + "-" + currentDateDay + "-" + currentDateYear);
//
//        //the list loaded
//        List<Appointment> currentDateAppointmentListCalendar = db.appointmentDAO.getAppointmentByStartDate(currentDateString.toString());
//
//        if (currentDateAppointmentListCalendar.size() > 0) {
//
//            ArrayAdapter<Appointment> currentDateAppointmentListCalendarDataAdapter = new ArrayAdapter<Appointment>(getApplicationContext(),
//                    android.R.layout.simple_list_item_1, currentDateAppointmentListCalendar);
//
//            calendarAppointmentListView.setAdapter(currentDateAppointmentListCalendarDataAdapter);
//        }

        //Floating action button for the bottom to create a new appointment

        mCalendarView.setOnDateChangeListener((CalendarView view, int year, int month, int dayOfMonth) -> {

            StringBuilder dateString = new StringBuilder();
            dateString.append(month+1 + "-" + dayOfMonth + "-" + year);

            //getting closer

            List<Appointment> appointmentListCalendar = db.appointmentDAO.getAppointmentByStartDate(dateString.toString());

            if (appointmentListCalendar.size() > 0) {
                {
                    ArrayAdapter<Appointment> appointmentListCalendarDataAdapter = new ArrayAdapter<Appointment>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, appointmentListCalendar);

                    calendarAppointmentListView.setAdapter(appointmentListCalendarDataAdapter);

                }
                calendarAppointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Appointment a = (Appointment) adapter.getItemAtPosition(position);
                        Intent intent = new Intent(getApplicationContext(), AppointmentEditorActivity.class);

                        intent.putExtra(AppointmentDetailFragment.ARG_APPOINTMENT_ID, a.getId());
                        startActivity(intent);
                    }
                });

            } else {
                ArrayAdapter<Appointment> appointmentListCalendarDataAdapter = new ArrayAdapter<Appointment>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, appointmentListCalendar);
                calendarAppointmentListView.setAdapter(appointmentListCalendarDataAdapter);
                Toast.makeText(getApplicationContext(), "There are no appointments on " + dateString, 0).show();// TODO Auto-generated method stub

            }
        });
    }

    /**
     * This method closes the database when called
     */
    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
