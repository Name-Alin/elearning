package com.elearning.controllers;

import com.elearning.dto.TrainingDto;
import com.elearning.model.training.Training;
import com.elearning.services.training.TrainingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
public class TrainingController {

    @Autowired
    TrainingServiceImpl trainingService;

    @GetMapping("/showTrainingForm")
    public String showTrainingForm(Model model) {
        TrainingDto trainingDto = new TrainingDto();
        model.addAttribute("trainingDto", trainingDto);
        model.addAttribute("allTrainings", trainingService.getAllTrainings());
        log.info("Trainings received from database");
        return "training/trainingForm";
    }

    @PostMapping("/addTraining")
    @ResponseStatus(HttpStatus.CREATED)
    public String addTraining(@Valid TrainingDto training, HttpServletResponse httpServletResponse) throws IOException {


        log.info(training.getTrainingTitle());
        log.info(training.getDescription());
        log.info(training.getPathToTraining());
        log.info(training.getMultipartFile().getName());

        trainingService.saveTraning(training);

        httpServletResponse.sendRedirect("/showTrainingForm");
        return "redirect:/showTrainingForm";
    }

    @GetMapping("/deleteTraining/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deteleTraining(@PathVariable("id") Long id, HttpServletResponse httpServletResponse) throws IOException {

        trainingService.deleteTraining(id);

        httpServletResponse.sendRedirect("/showTrainingForm");
        return "redirect:/showTrainingForm";
    }

    @GetMapping("/showUpdateForm/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        TrainingDto trainingDto = new TrainingDto();
        model.addAttribute("trainingDto", trainingDto);
        model.addAttribute("training", trainingService.getTrainingById(id));

        return "training/updateTrainingForm";
    }

    @PostMapping("/updateTraining")
    public String updateTraining(@Valid TrainingDto trainingDto) {

        trainingService.updateTraining(trainingDto);

        return "redirect:/showTrainingForm";
    }

}
