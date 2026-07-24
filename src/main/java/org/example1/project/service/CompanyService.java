package org.example1.project.service;

import org.example1.project.entity.Company;
import org.example1.project.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

//    public Company save(Company company) {
//        return repository.save(company);
//    }

    public Company save(Company company) {

        if(repository.existsByCompanyName(company.getCompanyName())) {
            throw new RuntimeException("Company already exists");
        }

        return repository.save(company);
    }
    public List<Company> getAll() {
        return repository.findAll();
    }

    public Company getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String delete(Long id) {
        repository.deleteById(id);
        return "Company Deleted Successfully";
    }
}