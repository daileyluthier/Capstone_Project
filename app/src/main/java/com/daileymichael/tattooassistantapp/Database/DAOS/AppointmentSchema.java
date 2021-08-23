package com.daileymichael.tattooassistantapp.Database.DAOS;

/**
 *
 */
public interface AppointmentSchema {

    String TABLE_APPOINTMENTS = "appointments";
    String APPOINTMENT_ID = "id";
    String APPOINTMENT_TITLE = "appointment_title";
    String APPOINTMENT_START_DATE = "appointment_start_date";
    String APPOINTMENT_START_TIME = "appointment_start_time";
    String APPOINTMENT_END_TIME = "appointment_end_time";
    String APPOINTMENT_DESCRIPTION = "appointment_description";
    String APPOINTMENT_PRICE = "appointment_price";
    String APPOINTMENT_CUSTOMER_ID = "appointment_customer_id";

    String[] APPOINTMENTS_COLUMNS = {APPOINTMENT_ID, APPOINTMENT_TITLE, APPOINTMENT_START_DATE, APPOINTMENT_START_TIME, APPOINTMENT_END_TIME, APPOINTMENT_DESCRIPTION, APPOINTMENT_PRICE, APPOINTMENT_CUSTOMER_ID};

    String APPOINTMENTS_CREATE =
            "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                    APPOINTMENT_ID + " INTEGER PRIMARY KEY, " +
                    APPOINTMENT_TITLE + " TEXT, " +
                    APPOINTMENT_DESCRIPTION + " TEXT, " +
                    APPOINTMENT_PRICE + " TEXT, " +
                    APPOINTMENT_START_DATE + " TEXT, " +
                    APPOINTMENT_START_TIME + " TEXT, " +
                    APPOINTMENT_END_TIME + " TEXT, " +
                    APPOINTMENT_CUSTOMER_ID + " INTEGER " +
                    ")";
}
