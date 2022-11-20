package com.elearning.services.training;

import com.elearning.model.training.Training;
import com.elearning.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class trainingServiceImpl {

    @Autowired
    TrainingRepository trainingRepository;

}
