package com.elearning.services.evaluation;

import com.elearning.dto.QuestionDto;
import com.elearning.dto.QuizDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.exceptions.ErrorType;
import com.elearning.exceptions.ResourceNotFoundException;
import com.elearning.model.evaluation.Quiz;
import com.elearning.repositories.QuizRepository;
import com.elearning.repositories.QuizRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuizServiceImpl {

    QuizRepository quizRepository;
    QuizRepositoryImpl quizRepositoryImpl;
    MapperDto mapper;

    public QuizServiceImpl(QuizRepository quizRepository, QuizRepositoryImpl quizRepositoryImpl, MapperDto mapper) {
        this.quizRepository = quizRepository;
        this.quizRepositoryImpl = quizRepositoryImpl;
        this.mapper = mapper;
    }

    @Transactional
    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream().map(mapper::convertToQuizDto).collect(Collectors.toList());
    }

    @Transactional
    public void saveQuiz(QuizDto quizDto) {
        Quiz quiz = mapper.convertToQuizEntity(quizDto);
        quizDto.getQuestions().forEach(quiz::addQuestion);
        quiz.getQuestions().forEach(q -> q.getAnswers().forEach(q::addAnswer));

        log.info(quizDto.getQuestions().toString());
        quiz.getQuestions().forEach(l -> l.getAnswers().forEach(g -> log.info(String.valueOf(g))));

        quizRepository.save(quiz);

    }

    public Long getLastQuizId() {
        return quizRepositoryImpl.getLastQuizId();

    }

    @Transactional
    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz was not found", ErrorType.QUIZ_DOES_NOT_EXIST));
//        quiz.getQuestions().forEach(quiz::removeQuestion);
        quizRepository.delete(quiz);
    }
}
