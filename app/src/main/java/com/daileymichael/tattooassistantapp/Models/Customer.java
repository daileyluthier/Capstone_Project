package com.daileymichael.tattooassistantapp.Models;

/**
 *
 */
public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;

    /**
     * @param id
     * @param name
     * @param phone
     * @param email
     */
    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * This method gets the course's ID
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method gets the course's title
     * @return title
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method gets the course start date
     * @return starDate
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * This method gets the course end date
     * @return endDate
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * This method creates a string representation of the term duration
     * @return
     */
    public String getInfo() {
        return phone + " to " + email;
    }

    /**
     * This method creates a string representation
     * @return
     */
    @Override
    public String toString() {
        return name + " (" + getInfo() + ")";
    }

    /**
     * This method validates the course dates
     * @return
     */
    public boolean isValid() {
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            return false;
        }
        return true;
    }
}
