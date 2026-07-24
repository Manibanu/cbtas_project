package org.example1.project.repository;

import org.example1.project.entity.Question;
import org.example1.project.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {

    List<Question> findByCompany_IdAndTechnology_IdAndDifficulty(
            Long companyId,
            Long technologyId,
            Difficulty difficulty
    );

    List<Question> findByCompanyIdAndTechnologyIdAndDifficulty(Long companyId, Long technologyId, Difficulty difficulty);

    List<Question> findByCompanyIdAndTechnologyId(Long companyId, Long technologyId);

    List<Question> findByDifficulty(Difficulty difficulty);

    List<Question> findByCompanyId(Long companyId);

    List<Question> findByTechnologyId(Long technologyId);
}