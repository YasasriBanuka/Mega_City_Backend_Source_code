package com.megacity.server.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document( collection = "cabs")
public class Cabs {
    
    @Id
    private String cabId;

    private String cabModel;

    private String cabNumberPlate;

    private Integer seats;

    private String imgUrl;

    private String availabilityStatus;

    private Boolean hasAC;

    private String luggageCapacity;

    private String driverName;

    private String driveremail;

    private String password;

    private String licenseImage;

    private String driveImage;

    private String contactNumber;

    private String userName;

    private String categoryId;

    private String location;

}
