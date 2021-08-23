package com.daileymichael.tattooassistantapp.UI.Fragments;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.Models.Customer;
import com.daileymichael.tattooassistantapp.R;

import com.daileymichael.tattooassistantapp.Models.Appointment;
import com.google.android.material.appbar.CollapsingToolbarLayout;


/**
 *
 */
public class AppointmentDetailFragment extends Fragment {
    public static final String ARG_APPOINTMENT_ID = "com.example.daileymichael.WGUTracker.appoitmentdetailfragment.appointmentid";
    private Appointment mAppointment;
    private Customer mCustomer;
    private int mCustomerId;
    public Database db;

    public AppointmentDetailFragment() {
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(getContext());
        db.open();

        if (getArguments().containsKey(ARG_APPOINTMENT_ID)) {
            mAppointment = db.appointmentDAO.getAppointmentById(getArguments().getInt(ARG_APPOINTMENT_ID));

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mAppointment.getTitle());
            }
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
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_appointment_detail, container, false);

        if (mAppointment != null) {
            mCustomer = db.customerDAO.getCustomerById(mAppointment.getCustomerId());
            ((TextView) rootView.findViewById(R.id.appointment_customer_field)).setText(mCustomer.getName());
            ((TextView) rootView.findViewById(R.id.title_field)).setText(mAppointment.getTitle());
            ((TextView) rootView.findViewById(R.id.appointment_description_field)).setText(mAppointment.getDescription());
            ((TextView) rootView.findViewById(R.id.appointment_price_field)).setText(mAppointment.getPrice());
            ((TextView) rootView.findViewById(R.id.appointment_start_date_field)).setText(mAppointment.getStartDate());
            ((TextView) rootView.findViewById(R.id.appointment_start_time_field)).setText(mAppointment.getStartTime());
            ((TextView) rootView.findViewById(R.id.appointment_end_time_field)).setText(mAppointment.getEndTime());
        }
        return rootView;
    }
}
