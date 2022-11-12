package com.elearning.dto;

import com.elearning.model.evaluation.Question;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class AnswerDto {
    private Long id;
    private String content;
    private Question question;
    private boolean correct;
}
