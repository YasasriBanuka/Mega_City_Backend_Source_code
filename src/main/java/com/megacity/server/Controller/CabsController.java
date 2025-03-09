package com.megacity.server.Controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.megacity.server.Model.Cabs;
import com.megacity.server.Service.CabsService;
import com.megacity.server.Service.CloudinaryService;


@RestController
@CrossOrigin
public class CabsController {

    @Autowired
    private CabsService cabsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryService cloudinaryService;

        @PostMapping("/auth/createcab")
    public Cabs addCab(
        @RequestParam("imageUrl") MultipartFile file,
        @RequestParam("cabModel") String cabModel,
        @RequestParam("cabNumberPlate") String cabNumberPlate,
        @RequestParam("seats") Integer seats,
        @RequestParam("availabilityStatus") String availabilityStatus,
        @RequestParam("hasAC") Boolean hasAC,
        @RequestParam("luggageCapacity") String luggageCapacity,
        @RequestParam("driverName") String driverName,
        @RequestParam("driveremail") String driveremail,
        @RequestParam("password") String password,
        @RequestParam("licenseImage") MultipartFile licensefile,
        @RequestParam("driveImage") MultipartFile driverfile,
        @RequestParam("contactNumber") String contactNumber,
        @RequestParam("userName") String userName,
        @RequestParam("categoryId") String categoryId,
        @RequestParam("location") String location
    ) throws IOException {

        // Upload image to Cloudinary
        String imageUrl = cloudinaryService.uploadImage(file);
        String licenseImage = cloudinaryService.uploadImage(licensefile);
        String driveImage = cloudinaryService.uploadImage(driverfile);

        // Save cab details with image URL
        Cabs cab = new Cabs();
        cab.setCabModel(cabModel);
        cab.setCabNumberPlate(cabNumberPlate);
        cab.setSeats(seats);
        cab.setImgUrl(imageUrl);  // Save Cloudinary image URL
        cab.setAvailabilityStatus(availabilityStatus);
        cab.setHasAC(hasAC);
        cab.setLuggageCapacity(luggageCapacity);
        cab.setDriverName(driverName);
        cab.setDriveremail(driveremail);
        cab.setPassword(passwordEncoder.encode(password)); 
        cab.setLicenseImage(licenseImage);
        cab.setDriveImage(driveImage);  // Save Cloudinary image URL
        cab.setContactNumber(contactNumber);
        cab.setUserName(userName);
        cab.setCategoryId(categoryId);
        cab.setLocation(location);

        return cabsService.addCab(cab);
    }


    @PutMapping("/auth/updatecab/{id}")
    public Cabs updateCab(@PathVariable("id") String cabId, @RequestBody Cabs cab) {
    return cabsService.updateCab(cabId, cab);
    }

    @DeleteMapping("/deletecab/{id}")
    public String deleteCab(@PathVariable("id") String cabId) {
        cabsService.deleteCab(cabId);
        return "Cab with ID " + cabId + " has been deleted.";
    }

    @GetMapping("/auth/getcabbyid/{id}")
    public Cabs getCabById(@PathVariable("id") String cabId) {
        return cabsService.getCabById(cabId);
    }

   @GetMapping("/auth/getallCabs")
    public ResponseEntity<List<Map<String, Object>>> getAllCabs() {
        return ResponseEntity.ok(cabsService.getAllCabs());
    }

        @GetMapping("/getcabsbycatid/{categoryId}")
    public List<Cabs> getcabsById(@PathVariable String categoryId) {
        return cabsService.getcabsById(categoryId);
    }

    
}
