package com.megacity.server.Controller;
import com.megacity.server.Model.Admin;
import com.megacity.server.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.megacity.server.Service.CloudinaryService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins ="*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/getAllAdmins")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/getAdminbyid/{id}")
    public Optional<Admin> getAdminById(@PathVariable String id) {
        return adminService.getAdminById(id);
    }

    @PostMapping("/createAdmin")
public Admin createAdmin(
    @RequestParam("userName") String userName,
    @RequestParam("password") String password,
    @RequestParam("adminEmail") String adminEmail,
    @RequestParam("adminImage") MultipartFile adminImage
) throws IOException {

    // Upload admin image to Cloudinary
    String adminImageUrl = cloudinaryService.uploadImage(adminImage);

    // Create and save admin details
    Admin admin = new Admin();
    admin.setUserName(userName);
    admin.setPassword(passwordEncoder.encode(password)); // Encrypt password
    admin.setAdminEmail(adminEmail);
    admin.setAdminImage(adminImageUrl); // Save Cloudinary image URL

    return adminService.createAdmin(admin);
}


    @PutMapping("updateAdmin/{id}")
    public Admin updateAdmin(@PathVariable String id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("deleteAdmin/{id}")
    public void deleteAdmin(@PathVariable String id) {
        adminService.deleteAdmin(id);
    }


    @GetMapping("/auth/checkUserName")
    public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(@RequestParam String userName) {
    boolean exists = adminService.existsByUsername(userName);
    return ResponseEntity.ok().body(Map.of("exists", exists));
}

}
