package com.elearning.controllers;


import com.elearning.dto.QuestionDto;
import com.elearning.model.evaluation.Answer;
import com.elearning.services.evaluation.QuestionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private final QuestionServiceImpl questionService;
    private Long quizId;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question")
    public String getQuestionForm(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "evaluation/questionForm";
    }

    @GetMapping("/quiz/{id}/questions")
    public String showQuestionsInQuiz(@PathVariable("id") Long id, Model model, QuestionDto questionDto) {
        quizId = id;
        model.addAttribute("quizQuestions", questionService.getAllQuizQuestions(id));

        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answers.add(new Answer());
        }
        questionDto.setAnswers(answers);

        return "evaluation/quizQuestions";
    }

    @PostMapping("/question/createOrSave")
    public String addQuestion(@Valid QuestionDto questionDto) {

        questionService.saveOrUpdateQuestion(questionDto, quizId);

        return "redirect:/quiz/" + quizId + "/questions";
    }

    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Long questionId) {

        questionService.deleteQuestion(questionId);

        return "redirect:/quiz/" + quizId + "/questions";
    }

    @GetMapping("/question/show/{id}")
    public String showSpecificQuestion(@PathVariable("id") Long questionId, Model model, QuestionDto questionDto) {

        model.addAttribute("question", questionService.getQuestionById(questionId));

        return "evaluation/questionUpdateForm";
    }


}
