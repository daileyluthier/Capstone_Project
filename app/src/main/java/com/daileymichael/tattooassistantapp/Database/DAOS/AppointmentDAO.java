package com.daileymichael.tattooassistantapp.Database.DAOS;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.daileymichael.tattooassistantapp.Database.DbContentProvider;
import com.daileymichael.tattooassistantapp.Models.Appointment;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

/**
 *
 */
public class AppointmentDAO extends DbContentProvider implements AppointmentSchema, AppointmentDAOInterface {
    private Cursor cursor;
    private ContentValues initialValues;

    public AppointmentDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * This method adds to the DB the appointment information
     * @param appointment
     * @return
     */
    public boolean addAppointment(Appointment appointment) {
        setContentValue(appointment);
        try {
            return super.insert(TABLE_APPOINTMENTS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * @return
     */
    public List<Appointment> getAppointmentByCustomer(int customerId) {
        final String selectionArgs[] = { String.valueOf(customerId) };
        final String selection = APPOINTMENT_CUSTOMER_ID + " = ?";
        List<Appointment> appointmentList = new ArrayList<>();

        cursor = super.query(TABLE_APPOINTMENTS, APPOINTMENTS_COLUMNS, selection,
                selectionArgs, APPOINTMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Appointment appointment = cursorToEntity(cursor);
                appointmentList.add(appointment);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppointmentByStartDate(String startDate) {
        final String selectionArgs[] = { String.valueOf(startDate) };
        final String selection = APPOINTMENT_START_DATE + " = ?";
        List<Appointment> appointmentList = new ArrayList<>();

        cursor = super.query(TABLE_APPOINTMENTS, APPOINTMENTS_COLUMNS, selection,
                selectionArgs, APPOINTMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Appointment appointment = cursorToEntity(cursor);
                appointmentList.add(appointment);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return appointmentList;
    }

    /**
     * @return
     */
    public Appointment getAppointmentById(int appointmentId) {
        final String selectionArgs[] = { String.valueOf(appointmentId) };
        final String selection = APPOINTMENT_ID + " = ?";

        Appointment appointment = null;

        cursor = super.query(TABLE_APPOINTMENTS, APPOINTMENTS_COLUMNS, selection,
                selectionArgs, APPOINTMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                appointment = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return appointment;
    }

    /**
     * @return
     */
    public int getAppointmentCount() {
        List<Appointment> appointmentList = getAppointments();
        return appointmentList.size();
    }

    /**
     * @return
     */
    public List<Appointment> getAppointments() {
        List<Appointment> appointmentList = new ArrayList<>();

        cursor = super.query(TABLE_APPOINTMENTS, APPOINTMENTS_COLUMNS, null,
                null, APPOINTMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Appointment appointment = cursorToEntity(cursor);
                appointmentList.add(appointment);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return appointmentList;
    }

    /**
     * @param appointment
     * @return
     */
    public boolean removeAppointment(Appointment appointment) {
        final String selectionArgs[] = { String.valueOf(appointment.getId()) };
        final String selection = APPOINTMENT_ID + " = ?";
        return super.delete(TABLE_APPOINTMENTS, selection, selectionArgs) > 0;
    }

    public boolean removeCustomerAppointment(Appointment appointment) {
        final String selectionArgs[] = { String.valueOf(appointment.getCustomerId()) };
        final String selection = APPOINTMENT_CUSTOMER_ID + " = ?";
        return super.delete(TABLE_APPOINTMENTS, selection, selectionArgs) > 0;
    }

    /**
     * This method updates your appointment
     * @param appointment
     * @return
     */
    public boolean updateAppointment(Appointment appointment) {
        final String selectionArgs[] = { String.valueOf(appointment.getId()) };
        final String selection = APPOINTMENT_ID + " = ?";

        setContentValue(appointment);
        try {
            return super.update(TABLE_APPOINTMENTS, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    @Override
    public int getMonthlyAppointmentCount(String month) {
        final String selectionArgs[] = { "LIKE " + String.valueOf(month) +"%" };
        final String selection = APPOINTMENT_START_DATE + " = ?";
        List<Appointment> appointmentList = new ArrayList<>();

        cursor = super.countQuery(TABLE_APPOINTMENTS, APPOINTMENT_START_DATE,"\"" + String.valueOf(month) + "%\"");
//        cursor = super.query(TABLE_APPOINTMENTS, APPOINTMENTS_COLUMNS, selection,
//                selectionArgs, APPOINTMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Appointment appointment = cursorToEntity(cursor);
                appointmentList.add(appointment);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return appointmentList.size();
    }

    /**
     * @param cursor
     * @return
     */
    protected Appointment cursorToEntity(Cursor cursor) {
        int appointmentIdIdx;
        int appointmentTitleIdx;
        int appointmentDescriptionIdx;
        int appointmentPriceIdx;
        int appointmentStartDateIdx;
        int appointmentStartTimeIdx;
        int appointmentEndTimeIdx;
        int appointmentCustomerIdIdx;
        int appointmentId = -1;
        String appointmentTitle = "";
        String appointmentDescription = "";
        String appointmentPrice = "";
        String appointmentStartDate = "";
        String appointmentStartTime = "";
        String appointmentEndTime = "";
        int appointmentCustomerId = -1;

        if (cursor != null) {
            if (cursor.getColumnIndex(APPOINTMENT_ID) != -1) {
                appointmentIdIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_ID);
                appointmentId = cursor.getInt(appointmentIdIdx);
            }
            if (cursor.getColumnIndex(APPOINTMENT_TITLE) != -1) {
                appointmentTitleIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_TITLE);
                appointmentTitle = cursor.getString(appointmentTitleIdx);
            }
            if (cursor.getColumnIndex(APPOINTMENT_PRICE) != -1) {
                appointmentPriceIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_PRICE);
                appointmentPrice = cursor.getString(appointmentPriceIdx);
            }
            if (cursor.getColumnIndex(APPOINTMENT_START_DATE) != -1) {
                appointmentStartDateIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_START_DATE);
                appointmentStartDate = cursor.getString(appointmentStartDateIdx);
            }
            if (cursor.getColumnIndex(APPOINTMENT_START_TIME) != -1) {
                appointmentStartTimeIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_START_TIME);
                appointmentStartTime = cursor.getString(appointmentStartTimeIdx);
            }
            if (cursor.getColumnIndex(APPOINTMENT_END_TIME) != -1) {
                appointmentEndTimeIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_END_TIME);
                appointmentEndTime = cursor.getString(appointmentEndTimeIdx);
            }
            if (cursor.getColumnIndex(APPOINTMENT_DESCRIPTION) != -1) {
                appointmentDescriptionIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_DESCRIPTION);
                appointmentDescription = cursor.getString(appointmentDescriptionIdx);
            }
            if (cursor.getColumnIndex(APPOINTMENT_CUSTOMER_ID) != -1) {
                appointmentCustomerIdIdx = cursor.getColumnIndexOrThrow(APPOINTMENT_CUSTOMER_ID);
                appointmentCustomerId = cursor.getInt(appointmentCustomerIdIdx);
            }
        }
        return new Appointment(appointmentId, appointmentTitle, appointmentStartDate, appointmentStartTime, appointmentEndTime, appointmentDescription, appointmentPrice, appointmentCustomerId);
    }
    /**
     * This method sets instance variables for insert/update into the database
     * @param appointment
     */
    private void setContentValue(Appointment appointment) {
        initialValues = new ContentValues();
        initialValues.put(APPOINTMENT_ID, appointment.getId());
        initialValues.put(APPOINTMENT_TITLE, appointment.getTitle());
        initialValues.put(APPOINTMENT_START_DATE, appointment.getStartDate());
        initialValues.put(APPOINTMENT_START_TIME, appointment.getStartTime());
        initialValues.put(APPOINTMENT_END_TIME, appointment.getEndTime());
        initialValues.put(APPOINTMENT_DESCRIPTION, appointment.getDescription());
        initialValues.put(APPOINTMENT_PRICE, appointment.getPrice());
        initialValues.put(APPOINTMENT_CUSTOMER_ID, appointment.getCustomerId());
    }

    /**
     *
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
