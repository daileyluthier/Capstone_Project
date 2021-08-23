package com.daileymichael.tattooassistantapp.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.Models.Appointment;
import com.daileymichael.tattooassistantapp.R;
import com.daileymichael.tattooassistantapp.UI.Fragments.AppointmentDetailFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

/**
 * TODO: Make the appointmentList show Tattoo Title, and time and maybe Name (filter list by time)
 */
public class AppointmentListActivity extends AppCompatActivity {
    private boolean mPane;
    public Database db;

    /**
     * This method sets the contentView and toolbar & floating action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        db = new Database(this);
        db.open();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentEditorActivity.class);
                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (findViewById(R.id.appointment_detail_container) != null) {
            mPane = true;
        }
        View recyclerView = findViewById(R.id.appointment_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // ID represents the Home or Up button; basically if this case activity, up button is shown
            // uses NavUtils to allow users to nav up a level in app structure.
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back  ***For my reference
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method sets up the recyclerView and allows for the modification for layout to be master/detail
     * @param recyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, db.appointmentDAO.getAppointments(), mPane));
    }

    /**
     * This method/class handles the items for the recyclerView using the RecyclerView.Adapter
     */
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final AppointmentListActivity mParentActivity;
        private final List<Appointment> mValues;
        private final boolean mPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment appointment = (Appointment) view.getTag();
                if (mPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(AppointmentDetailFragment.ARG_APPOINTMENT_ID, appointment.getId());

                    AppointmentDetailFragment fragment = new AppointmentDetailFragment();
                    fragment.setArguments(arguments);

                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.appointment_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AppointmentDetailActivity.class);

                    intent.putExtra(AppointmentDetailFragment.ARG_APPOINTMENT_ID, appointment.getId());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(AppointmentListActivity parent,
                                       List<Appointment> items,
                                       boolean pane) {
            mValues = items;
            mParentActivity = parent;
            mPane = pane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_appointment_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(position + 1));
            holder.mContentView.setText(String.valueOf(mValues.get(position)));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_title);
                mContentView = view.findViewById(R.id.id_start_time);
            }
        }
    }
}
