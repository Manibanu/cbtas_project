package org.example1.project.service;

import org.example1.project.entity.Technology;
import org.example1.project.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    @Autowired
    private TechnologyRepository repository;

    public Technology save(Technology technology) {
        return repository.save(technology);
    }

    public List<Technology> getAll() {
        return repository.findAll();
    }

    public Technology getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String delete(Long id) {
        repository.deleteById(id);
        return "Technology Deleted Successfully";
    }
    public List<Technology> saveAll(List<Technology> technologies) {
        return repository.saveAll(technologies);
    }
}