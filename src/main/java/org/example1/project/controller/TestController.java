package org.example1.project.controller;

import org.example1.project.dto.QuestionResponseDTO;
import org.example1.project.entity.Test;
import org.example1.project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService service;

    // Starts a test: creates the Test record AND randomly assigns
    // matching questions to it behind the scenes.
    @PostMapping("/start")
    public Test startTest(@RequestBody Test test) {
        return service.startTest(test);
    }

    // Returns the questions assigned to this test, WITHOUT the correct answers.
    // The frontend should call this once per test and reuse the list -
    // calling it again returns the same fixed set (not a new random one).
    @GetMapping("/{id}/questions")
    public List<QuestionResponseDTO> getQuestionsForTest(@PathVariable Long id) {
        return service.getQuestionsForTest(id);
    }

    @GetMapping("/all")
    public List<Test> getAllTests() {
        return service.getAllTests();
    }

    @GetMapping("/{id}")
    public Test getTest(@PathVariable Long id) {
        return service.getTest(id);
    }
}