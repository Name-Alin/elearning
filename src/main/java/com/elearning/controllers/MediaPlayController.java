package com.elearning.controllers;

import com.elearning.services.training.TrainingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Slf4j
@Controller
public class MediaPlayController {

    @Autowired
    TrainingServiceImpl trainingService;


    @GetMapping(value = "/video_training/{id}")
    public ResponseEntity<StreamingResponseBody> playVideo(
            @PathVariable("id") Long id,
            @RequestHeader(value = "Range", required = false) String rangeHeader) {

            return trainingService.loadPartialMediaFile(id, rangeHeader);

    }

}
