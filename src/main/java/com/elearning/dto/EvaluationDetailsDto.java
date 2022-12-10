package com.elearning.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Component
public class EvaluationDetailsDto {

    private Long id;


    private Long trainingId;

    private Timestamp startTime;
    private Timestamp endTime;
    private int totalQuizDuration;
    private float quizPercentCorrect;
    private boolean graduated;
    private int numberOfAttempts;

}
