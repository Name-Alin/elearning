package com.elearning.services.evaluation;

import com.elearning.dto.QuizDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.exceptions.ErrorType;
import com.elearning.exceptions.ResourceNotFoundException;
import com.elearning.model.evaluation.Answer;
import com.elearning.model.evaluation.Question;
import com.elearning.model.evaluation.Quiz;
import com.elearning.repositories.QuestionRepository;
import com.elearning.repositories.QuizRepository;
import com.elearning.repositories.QuizRepositoryImpl;
import com.elearning.repositories.TrainingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuizServiceImpl {

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuizRepositoryImpl quizRepositoryImpl;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    MapperDto mapper;
    @Autowired
    TrainingRepository trainingRepository;

//    public QuizServiceImpl(QuizRepository quizRepository, QuizRepositoryImpl quizRepositoryImpl, MapperDto mapper) {
//        this.quizRepository = quizRepository;
//        this.quizRepositoryImpl = quizRepositoryImpl;
//        this.mapper = mapper;
//    }

    @Transactional
    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream().map(mapper::convertToQuizDto).collect(Collectors.toList());
    }

    @Transactional
    public void saveQuiz(QuizDto quizDto) {
        Quiz quiz = mapper.convertToQuizEntity(quizDto);
        quizDto.getQuestions().forEach(quiz::addQuestion);
        quiz.getQuestions().forEach(q -> q.getAnswers().forEach(q::addAnswer));

        if (quizDto.getTrainingId() != null) {
            quiz.setTraining(trainingRepository.getReferenceById(quizDto.getTrainingId()));
        }
        quiz.setCreatedDate(new Timestamp(System.currentTimeMillis()));

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

    public float getQuizResult(QuizDto quizResult) {
        List<Question> questionsFromDb = new ArrayList<>();
        AtomicInteger correct = new AtomicInteger();
        AtomicInteger correctAnswersFromDb = new AtomicInteger();
        quizResult.getQuestions().forEach(q -> questionsFromDb.add(questionRepository.getReferenceById(q.getId())));

        quizResult.getQuestions().forEach(qFromUI ->
                questionsFromDb.forEach(qFromDB -> {
                    if (Objects.equals(qFromDB.getId(), qFromUI.getId())) {

                        List<Answer> collectTrueAttempt = qFromUI.getAnswers().stream().filter(Answer::isCorrect).collect(Collectors.toList());
                        List<Answer> collectTrueDb = qFromDB.getAnswers().stream().filter(Answer::isCorrect).collect(Collectors.toList());
                        correctAnswersFromDb.getAndAdd(collectTrueDb.size());

                        //check only if size is the same, else skip because answer is not correct
                        if (collectTrueAttempt.size() > 1 && collectTrueAttempt.size() == collectTrueDb.size()) {
                            log.info("Size higher than 1: \n Size of collectTrueAttempt is: {} \n and Size of collectTrueDb is: {}",
                                    collectTrueAttempt.size(), collectTrueDb.size());

                            //if ids match, answer is correct
                            collectTrueDb.forEach(answerDb ->
                                    collectTrueAttempt.forEach(answerAt -> {
                                        if (Objects.equals(answerDb.getId(), answerAt.getId())) {
                                            correct.getAndIncrement();
                                        }
                                    })
                            );

                        } else if (collectTrueAttempt.size() == 1 && collectTrueDb.size() == 1
                                && Objects.equals(collectTrueAttempt.get(0).getId(), collectTrueDb.get(0).getId())) {
                            log.info("Size == 1: \n Size of collectTrueAttempt is: {} \n and Size of collectTrueDb is: {}",
                                    collectTrueAttempt.size(), collectTrueDb.size());
                            correct.getAndIncrement();
                        }
                    }
                })
        );


        float percentCorrect = Float.parseFloat(String.valueOf(correct)) / Float.parseFloat(String.valueOf(correctAnswersFromDb));
        log.info("Correct answered: {}% ", percentCorrect * 100);
        log.info("result is: " + correct);
        return percentCorrect * 100;
    }

    public QuizDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz was not found", ErrorType.QUIZ_DOES_NOT_EXIST));
        QuizDto quizDto = mapper.convertToQuizDto(quiz);

        try {
            if (quiz.getTraining().getId() != null) {
                quizDto.setTrainingId(quiz.getTraining().getId());
            }
        } catch (Exception ex) {
            log.info(ex.toString());
        }

        return quizDto;
    }

    public void updateTitleOrDesc(QuizDto quizDto) {

        Quiz quiz = quizRepository.getReferenceById(quizDto.getId());
        quiz.setName(quizDto.getName());
        quiz.setDescription(quizDto.getDescription());
        quiz.setExpectedPercent(quizDto.getExpectedPercent());

        if (quizDto.getTrainingId() != null) {
            quiz.setTraining(trainingRepository.getReferenceById(quizDto.getTrainingId()));
        } else {
            quiz.setTraining(null);
        }

        quizRepository.save(quiz);
    }

    public List<QuizDto> getAllQuizzesForTraining(Long trainingId) {
        List<QuizDto> quizDtos = new ArrayList<>();

        try {

            quizDtos.addAll(quizRepository.getQuizzesForTraining(trainingRepository.getReferenceById(trainingId))
                    .stream().map(mapper::convertToQuizDto).collect(Collectors.toList()));

        } catch (Exception ex) {
            log.info(ex.toString());
        }
        return quizDtos;
    }
}
