package org.example1.project.service;

import org.example1.project.entity.Company;
import org.example1.project.entity.Question;
import org.example1.project.entity.Technology;
import org.example1.project.enums.Difficulty;
import org.example1.project.repository.CompanyRepository;
import org.example1.project.repository.QuestionRepo;
import org.example1.project.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {


    @Autowired
    private QuestionRepo repository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TechnologyRepository technologyRepository;


    public Question saveQuestion(Question question) {

        Company company = companyRepository.findById(
                question.getCompany().getId()
        ).orElse(null);

        Technology technology = technologyRepository.findById(
                question.getTechnology().getId()
        ).orElse(null);


        question.setCompany(company);
        question.setTechnology(technology);

        return repository.save(question);
    }


    public List<Question> getAllQuestions() {
        return repository.findAll();
    }


    public Question getQuestionById(Long id) {
        return repository.findById(id).orElse(null);
    }


    public List<Question> getByCompany(Long companyId) {
        return repository.findByCompanyId(companyId);
    }


    public List<Question> getByTechnology(Long technologyId) {
        return repository.findByTechnologyId(technologyId);
    }


    public List<Question> getByDifficulty(Difficulty difficulty) {
        return repository.findByDifficulty(difficulty);
    }


    public List<Question> getByCompanyTechnology(Long companyId, Long technologyId) {
        return repository.findByCompanyIdAndTechnologyId(companyId, technologyId);
    }


    public List<Question> getByCompanyTechnologyDifficulty(
            Long companyId,
            Long technologyId,
            Difficulty difficulty) {

        return repository.findByCompanyIdAndTechnologyIdAndDifficulty(
                companyId,
                technologyId,
                difficulty
        );
    }


    public String deleteQuestion(Long id) {
        repository.deleteById(id);
        return "Question Deleted Successfully";
    }
}