//package com.elearning.model.evaluation;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "evaluation_details")
//public class EvaluationDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    private Long userId;
//    private Long quizId;
//    private Long trainingId;
//
//    private String trainingQuizUser;
//    private int totalQuizDuration;
//    private int durationPerQuestion;
//    private int quizPercentCorrect;
//    private boolean graduated;
//    private int timePerformance;
//    private int numberOfAttempts;
//    private String wrongAnswers;
//    private String goodAnswers;
//}