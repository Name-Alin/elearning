package com.elearning.repositories;

import com.elearning.model.evaluation.Quiz;
import com.elearning.model.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q WHERE q.training = :training")
    List<Quiz> getQuizzesForTraining(@Param("training") Training training);

}