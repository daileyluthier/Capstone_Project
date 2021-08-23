package com.daileymichael.tattooassistantapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.MenuItem;

import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.R;
import com.google.android.material.navigation.NavigationView;

/**
 *Capstone project by Michael Dailey
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    public Database db;

    /**
     * This method sets the contentView and toolbar action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * This method inflates the menu to add items to the action bar if it is present
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * @param view
     */
    public void showCalendar(View view) {
        Intent intent = new Intent(this, CalendarViewActivity.class);
        startActivity(intent);
    }

    /**
     *
     * * @param view
     */
    public void showCustomers(View view) {
        Intent intent = new Intent(this, CustomerEditorActivity.class);
        startActivity(intent);
    }

    /**
     * This method directs the user to the show the terms using TermListActivity
     * @param view
     */
    public void showAppointments(View view) {
        Intent intent = new Intent(this, AppointmentEditorActivity.class);
        startActivity(intent);
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
            case R.id.action_settings:
                Intent intent = new Intent(this, ReportsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method enables the sidebar navigation
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_customers:
                Intent nCustomers = new Intent(MainActivity.this, CustomerListActivity.class);
                startActivity(nCustomers);
                break;
            case R.id.nav_appointments:
                Intent nAppointments = new Intent(MainActivity.this, AppointmentListActivity.class);
                startActivity(nAppointments);
                break;
            case R.id.nav_calendar:
                Intent intent = new Intent(this, CalendarViewActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_reports:
                Intent nReports = new Intent(MainActivity.this, ReportsActivity.class);
                startActivity(nReports);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
