package com.daileymichael.tattooassistantapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.daileymichael.tattooassistantapp.Database.DAOS.AppointmentDAO;
import com.daileymichael.tattooassistantapp.Database.DAOS.AppointmentSchema;
import com.daileymichael.tattooassistantapp.Database.DAOS.CustomerDAO;
import com.daileymichael.tattooassistantapp.Database.DAOS.CustomerSchema;

/**
 *
 */
public class Database {
    private static final String DATABASE_NAME = "TattooHelper.db";
    private static final int DATABASE_VERSION = 7;

    private DatabaseHelper mDbHelper;
    private final Context mContext;

    public static CustomerDAO customerDAO;
    public static AppointmentDAO appointmentDAO;


    /**
     * @param context
     */
    public Database(Context context) {
        this.mContext = context;
    }

    /**
     * @return
     */
    public Database open() {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();


        customerDAO = new CustomerDAO(mDb);
        appointmentDAO = new AppointmentDAO(mDb);

        return this;
    }


    /**
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CustomerSchema.CUSTOMERS_CREATE);
            db.execSQL(AppointmentSchema.APPOINTMENTS_CREATE);

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int o, int n) {

            db.execSQL("DROP TABLE IF EXISTS " + CustomerSchema.TABLE_CUSTOMERS);
            db.execSQL("DROP TABLE IF EXISTS " + AppointmentSchema.TABLE_APPOINTMENTS);

            onCreate(db);
        }
    }
}
