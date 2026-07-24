package org.example1.project.controller;

import org.example1.project.entity.Technology;
import org.example1.project.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technology")
public class TechnologyController {

    @Autowired
    private TechnologyService service;

    @PostMapping("/save")
    public Technology save(@RequestBody Technology technology) {
        return service.save(technology);
    }

    @GetMapping("/all")
    public List<Technology> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Technology getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @PostMapping("/saveAll")
    public List<Technology> saveAll(@RequestBody List<Technology> technologies) {
        return service.saveAll(technologies);
    }
}