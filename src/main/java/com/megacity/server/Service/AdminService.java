package com.megacity.server.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.megacity.server.Model.Admin;

@Service
public interface AdminService {

    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(String adminId);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(String adminId, Admin admin);
    void deleteAdmin(String adminId);
    public boolean existsByUsername(String userName);
    
}
