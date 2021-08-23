

package com.daileymichael.tattooassistantapp.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * TODO: Modify the toString method to include different parameters
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String price;

    //date time
    private String startDate;
    private String startTime;
    private String endTime;

    //Customer id
    private int customerId;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    /**
     * This method builds an Appointment instance

     */
    public Appointment(int id, String title, String startDate, String startTime, String endTime, String description, String price, int customerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.startTime = startTime;

        this.endTime = endTime;
        this.customerId = customerId;

    }

    /**
     * This method gets the appointment ID
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method gets the appointment title
     * @return name
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method returns description
     * @return type
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This method get an appointment's date
     * @return date
     */
    public String getStartDate() {
        return this.startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * This method get the related customer ID.
     * @return associated customer ID
     */
    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCustomerId() {
        return this.customerId = customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * This method creates a string representation of an assessment
     * @return string repr
     */
    @Override
    public String toString() {
        return String.format(this.startDate + " " + this.startTime, dateFormat) + "   " + this.title;
    }

    /**
     * This is the validation method
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || description.isEmpty() || price.isEmpty() || startDate.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            return false;
        }
        try {
            //Are the dates in the correct format?
            dateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        try {
            //Are the times in the correct format?
            timeFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        try {
            //Are the times in the correct format?
            timeFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        return true;
    }
}

