package com.elearning.dto;

import com.elearning.model.evaluation.Question;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class QuizDto implements Serializable {
    private Long id;
    private List<Question> questions;
    private String name;
    private String description;
    private Timestamp createdDate;
}
