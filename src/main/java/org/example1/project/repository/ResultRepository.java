package org.example1.project.repository;

import org.example1.project.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudentId(Long studentId);

    List<Result> findByCompanyId(Long companyId);

    List<Result> findByTechnologyId(Long technologyId);

}