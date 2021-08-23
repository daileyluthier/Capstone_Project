package com.daileymichael.tattooassistantapp.Database.DAOS;

import com.daileymichael.tattooassistantapp.Models.Appointment;

import java.util.List;

/**
 *
 */
public interface AppointmentDAOInterface {
    boolean addAppointment(Appointment appointment);

    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAppointmentByCustomer(int customerId);

    List<Appointment> getAppointmentByStartDate(String startDate);

    int getAppointmentCount();

    int getMonthlyAppointmentCount(String month);

    List<Appointment> getAppointments();

    boolean removeAppointment(Appointment appointment);

    boolean removeCustomerAppointment(Appointment appointment);


    boolean updateAppointment(Appointment appointment);
}
