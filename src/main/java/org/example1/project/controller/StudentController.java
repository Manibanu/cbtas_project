package org.example1.project.controller;

import org.example1.project.entity.Student;
import org.example1.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service;

    // Registration
    @PostMapping("/save")
    public Student save(@RequestBody Student student) {
        return service.save(student);
    }

    // Login - accepts { "email": "...", "password": "..." }
    @PostMapping("/login")
    public Student login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        return service.login(email, password);
    }

    @GetMapping("/all")
    public List<Student> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
}