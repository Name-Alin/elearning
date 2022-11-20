package com.elearning.model.training;

import com.elearning.model.evaluation.Quiz;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String trainingTitle;
    private String description;
    private String pathToTraining;

    @OneToMany(mappedBy = "training")
    @ToString.Exclude
    @Column(name = "quiz_id")
    private List<Quiz> quiz;

    public void addQuiz(Quiz quiz) {
        this.quiz.add(quiz);
        quiz.setTraining(this);
    }

    public void removeQuiz(Quiz quiz) {
        this.quiz.remove(quiz);
        quiz.setTraining(null);
    }

//    @Column(name = "quiz_id")
//    private Long quizId;

}