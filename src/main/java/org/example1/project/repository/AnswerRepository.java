package org.example1.project.repository;


import org.example1.project.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnswerRepository
        extends JpaRepository<Answer,Long> {


}