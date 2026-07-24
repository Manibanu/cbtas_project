package org.example1.project.service;

import org.example1.project.entity.Student;
import org.example1.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    // This bean must exist in your SecurityConfig.java:
    // @Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registers a NEW student. Rejects the request if the email is already taken,
    // instead of silently returning the existing account (that was a security hole -
    // it let anyone "log in" as an existing student just by knowing their email).
    public Student save(Student student) {

        if (repository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists. Please log in instead.");
        }

        // Hash the password before saving - never store plain text passwords.
        String hashedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(hashedPassword);

        return repository.save(student);
    }

    // Real login: checks the submitted password against the stored HASH.
    // Returns the student if it matches, throws an error if it doesn't.
    public Student login(String email, String rawPassword) {

        Student student = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean matches = passwordEncoder.matches(rawPassword, student.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid email or password");
        }

        return student;
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String delete(Long id) {
        repository.deleteById(id);
        return "Student Deleted Successfully";
    }
}