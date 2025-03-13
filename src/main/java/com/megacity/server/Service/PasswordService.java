package com.megacity.server.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    ResponseEntity<?> sendPasswordResetOtp(String customerEmail);
    ResponseEntity<?> resetPassword(String customerEmail, String otp, String newPassword);
}
