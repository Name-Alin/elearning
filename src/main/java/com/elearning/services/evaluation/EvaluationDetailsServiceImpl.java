package com.elearning.services.evaluation;


import com.elearning.dto.EvaluationDetailsDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.model.authentication.User;
import com.elearning.model.evaluation.EvaluationDetails;
import com.elearning.model.evaluation.Quiz;
import com.elearning.repositories.EvaluationDetailsRepository;
import com.elearning.repositories.QuizRepository;
import com.elearning.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EvaluationDetailsServiceImpl {

    @Autowired
    EvaluationDetailsRepository evalDetailsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    MapperDto mapper;

    public void saveEvaluation(EvaluationDetailsDto evaluationDetailsDto) {

        EvaluationDetails evaluationDetails = mapper.convertToEvaluationDetailsEntity(evaluationDetailsDto);
        User user = userRepository.findByUsername(evaluationDetailsDto.getUsername()).get();
        Quiz quiz = quizRepository.getReferenceById(evaluationDetailsDto.getQuizId());

        evaluationDetailsDto.setGraduated(evaluationDetailsDto.getQuizPercentCorrect() >= quiz.getExpectedPercent());
        evaluationDetails.setUser(user);
        long quizDuration = (evaluationDetailsDto.getEndTime().getTime() - evaluationDetailsDto.getStartTime().getTime());
        log.info("Quiz duration is: {} minutes ", TimeUnit.MILLISECONDS.toMinutes(quizDuration));

        String[] time = new Timestamp(quizDuration).toString().split(" ");
        log.info(time[1]);
        Date date = new Date(quizDuration);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        log.info(format.format(date));

//        evaluationDetails.setQuizDuration((long) (quizDuration * 0.001));
        evalDetailsRepository.save(evaluationDetails);


    }

}
