package com.elearning.repositories;

import com.elearning.model.evaluation.Quiz;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuizRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public Long getLastQuizId() {
        Quiz quiz = entityManager.createQuery("SELECT q FROM Quiz q ORDER BY id DESC", Quiz.class).setMaxResults(1).getSingleResult();

        return quiz.getId();
    }
}
