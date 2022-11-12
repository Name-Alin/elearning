package com.elearning.repositories;

import com.elearning.model.authentication.User;
import com.elearning.model.evaluation.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT id FROM Quiz ORDER BY id DESC limit 1")
    Long getLastQuizId();
}