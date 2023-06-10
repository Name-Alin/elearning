package com.elearning.controllers;

import com.elearning.dto.AnswerDto;
import com.elearning.dto.EvaluationDetailsDto;
import com.elearning.dto.QuizDto;
import com.elearning.model.evaluation.Answer;
import com.elearning.model.evaluation.Question;
import com.elearning.services.evaluation.EvaluationDetailsServiceImpl;
import com.elearning.services.evaluation.QuizServiceImpl;
import com.elearning.services.training.TrainingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Controller
public class QuizController {

    @Autowired
    EvaluationDetailsDto evaluationDetails;
    @Autowired
    TrainingServiceImpl trainingService;
    @Autowired
    EvaluationDetailsServiceImpl evaluationDetailsService;

    QuizServiceImpl quizService;

    public QuizController(QuizServiceImpl quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz/show")
    public String getQuizzes(Model model) {

        model.addAttribute("quizzes", quizService.getAllQuizzes());

        return "evaluation/showQuizzes";
    }

    @GetMapping("/quiz/form")
    public String getQuizForm(Model model, Question questionDto, QuizDto quizDto) {
        List<Question> questions = new ArrayList<>();
        List<Answer> answerDtos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answerDtos.add(new Answer());
        }
        questionDto.setAnswers(answerDtos);
        questions.add(questionDto);
        quizDto.setQuestions(questions);
        model.addAttribute("trainings", trainingService.getAllTrainings());

        return "evaluation/quizForm";
    }

    @PostMapping("/quiz/create")
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

    @GetMapping("/quiz/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteQuiz(@PathVariable("id") Long id, HttpServletResponse httpServletResponse) throws IOException {

        quizService.deleteQuiz(id);

        httpServletResponse.sendRedirect("/quiz/show");
        return "redirect:/quiz/show";
    }

    @GetMapping("/quiz/getById/{id}")
    public String getQuizById(@PathVariable("id") Long id, Model model) {

        model.addAttribute("quizDto", quizService.getQuizById(id));
        model.addAttribute("trainings", trainingService.getAllTrainings());

        return "evaluation/quizTitleOrDescUpdate";
    }

    @PostMapping("/quiz/updateTitleOrDesc")
    public String updateTitleOrDesc(@Valid QuizDto quizDto) {

        quizService.updateTitleOrDesc(quizDto);


        return "redirect:/quiz/show";
    }

    @GetMapping("/attemptQuiz/{id}")
    public String attemptQuiz(Model model, HttpServletRequest httpServletRequest, @PathVariable("id") Long trainingId) {

        List<QuizDto> quiz = quizService.getAllQuizzesForTraining(trainingId);
        if (quiz.size() != 0) {
            Random random = new Random();
            QuizDto quizDto = quiz.get(random.nextInt(quiz.size()));
            List<AnswerDto> quizDto1 = new ArrayList<>();
            log.info("Attempted quiz with id: {}", quizDto.getId());
            model.addAttribute("attemptedQuiz", quizDto);
            model.addAttribute("quizResult", quizDto1);

            log.info("User is: {}", httpServletRequest.getUserPrincipal().getName());
            evaluationDetails.setUsername(httpServletRequest.getUserPrincipal().getName());
            evaluationDetails.setTrainingId(trainingId);

            log.info("Attempting quiz with id: {}", quizDto.getId());
            evaluationDetails.setQuizId(quizDto.getId());

            log.info("Start time is: " + new Timestamp(System.currentTimeMillis()));
            evaluationDetails.setStartTime(new Timestamp(System.currentTimeMillis()));

            return "takingTraining/attemptQuiz";
        } else {
            return "404error";
        }

    }

    @PostMapping("/submit")
    public String submitResults(@ModelAttribute QuizDto quizResult, HttpServletResponse httpServletResponse) throws IOException {

        evaluationDetails.setEndTime(new Timestamp(System.currentTimeMillis()));
        log.info(String.valueOf(evaluationDetails.getEndTime()));

        log.info(quizResult.toString());
        quizResult.getQuestions().forEach(l -> log.info(l.getAnswers().toString()));
//        answerDto.forEach(l->log.info("is correct?"+l.isCorrect()));
        log.info("is correct?");
        evaluationDetails.setQuizPercentCorrect(quizService.getQuizResult(quizResult));
        Long id = evaluationDetails.getTrainingId();
        evaluationDetailsService.saveEvaluation(evaluationDetails);


//        httpServletResponse.sendRedirect("/quizResult/" + id);
        return "redirect:/quizResult/" + id;
    }

}
