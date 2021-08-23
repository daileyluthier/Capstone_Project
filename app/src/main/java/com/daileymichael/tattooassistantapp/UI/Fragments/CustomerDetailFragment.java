package com.daileymichael.tattooassistantapp.UI.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.daileymichael.tattooassistantapp.Database.Database;

import com.daileymichael.tattooassistantapp.Models.Appointment;
import com.daileymichael.tattooassistantapp.Models.Customer;

import com.daileymichael.tattooassistantapp.R;
import com.daileymichael.tattooassistantapp.UI.Activity.AppointmentEditorActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

/**
 *
 */
public class CustomerDetailFragment extends Fragment {
    public static final String ARG_CUSTOMER_ID = "com.example.daileymichael.WGUTracker.customerdetailfragment.customerid";
    private Customer mCustomer;
    public Database db;

    public CustomerDetailFragment() {
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

        if (getArguments().containsKey(ARG_CUSTOMER_ID)) {
            mCustomer = db.customerDAO.getCustomerById(getArguments().getInt(ARG_CUSTOMER_ID));

            Activity activity = this.getActivity();
            //for using master/detail layout
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mCustomer.getName());
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
     * This method set the customer list height
     * @param adapter
     * @param lv
     */
    private void setListHeight(ArrayAdapter adapter, ListView lv) {
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (lv.getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();
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
        View rootView = inflater.inflate(R.layout.content_customer_detail, container, false);

        if (mCustomer != null) {
            ((TextView) rootView.findViewById(R.id.customer_name_field)).setText(mCustomer.getName());
            ((TextView) rootView.findViewById(R.id.customer_phone_field)).setText(mCustomer.getPhone());
            ((TextView) rootView.findViewById(R.id.customer_email_field)).setText(mCustomer.getEmail());

            final int mCustomerId = mCustomer.getId();

            List<Appointment> appointmentList = db.appointmentDAO.getAppointmentByCustomer(mCustomerId);

            if (appointmentList.size() > 0) {
                rootView.findViewById(R.id.appointment_list).setVisibility(View.VISIBLE);

                ListView appointmentListView = rootView.findViewById(R.id.appointment_list);
                ArrayAdapter<Appointment> appointmentListDataAdapter = new ArrayAdapter<Appointment>(getActivity(),
                        android.R.layout.simple_list_item_1, appointmentList);

                appointmentListView.setAdapter(appointmentListDataAdapter);

                appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Appointment a = (Appointment) adapter.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(), AppointmentEditorActivity.class);

                        intent.putExtra(AppointmentDetailFragment.ARG_APPOINTMENT_ID, a.getId());
                        startActivity(intent);
                    }
                });
                setListHeight(appointmentListDataAdapter, appointmentListView);
            } else {
                rootView.findViewById(R.id.appointment_list).setVisibility(View.GONE);
            }
        }
        return rootView;
    }
}
