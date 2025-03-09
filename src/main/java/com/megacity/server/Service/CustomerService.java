package com.megacity.server.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.megacity.server.Model.Customer;

@Service
public interface CustomerService {

    Customer addCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(String customerId);
    void deleteCustomer(String customerId);
    Customer updateCustomer(String customerId, Customer customer);
    public boolean existsByUsername(String userName);

    
}