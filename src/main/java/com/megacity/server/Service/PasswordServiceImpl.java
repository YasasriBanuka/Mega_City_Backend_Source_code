package com.megacity.server.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.megacity.server.Model.Customer;
import com.megacity.server.Model.OtpStorage;
import com.megacity.server.Repository.CustomerRepository;
import com.megacity.server.Repository.OtpStorageRepository;


@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OtpStorageRepository otpStorageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final int OTP_EXPIRATION_MINUTES = 5;

    @Override
    public ResponseEntity<?> sendPasswordResetOtp(String customerEmail) {
        Optional<Customer> customerOtp = customerRepository.findByUserName(customerEmail);

        if(!customerOtp.isPresent()){
            return ResponseEntity.badRequest().body("Email not found");
        }

        String otp = generateOTP();
        Instant expiryTime = Instant.now().plusSeconds(OTP_EXPIRATION_MINUTES * 60);

        OtpStorage otpStorage = new OtpStorage(customerEmail, otp, expiryTime);
        otpStorageRepository.save(otpStorage);

        String emailBody = "Your OTP for password rest is: " + otp + "\nThis OTP will expire in " + OTP_EXPIRATION_MINUTES + " minutes.";
        emailService.sendEmail(customerEmail, "Password Reset OTP", emailBody);

        return ResponseEntity.ok("OTP sent to your email");
    }

    @Override
    public ResponseEntity<?> resetPassword(String email, String otp, String newPassword) {
        Optional<OtpStorage> otpStorageOpt = otpStorageRepository.findByEmail(email);

        if (!otpStorageOpt.isPresent()) {
            System.out.println("No OTP request found for email: " + email);
            return ResponseEntity.badRequest().body("No OTP request found");
        }

        OtpStorage otpStorage = otpStorageOpt.get();

        if (Instant.now().isAfter(otpStorage.getExpiryTime())) {
            System.out.println("OTP expired for email: " + email);
            otpStorageRepository.deleteByEmail(email);
            return ResponseEntity.badRequest().body("OTP has expired");
        }

        if (!otpStorage.getOtp().equals(otp)) {
            System.out.println("Invalid OTP for email: " + email);
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        if (!isValidPassword(newPassword)) {
            System.out.println("Invalid password for email: " + email);
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long, include a number, and an uppercase letter.");
        }

        customerRepository.findByUserName(email).ifPresent(customer -> {
            customer.setPassword(passwordEncoder.encode(newPassword));
            customerRepository.save(customer);
            System.out.println("Password updated for customer: " + email);
        });


        otpStorageRepository.deleteByEmail(email);
        System.out.println("OTP deleted for email: " + email);

        return ResponseEntity.ok("Password updated successfully");
    }

    private String generateOTP(){
        SecureRandom random = new SecureRandom();
        int otp = 100_000 + random.nextInt(900_000);
        return String.valueOf(otp);
    }

    private boolean isValidPassword(String password){
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[A-Z].*");
    }

    
}

