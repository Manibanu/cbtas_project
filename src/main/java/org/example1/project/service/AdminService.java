package org.example1.project.service;

import org.example1.project.entity.Admin;
import org.example1.project.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Same PasswordEncoder bean used by StudentService - must exist in SecurityConfig.
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registers a new Admin. Rejects duplicate emails, and hashes the password
    // before storing it - never save plain text passwords.
    public Admin saveAdmin(Admin admin) {

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists. Please log in instead.");
        }

        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);

        return adminRepository.save(admin);
    }

    // Real login: checks the submitted password against the stored HASH.
    public Admin login(String email, String rawPassword) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean matches = passwordEncoder.matches(rawPassword, admin.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid email or password");
        }

        return admin;
    }

    // Get All Admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get Admin By Id
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    // Delete Admin
    public String deleteAdmin(Long id) {
        adminRepository.deleteById(id);
        return "Admin Deleted Successfully";
    }
}