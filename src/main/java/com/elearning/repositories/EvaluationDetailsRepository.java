package com.elearning.repositories;

import com.elearning.model.authentication.User;
import com.elearning.model.evaluation.EvaluationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluationDetailsRepository extends JpaRepository<EvaluationDetails, Long> {

    @Query("SELECT e FROM EvaluationDetails e WHERE e.user = :user")
    List<EvaluationDetails> userEvaluations(@Param("user") User user);

}
