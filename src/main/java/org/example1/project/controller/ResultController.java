package org.example1.project.controller;

import org.example1.project.entity.Result;
import org.example1.project.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultService service;

    @PostMapping("/save")
    public Result save(@RequestBody Result result) {
        return service.save(result);
    }

    @GetMapping("/all")
    public List<Result> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Result> getByStudent(@PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
}