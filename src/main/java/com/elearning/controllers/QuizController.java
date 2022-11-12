package com.elearning.controllers;

import com.elearning.dto.AnswerDto;
import com.elearning.dto.QuestionDto;
import com.elearning.dto.QuizDto;
import com.elearning.model.evaluation.Answer;
import com.elearning.model.evaluation.Question;
import com.elearning.services.evaluation.QuizServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
public class QuizController {

    QuizServiceImpl quizService;

    public QuizController(QuizServiceImpl quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz")
    public String getQuestionForm(Model model, Question questionDto, QuizDto quizDto) {
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
    public String createNewQuiz(@Valid QuizDto quizDto, BindingResult result) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
        }

        quizService.saveOrUpdateQuiz(quizDto);

        Long id = quizService.getLastQuizId();

        return "redirect:/quiz";
    }

}
