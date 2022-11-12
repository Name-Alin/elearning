package com.elearning.services.evaluation;

import com.elearning.dto.QuestionDto;
import com.elearning.dto.QuizDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.model.evaluation.Quiz;
import com.elearning.repositories.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuizServiceImpl {

    QuizRepository quizRepository;
    MapperDto mapper;

    public QuizServiceImpl(QuizRepository quizRepository, MapperDto mapper) {
        this.quizRepository = quizRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<QuizDto> getAllQuestions() {
        return quizRepository.findAll().stream().map(mapper::convertToQuizDto).collect(Collectors.toList());
    }

    @Transactional
    public void saveOrUpdateQuiz(QuizDto quizDto) {
        Quiz quiz = mapper.convertToQuizEntity(quizDto);
        quizDto.getQuestions().forEach(quiz::addQuestion);
        quiz.getQuestions().forEach(q -> q.getAnswers().forEach(q::addAnswer));

        log.info(quizDto.getQuestions().toString());
        quiz.getQuestions().forEach(l -> l.getAnswers().forEach(g -> log.info(String.valueOf(g))));

        quizRepository.save(quiz);

    }

    public Long getLastQuizId() {
        return quizRepository.getLastQuizId();

    }


}
