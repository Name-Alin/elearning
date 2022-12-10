package com.elearning.controllers;

import com.elearning.dto.TrainingDto;
import com.elearning.services.training.TrainingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class TrainingController {

    @Autowired
    TrainingServiceImpl trainingService;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        List<TrainingDto> trainingDtos = trainingService.getAllTrainings();
        trainingDtos.forEach(f -> {
                    f.setPathToTraining(f.getPathToTraining().substring(1));
                    log.info(f.getPathToTraining());
                }
        );
        model.addAttribute("trainings", trainingDtos);

        return "index";
    }

    @GetMapping("/training/{id}")
    public String takingTraining(@PathVariable("id") Long id, Model model) {

        model.addAttribute("training", trainingService.getTrainingById(id));

        return "takingTraining/takingTrainingForm";
    }

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

        trainingService.saveTraning(training);

        httpServletResponse.sendRedirect("/showTrainingForm");
        return "redirect:/showTrainingForm";
    }

    @GetMapping("/deleteTraining/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTraining(@PathVariable("id") Long id, HttpServletResponse httpServletResponse) throws IOException {

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
