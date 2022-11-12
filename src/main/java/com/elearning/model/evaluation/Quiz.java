package com.elearning.model.evaluation;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "quiz")
    @ToString.Exclude
    private List<Question> questions = new ArrayList<>();

    private Timestamp startTime;
    private Timestamp endTime;
    private boolean isOpen;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quiz quiz = (Quiz) o;
        return id != null && Objects.equals(id, quiz.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addQuestion(Question question) {
//        questions.add(question);
        question.setQuiz(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setQuiz(null);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        questions.forEach(question -> question.setQuiz(this));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}