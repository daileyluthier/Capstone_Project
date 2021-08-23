package com.daileymichael.tattooassistantapp.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *
 */
public class AlarmReceiver extends BroadcastReceiver {
    private String CHANNEL_ID = "app_notification_channel";
    private String CHANNEL_DESC = "Tattoo Assistant";

        public static final String EXTRA_APPOINTMENT_TITLE    =  "com.daileymichael.wgutracker.APPOINTMENT_TITLE";
        public static final String EXTRA_APPOINTMENT_DESCRIPTION    =  "com.daileymichael.wgutracker.APPOINTMENT_DESCRIPTION";
        public static final String EXTRA_APPOINTMENT_START_DATE = "com.daileymichael.wgutracker.APPOINTMENT_START_DATE";
        public static final String EXTRA_APPOINTMENT_START_TIME = "com.daileymichael.wgutracker.APPOINTMENT_START_TIME";
        public static final String EXTRA_APPOINTMENT_ID    =  "com.daileymichael.wgutracker.APPOINTMENT_ID";

        @Override
        public void onReceive(Context context, Intent intent) {
            String appointmentTitle = intent.getStringExtra(EXTRA_APPOINTMENT_TITLE);
            String appointmentDescription = intent.getStringExtra(EXTRA_APPOINTMENT_DESCRIPTION);
            int appointmentId = intent.getIntExtra(EXTRA_APPOINTMENT_ID, 0);

            AppointmentReminderNotification.notify(context, appointmentTitle, appointmentDescription, appointmentId);
        }
    }

