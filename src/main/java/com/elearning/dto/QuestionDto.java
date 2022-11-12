package com.elearning.dto;

import com.elearning.model.evaluation.Answer;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class QuestionDto {
    private Long id;
    private String content;
    private List<Answer> answers;
}
