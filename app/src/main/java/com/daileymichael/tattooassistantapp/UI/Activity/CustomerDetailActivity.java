package com.daileymichael.tattooassistantapp.UI.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.Models.Customer;

import com.daileymichael.tattooassistantapp.R;
import com.daileymichael.tattooassistantapp.Receivers.AlarmReceiver;
import com.daileymichael.tattooassistantapp.UI.Fragments.CustomerDetailFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class CustomerDetailActivity extends AppCompatActivity {

    private Customer mSelectedCustomer;
    public Database db;

    /**
     * This method sets the contentView and toolbar & floating action items
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        db = new Database(this);
        db.open();

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedCustomerId = getIntent().getIntExtra(CustomerDetailFragment.ARG_CUSTOMER_ID, 0);
        mSelectedCustomer = db.customerDAO.getCustomerById(selectedCustomerId);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerDetailActivity.this, CustomerEditorActivity.class);
            intent.putExtra(CustomerDetailFragment.ARG_CUSTOMER_ID, selectedCustomerId);
            startActivity(intent);
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //fragments api guide: http://developer.android.com/guide/components/fragments.html ***For my reference
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putInt(CustomerDetailFragment.ARG_CUSTOMER_ID,
                    getIntent().getIntExtra(CustomerDetailFragment.ARG_CUSTOMER_ID, 0));

            CustomerDetailFragment fragment = new CustomerDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.customer_detail_container, fragment)
                    .commit();
        }
    }

    /**
     * This method inflates the menu to add items to the action bar if it is present
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    /**
     * This method closes the database when called.
     */
    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * This method handles action to take place based on the item selected
     *
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
            case R.id.action_reminder:

                String startNotificationTitle = "Customer Start Reminder";
                String startNotificationText = mSelectedCustomer.getName() + " begins today.";

                Intent startNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                startNotificationIntent.putExtra("mNotificationTitle", startNotificationTitle);
                startNotificationIntent.putExtra("mNotificationContent", startNotificationText);

                PendingIntent startPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, startNotificationIntent, 0);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                String endNotificationTitle = "Customer End Reminder";
                String endNotificationText = mSelectedCustomer.getName() + " ends today.";

                Intent endNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                endNotificationIntent.putExtra("mNotificationTitle", endNotificationTitle);
                endNotificationIntent.putExtra("mNotificationContent", endNotificationText);

                PendingIntent endPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, endNotificationIntent, 0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                Toast.makeText(this, "Reminder set for " + startNotificationText, Toast.LENGTH_SHORT).show();

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

                    Date startDate = dateFormat.parse(mSelectedCustomer.getPhone());
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(startDate);
                    startAlarmManager.set(AlarmManager.RTC_WAKEUP, startCal.getTimeInMillis(), startPendingIntent);

                    Date endDate = dateFormat.parse(mSelectedCustomer.getEmail());
                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(endDate);
                    endAlarmManager.set(AlarmManager.RTC_WAKEUP, endCal.getTimeInMillis(), endPendingIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_home:
                Intent iHome = new Intent(this, MainActivity.class);
                startActivity(iHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method directs to the AppointmentEditorActivity
     *
     * @param view
     */
    public void onAppointmentEdit(View view) {
        Intent intent = new Intent(this, AppointmentEditorActivity.class);
        intent.putExtra(CustomerDetailFragment.ARG_CUSTOMER_ID, mSelectedCustomer.getId());
        startActivity(intent);
    }
}
