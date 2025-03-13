package com.megacity.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megacity.server.Service.PasswordService;

@RestController
public class PasswordResetController {
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String customerEmail){
        return passwordService.sendPasswordResetOtp(customerEmail);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword){
        return passwordService.resetPassword(email, otp, newPassword);
    }
}
