package com.megacity.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.megacity.server.Model.Customer;
import com.megacity.server.Service.CustomerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/createCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        // Check if the username already exists
        if (customerService.existsByUsername(customer.getUserName())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already taken. Please choose another one.");
        }

        // Encode the password and save the customer
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/getallcustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/auth/getcustromerbyid/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/updatecustomer/{customerId}")
public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
    return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
}

    @DeleteMapping("/deletecustomer/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/auth/checkUsername")
public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(@RequestParam String userName) {
    boolean exists = customerService.existsByUsername(userName);
    return ResponseEntity.ok().body(Map.of("exists", exists));
}


}
