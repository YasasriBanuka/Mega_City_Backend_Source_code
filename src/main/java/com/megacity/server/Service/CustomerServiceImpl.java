package com.megacity.server.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacity.server.Model.Customer;
import com.megacity.server.Repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public boolean existsByUsername(String userName) {
        return customerRepository.existsByUserName(userName);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
public Customer updateCustomer(String customerId, Customer customer) {
    return customerRepository.findById(customerId).map(existingCustomer -> {
        // Update only the fields that are provided in the request
        if (customer.getCustomerName() != null) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }
        if (customer.getCustomerAddress() != null) {
            existingCustomer.setCustomerAddress(customer.getCustomerAddress());
        }
        if (customer.getCustomerEmail() != null) {
            existingCustomer.setCustomerEmail(customer.getCustomerEmail());
        }
        if (customer.getUserName() != null) {
            existingCustomer.setUserName(customer.getUserName());
        }
        if (customer.getPassword() != null) {
            existingCustomer.setPassword(customer.getPassword());
        }
        if (customer.getCustomerPhone() != null) {
            existingCustomer.setCustomerPhone(customer.getCustomerPhone());
        }
        return customerRepository.save(existingCustomer);
    }).orElseThrow(() -> new RuntimeException("Customer not found"));
}
 
    
}
