package com.elearning.controllers;

import com.elearning.dto.QuizDto;
import com.elearning.model.evaluation.Answer;
import com.elearning.model.evaluation.Question;
import com.elearning.services.evaluation.QuizServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class QuizController {

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

}
