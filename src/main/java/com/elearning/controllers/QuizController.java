package com.elearning.controllers;

import com.elearning.dto.AnswerDto;
import com.elearning.dto.EvaluationDetailsDto;
import com.elearning.dto.QuizDto;
import com.elearning.model.evaluation.Answer;
import com.elearning.model.evaluation.Question;
import com.elearning.services.evaluation.QuizServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.cert.CertPath;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Controller
public class QuizController {

    @Autowired
    EvaluationDetailsDto evaluationDetails;

    QuizServiceImpl quizService;

    public QuizController(QuizServiceImpl quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/showQuizzes")
    public String getQuizzes(Model model) {

        model.addAttribute("quizzes", quizService.getAllQuizzes());

        return "evaluation/showQuizzes";
    }

    @GetMapping("/quizForm")
    public String getQuizForm(Model model, Question questionDto, QuizDto quizDto) {
        List<Question> questions = new ArrayList<>();
        List<Answer> answerDtos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answerDtos.add(new Answer());
        }
        questionDto.setAnswers(answerDtos);
        questions.add(questionDto);
        quizDto.setQuestions(questions);
//        model.addAttribute("quizDto", quizDto);

        return "evaluation/quizForm";
    }

    @PostMapping("/createNewQuiz")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewQuiz(@Valid QuizDto quizDto, BindingResult result, HttpServletResponse httpServletResponse) throws IOException {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
        }

        quizService.saveQuiz(quizDto);
        Long id = quizService.getLastQuizId();

        httpServletResponse.sendRedirect("/quiz/" + id + "/questions");
        return "redirect:/quiz/" + id + "/questions";
    }

    @GetMapping("/deleteQuiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteQuiz(@PathVariable("id") Long id) {

        quizService.deleteQuiz(id);

        return "redirect:/showQuizzes";
    }

    @GetMapping("/attemptQuiz")
    public String attemptQuiz(Model model, HttpServletRequest httpServletRequest) {

        List<QuizDto> quiz = quizService.getAllQuizzes();
        Random random = new Random();
        QuizDto quizDto = quiz.get(random.nextInt(quiz.size()));
        List<AnswerDto> quizDto1 = new ArrayList<>();
        log.info("Attempted quiz with id: {}", quizDto.getId());
        model.addAttribute("attemptedQuiz", quizDto);
        model.addAttribute("quizResult", quizDto1);

        log.info("User is: {}", httpServletRequest.getUserPrincipal().getName());
        Date date = new Date();
        log.info("The time is: " + new Timestamp(System.currentTimeMillis()));

        evaluationDetails.setStartTime(new Timestamp(System.currentTimeMillis()));

        return "takingTraining/attemptQuiz";
    }

    @PostMapping("/submit")
    public String submitResults(@ModelAttribute QuizDto quizResult) {

        evaluationDetails.setEndTime(new Timestamp(System.currentTimeMillis()));
        log.info(String.valueOf(evaluationDetails.getEndTime()));
//        long l1 = Long.parseLong(String.valueOf(evaluationDetails.getEndTime()));
//        long l2 = Long.parseLong(String.valueOf(evaluationDetails.getStartTime()));
//        long totalDuration = l1 - l2;
//        log.info(String.valueOf(totalDuration));
//        evaluationDetails.setTotalQuizDuration();
        log.info(quizResult.toString());
        quizResult.getQuestions().forEach(l -> log.info(l.getAnswers().toString()));
//        answerDto.forEach(l->log.info("is correct?"+l.isCorrect()));
        log.info("is correct?");
        quizService.getQuizResult(quizResult);

        return "redirect:/";
    }

}
