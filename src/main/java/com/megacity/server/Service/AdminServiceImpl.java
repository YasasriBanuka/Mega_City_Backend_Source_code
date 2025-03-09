package com.megacity.server.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacity.server.Model.Admin;
import com.megacity.server.Repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminById(String adminId) {
        return adminRepository.findById(adminId);
    }


    public boolean existsByUsername(String userName) {
        return adminRepository.existsByUserName(userName);
    }

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(String adminId, Admin updatedAdmin) {
        if (adminRepository.existsById(adminId)) {
            updatedAdmin.setAdminId(adminId); // Ensure ID remains the same
            return adminRepository.save(updatedAdmin);
        }
        return null; // Handle admin not found scenario
    }

    @Override
    public void deleteAdmin(String adminId) {
        adminRepository.deleteById(adminId);
    }
}