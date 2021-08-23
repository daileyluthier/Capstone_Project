package com.daileymichael.tattooassistantapp.Database.DAOS;

import com.daileymichael.tattooassistantapp.Models.Customer;

import java.util.List;

public interface CustomerDAOInterface {

    boolean addCustomer(Customer customer);

    Customer getCustomerById(int customerId);

    int getCustomerCount();

    List<Customer> getCustomers();

    boolean removeCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
}
