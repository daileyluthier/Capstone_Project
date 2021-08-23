package com.daileymichael.tattooassistantapp.Database.DAOS;

public interface CustomerSchema {

    String TABLE_CUSTOMERS = "customers";
    String CUSTOMER_ID = "id";
    String CUSTOMER_NAME = "customer_name";
    String CUSTOMER_PHONE = "customer_phone";
    String CUSTOMER_EMAIL = "customer_email";

    String[] CUSTOMER_COLUMNS = {CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_PHONE, CUSTOMER_EMAIL};

    String CUSTOMERS_CREATE =
            "CREATE TABLE " + TABLE_CUSTOMERS + " (" +
                    CUSTOMER_ID + " INTEGER PRIMARY KEY, " +
                    CUSTOMER_NAME + " TEXT, " +
                    CUSTOMER_PHONE + " TEXT, " +
                    CUSTOMER_EMAIL + " TEXT " +
                    ")";
}
