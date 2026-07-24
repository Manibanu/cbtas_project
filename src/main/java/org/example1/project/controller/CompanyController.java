package org.example1.project.controller;

import org.example1.project.entity.Company;
import org.example1.project.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @PostMapping("/save")
    public Company save(@RequestBody Company company) {
        return service.save(company);
    }

    @GetMapping("/all")
    public List<Company> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Company getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
}