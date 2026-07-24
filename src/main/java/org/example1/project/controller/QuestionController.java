package org.example1.project.controller;

import org.example1.project.entity.Question;
import org.example1.project.enums.Difficulty;
import org.example1.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @PostMapping("/save")
    public Question saveQuestion(@RequestBody Question question) {
        return service.saveQuestion(question);
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return service.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return service.getQuestionById(id);
    }

    @GetMapping("/company/{companyId}")
    public List<Question> getByCompany(@PathVariable Long companyId) {
        return service.getByCompany(companyId);
    }

    @GetMapping("/technology/{technologyId}")
    public List<Question> getByTechnology(@PathVariable Long technologyId) {
        return service.getByTechnology(technologyId);
    }

    @GetMapping("/difficulty/{difficulty}")
    public List<Question> getByDifficulty(@PathVariable Difficulty difficulty) {
        return service.getByDifficulty(difficulty);
    }

    @GetMapping("/filter")
    public List<Question> filterQuestions(
            @RequestParam Long companyId,
            @RequestParam Long technologyId,
            @RequestParam Difficulty difficulty) {

        return service.getByCompanyTechnologyDifficulty(
                companyId,
                technologyId,
                difficulty
        );
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        return service.deleteQuestion(id);
    }
}