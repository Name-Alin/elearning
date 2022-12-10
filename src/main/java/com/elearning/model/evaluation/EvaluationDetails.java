package com.elearning.model.evaluation;

import com.elearning.model.authentication.User;
import com.elearning.model.training.Training;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "evaluation_details")
public class EvaluationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @Column(name = "quiz_id")
    private Long quizId;
    @ManyToOne(targetEntity = Training.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "training_id", insertable = false, updatable = false)
    private Training training;
    @Column(name = "training_id")
    private Long trainingId;

//    private Timestamp startTime;
//    private Timestamp endTime;
    private int totalQuizDuration;
//  private int durationPerQuestion;
    private float quizPercentCorrect;
    private boolean graduated;
//  private int timePerformance;
    private int numberOfAttempts;
//  private String wrongAnswers;
//  private String goodAnswers;


}