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
@Document(collection = "admin")
public class Admin {

    @Id
    private String adminId;
    
    private String userName;

    private String password;

    private String adminEmail;

    private String adminImage;


}
