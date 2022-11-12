package com.elearning.repositories;

import com.elearning.model.evaluation.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}