package org.example1.project.service;

import org.example1.project.dto.QuestionResponseDTO;
import org.example1.project.entity.*;
import org.example1.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private TestQuestionRepo testQuestionRepo;

    // Starts a new test: links the real Student/Company/Technology records,
    // then randomly picks N questions matching company+technology+difficulty
    // and saves them into the test_questions join table, so this exact set
    // of questions stays fixed for this test (won't change on refetch).
    public Test startTest(Test test) {

        Student student = studentRepository.findById(
                test.getStudent().getId()
        ).orElseThrow(() -> new RuntimeException("Student Not Found"));

        Company company = companyRepository.findById(
                test.getCompany().getId()
        ).orElseThrow(() -> new RuntimeException("Company Not Found"));

        Technology technology = technologyRepository.findById(
                test.getTechnology().getId()
        ).orElseThrow(() -> new RuntimeException("Technology Not Found"));

        test.setStudent(student);
        test.setCompany(company);
        test.setTechnology(technology);
        test.setStartTime(LocalDateTime.now());

        // Save the test first so it has an ID we can link questions to
        Test savedTest = repository.save(test);

        // Find every question matching this company + technology + difficulty
        List<Question> matchingQuestions = questionRepo
                .findByCompanyIdAndTechnologyIdAndDifficulty(
                        company.getId(),
                        technology.getId(),
                        test.getDifficulty()
                );

        if (matchingQuestions.isEmpty()) {
            throw new RuntimeException(
                    "No questions found for this company/technology/difficulty combination"
            );
        }

        // Shuffle so each test attempt gets a different random order/subset
        Collections.shuffle(matchingQuestions);

        int howMany = (savedTest.getTotalQuestions() != null
                && savedTest.getTotalQuestions() > 0
                && savedTest.getTotalQuestions() <= matchingQuestions.size())
                ? savedTest.getTotalQuestions()
                : matchingQuestions.size();

        List<TestQuestion> testQuestions = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            TestQuestion tq = new TestQuestion();
            tq.setTest(savedTest);
            tq.setQuestion(matchingQuestions.get(i));
            testQuestions.add(tq);
        }

        testQuestionRepo.saveAll(testQuestions);

        return savedTest;
    }

    // Returns the exact questions assigned to this test (fixed set, from
    // test_questions), with correctAnswer stripped out via the DTO.
    public List<QuestionResponseDTO> getQuestionsForTest(Long testId) {

        List<TestQuestion> testQuestions = testQuestionRepo.findByTestId(testId);

        if (testQuestions.isEmpty()) {
            throw new RuntimeException("No questions found for this test. Did the test start correctly?");
        }

        List<QuestionResponseDTO> result = new ArrayList<>();

        for (TestQuestion tq : testQuestions) {
            Question q = tq.getQuestion();
            result.add(new QuestionResponseDTO(
                    q.getId(),
                    q.getQuestion(),
                    q.getOptionA(),
                    q.getOptionB(),
                    q.getOptionC(),
                    q.getOptionD()
            ));
        }

        return result;
    }

    public List<Test> getAllTests() {
        return repository.findAll();
    }

    public Test getTest(Long id) {
        return repository.findById(id).orElse(null);
    }
}