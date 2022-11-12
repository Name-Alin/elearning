package com.elearning.controllers;


import com.elearning.services.evaluation.QuestionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

    private final QuestionServiceImpl questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question")
    public String getQuestionForm(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "evaluation/questionForm";
    }


}
