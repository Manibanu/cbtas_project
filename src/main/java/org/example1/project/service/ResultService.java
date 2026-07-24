package org.example1.project.service;

import org.example1.project.entity.Company;
import org.example1.project.entity.Result;
import org.example1.project.entity.Student;
import org.example1.project.entity.Technology;
import org.example1.project.repository.CompanyRepository;
import org.example1.project.repository.ResultRepository;
import org.example1.project.repository.StudentRepository;
import org.example1.project.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    public Result save(Result result) {

        // Fetch Student from Database
        Student student = studentRepository.findById(
                result.getStudent().getId()
        ).orElse(null);

        // Fetch Company from Database
        Company company = companyRepository.findById(
                result.getCompany().getId()
        ).orElse(null);

        // Fetch Technology from Database
        Technology technology = technologyRepository.findById(
                result.getTechnology().getId()
        ).orElse(null);

        // Set fetched objects
        result.setStudent(student);
        result.setCompany(company);
        result.setTechnology(technology);

        // Calculate Result
        int total = result.getTotalQuestions();
        int correct = result.getCorrectAnswers();
        int wrong = total - correct;

        double percentage = ((double) correct / total) * 100;

        result.setWrongAnswers(wrong);
        result.setPercentage(percentage);

        if (percentage >= 50) {
            result.setResultStatus("PASS");
        } else {
            result.setResultStatus("FAIL");
        }

        return repository.save(result);
    }

    public List<Result> getAll() {
        return repository.findAll();
    }

    public Result getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Result> getByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public String delete(Long id) {
        repository.deleteById(id);
        return "Result Deleted Successfully";
    }
}