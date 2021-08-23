package com.daileymichael.tattooassistantapp.UI.Activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.Models.Appointment;
import com.daileymichael.tattooassistantapp.Models.Customer;
import com.daileymichael.tattooassistantapp.R;

import com.daileymichael.tattooassistantapp.UI.Fragments.AppointmentDetailFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * TODO: Make the format displayed in app for time to be hh:mm not military time with ending taken off
 */
public class AppointmentDetailActivity extends AppCompatActivity {
    public static final String APPOINTMENT_ID = "com.example.daileymichael.WGUTracker.appoitmentdetailactivity.appointmentid";
    private Appointment mSelectedAppointment;
    private Customer mSelectedCustomer;
    public Database db;
    EditText editStartDate;
    EditText editStartTime;
    EditText editEndTime;
    EditText editTitle;
    EditText editDescription;

    /**
     * This method sets the contentView and toolbar & floating action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedAppointmentId = getIntent().getIntExtra(AppointmentDetailFragment.ARG_APPOINTMENT_ID, 0);
        mSelectedAppointment = db.appointmentDAO.getAppointmentById(selectedAppointmentId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AppointmentDetailActivity.this, AppointmentEditorActivity.class);
            intent.putExtra(AppointmentDetailFragment.ARG_APPOINTMENT_ID, selectedAppointmentId);
            startActivity(intent);
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //fragments api guide: http://developer.android.com/guide/components/fragments.html ***To learn more about fragments in mobile
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(AppointmentDetailFragment.ARG_APPOINTMENT_ID,
                    getIntent().getIntExtra(AppointmentDetailFragment.ARG_APPOINTMENT_ID, 0));

            AppointmentDetailFragment fragment = new AppointmentDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.appointment_detail_container, fragment)
                    .commit();
        }

        editStartDate = (EditText) findViewById(R.id.appointment_start_date);
        editStartTime = (EditText) findViewById(R.id.appointment_start_time);
        editEndTime = (EditText) findViewById(R.id.appointment_end_time);
        editTitle = (EditText) findViewById(R.id.appointment_title);
        editDescription = (EditText) findViewById(R.id.appointment_description);
    }

    /**
     * This method inflates the menu to add items to the action bar if it is present
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
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
     * This method handles action to take place based on the item selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // ID represents the Home or Up button; basically if this case activity, up button is shown
                // uses NavUtils to allow users to nav up a level in app structure.
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back  ***For my reference
                NavUtils.navigateUpTo(this, new Intent(this, AppointmentListActivity.class));
                return true;
            case R.id.action_send_sms:
                sendSms();
                return true;
            case R.id.action_send_email:
                sendEmail();
                return true;
            case R.id.action_home:
                Intent iHome = new Intent(this, MainActivity.class);
                startActivity(iHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void sendToCalendar() {
        mSelectedCustomer = db.customerDAO.getCustomerById(mSelectedAppointment.getCustomerId());
        String title = mSelectedAppointment.getTitle();
        String email = mSelectedCustomer.getEmail();
        String phone = mSelectedCustomer.getPhone();
        String name = mSelectedCustomer.getName();
        String description = mSelectedAppointment.getDescription();
        String startDate = mSelectedAppointment.getStartDate();
        String startTime = mSelectedAppointment.getStartTime();
        String endTime = mSelectedAppointment.getEndTime();

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("title", name + " " + title);
        intent.putExtra("description", description);
        intent.putExtra("email", email);
        intent.putExtra("beginDate", startDate + startTime);
        intent.putExtra("beginTime", startTime);
        intent.putExtra("endTime", endTime);
        startActivity(intent);
    }

    private void sendSms() {
        mSelectedCustomer = db.customerDAO.getCustomerById(mSelectedAppointment.getCustomerId());
        String title = mSelectedAppointment.getTitle();
        String email = mSelectedCustomer.getEmail();
        String phone = mSelectedCustomer.getPhone();
        String name = mSelectedCustomer.getName();
        String description = mSelectedAppointment.getDescription();
        String startDate = mSelectedAppointment.getStartDate();
        String startTime = mSelectedAppointment.getStartTime();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.fromParts("sms", phone, null));
//        intent.putExtra("sms_to", phone);
        intent.putExtra("sms_body", "Reminder for " + " " + name + " " + "your appointment starts at " + startTime + " on " + startDate);

        startActivity(intent);
    }

    private void sendEmail() {
        mSelectedCustomer = db.customerDAO.getCustomerById(mSelectedAppointment.getCustomerId());
        String title = mSelectedAppointment.getTitle();
        String email = mSelectedCustomer.getEmail();
        String description = mSelectedAppointment.getDescription();
        String startDate = mSelectedAppointment.getStartDate();
        String startTime = mSelectedAppointment.getStartTime();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.CATEGORY_APP_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, title + " " + email);
        intent.putExtra(Intent.EXTRA_TEXT, description + " " + startDate + " " + startTime + " ");
        startActivity(intent);
    }
}
