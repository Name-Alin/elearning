//package com.elearning.model.training;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import javax.persistence.*;
//
//@ToString
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//
//@Entity
//@Table(name = "training")
//public class Training {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//    private String trainingTitle;
//    private String description;
//    private String pathToTraining;
//
//    private Long quizId;
//
//}