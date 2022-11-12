package com.elearning.services.evaluation;

import com.elearning.dto.QuestionDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.repositories.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionServiceImpl {

    QuestionRepository questionRepository;
    MapperDto mapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, MapperDto mapper) {
        this.questionRepository = questionRepository;
        this.mapper = mapper;
    }

    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream().map(mapper::convertToQuestionDto).collect(Collectors.toList());
    }

    @GetMapping("quiz/{id}/questions")
    public String showQuestionsInQuiz(){

        return "";
    }
}
