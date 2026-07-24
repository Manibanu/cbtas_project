package org.example1.project.repository;

import org.example1.project.entity.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionRepo extends JpaRepository<TestQuestion, Long> {

    // Fetches all the questions that were randomly assigned to a specific test,
    // so we can show them to the student and later grade against them.
    List<TestQuestion> findByTestId(Long testId);

    void deleteByTestId(Long testId);
}