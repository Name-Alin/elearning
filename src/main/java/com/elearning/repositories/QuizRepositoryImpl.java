package com.elearning.repositories;

import com.elearning.model.evaluation.EvaluationDetails;
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

//    @Query("SELECT e FROM EvaluationDetails e WHERE e.user = :user and e.trainingId = trainingId")
    public EvaluationDetails getTrainingEvaluationForUser(Long userId, Long trainingId) {
        String query = String.format("SELECT e FROM EvaluationDetails e WHERE e.trainingId = %s ORDER BY id DESC", trainingId);
        return entityManager.createQuery(query, EvaluationDetails.class).setMaxResults(1).getSingleResult();
    }

}
