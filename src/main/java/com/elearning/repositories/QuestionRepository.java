package com.elearning.repositories;

import com.elearning.model.evaluation.Question;
import com.elearning.model.evaluation.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.quiz = :quiz")
    public List<Question> findQuestionsByQuizId(@Param("quiz") Quiz quiz);
}