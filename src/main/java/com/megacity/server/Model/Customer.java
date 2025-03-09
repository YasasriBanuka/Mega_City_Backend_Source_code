package com.megacity.server.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {

    @Id
    private String customerId;

    private String customerName;

    private String customerAddress;

    private String customerEmail;

    private String userName;

    private String password;

    private String customerPhone;
    
}
