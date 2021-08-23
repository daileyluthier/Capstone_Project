package com.daileymichael.tattooassistantapp.Database.DAOS;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;


import com.daileymichael.tattooassistantapp.Database.DbContentProvider;
import com.daileymichael.tattooassistantapp.Models.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CustomerDAO extends DbContentProvider implements CustomerSchema, CustomerDAOInterface {
    private Cursor cursor;
    private ContentValues initialValues;

    public CustomerDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * This method adds to the DB the customer
     * @param customer
     * @return
     */
    public boolean addCustomer(Customer customer) {
        setContentValue(customer);
        try {
            return super.insert(TABLE_CUSTOMERS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     *
     * @param customerId
     * @return
     */
    public Customer getCustomerById(int customerId) {
        final String selectionArgs[] = { String.valueOf(customerId) };
        final String selection = CUSTOMER_ID + " = ?";

        Customer customer = null;

        cursor = super.query(TABLE_CUSTOMERS, CUSTOMER_COLUMNS, selection,
                selectionArgs, CUSTOMER_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customer = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return customer;
    }

    /**
     *
     * @return
     */
    public int getCustomerCount() {
        List<Customer> customerList = getCustomers();
        return customerList.size();
    }

    /**
     * yea
     * @return
     */
    public List<Customer> getCustomers() {
        List<Customer> customerList = new ArrayList<>();

        cursor = super.query(TABLE_CUSTOMERS, CUSTOMER_COLUMNS, null,
                null, CUSTOMER_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Customer customer = cursorToEntity(cursor);
                customerList.add(customer);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return customerList;
    }

    /**
     *
     * @param customer
     * @return
     */
    public boolean removeCustomer(Customer customer) {
        final String selectionArgs[] = { String.valueOf(customer.getId()) };
        final String selection = CUSTOMER_ID + " = ?";
        return super.delete(TABLE_CUSTOMERS, selection, selectionArgs) > 0;
    }

    /**
     * This method updates the customer
     *
     * @param customer
     * @return
     */
    public boolean updateCustomer(Customer customer) {
        final String selectionArgs[] = { String.valueOf(customer.getId()) };
        final String selection = CUSTOMER_ID + " = ?";

        setContentValue(customer);
        try {
            return super.update(TABLE_CUSTOMERS, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     *
     * @param cursor
     * @return
     */
    protected Customer cursorToEntity(Cursor cursor) {
        int customerIdIdx;
        int customerNameIdx;
        int customerPhoneIdx;
        int customerEmailIdx;
        int customerId = -1;
        String customerName = "";
        String customerPhone = "";
        String customerEmail = "";

        if (cursor != null) {
            if (cursor.getColumnIndex(CUSTOMER_ID) != -1) {
                customerIdIdx = cursor.getColumnIndexOrThrow(CUSTOMER_ID);
                customerId = cursor.getInt(customerIdIdx);
            }
            if (cursor.getColumnIndex(CUSTOMER_NAME) != -1) {
                customerNameIdx = cursor.getColumnIndexOrThrow(CUSTOMER_NAME);
                customerName = cursor.getString(customerNameIdx);
            }
            if (cursor.getColumnIndex(CUSTOMER_PHONE) != -1) {
                customerPhoneIdx = cursor.getColumnIndexOrThrow(CUSTOMER_PHONE);
                customerPhone = cursor.getString(customerPhoneIdx);
            }
            if (cursor.getColumnIndex(CUSTOMER_EMAIL) != -1) {
                customerEmailIdx = cursor.getColumnIndexOrThrow(CUSTOMER_EMAIL);
                customerEmail = cursor.getString(customerEmailIdx);
            }
        }
        return new Customer(customerId, customerName, customerPhone, customerEmail);
    }

    /**
     *
     * @param customer
     */
    private void setContentValue(Customer customer) {
        initialValues = new ContentValues();
        initialValues.put(CUSTOMER_ID, customer.getId());
        initialValues.put(CUSTOMER_NAME, customer.getName());
        initialValues.put(CUSTOMER_PHONE, customer.getPhone());
        initialValues.put(CUSTOMER_EMAIL, customer.getEmail());
    }

    /**
     *
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
