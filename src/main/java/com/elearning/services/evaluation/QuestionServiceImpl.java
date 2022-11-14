package com.elearning.services.evaluation;

import com.elearning.dto.QuestionDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.exceptions.ErrorType;
import com.elearning.exceptions.ResourceNotFoundException;
import com.elearning.model.evaluation.Question;
import com.elearning.model.evaluation.Quiz;
import com.elearning.repositories.QuestionRepository;
import com.elearning.repositories.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionServiceImpl {

    QuestionRepository questionRepository;
    MapperDto mapper;
    QuizRepository quizRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, MapperDto mapper, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.mapper = mapper;
        this.quizRepository = quizRepository;
    }

    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream().map(mapper::convertToQuestionDto).collect(Collectors.toList());
    }

    public List<QuestionDto> getAllQuizQuestions(Long quizId) {

        Optional<Quiz> quiz = quizRepository.findById(quizId);
        return quiz.map(value -> questionRepository.findQuestionsByQuizId(value)
                        .stream().map(mapper::convertToQuestionDto).collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz was not found", ErrorType.QUIZ_DOES_NOT_EXIST));
    }

    public void saveOrUpdateQuestion(QuestionDto questionDto, Long quizId) {
        Question question = mapper.convertToQuestionEntity(questionDto);
        question.getAnswers().forEach(question::addAnswer);
        question.setQuiz(quizRepository.getReferenceById(quizId));
        questionRepository.save(question);
    }

    public void deleteQuestion(Long questionId) {

        questionRepository.delete(questionRepository.getReferenceById(questionId));

    }

    public QuestionDto getQuestionById(Long questionId) {
        return mapper.convertToQuestionDto(questionRepository.getReferenceById(questionId));

    }

    public void updateQuestion(QuestionDto questionDto) {
        Question question = mapper.convertToQuestionEntity(questionDto);
        question.getAnswers().forEach(question::addAnswer);
        log.info(question.getQuiz().toString());
    }
}
