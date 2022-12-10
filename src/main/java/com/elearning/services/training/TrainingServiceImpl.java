package com.elearning.services.training;

import com.elearning.dto.TrainingDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.exceptions.ErrorType;
import com.elearning.exceptions.ResourceNotFoundException;
import com.elearning.model.training.Training;
import com.elearning.repositories.TrainingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TrainingServiceImpl {

    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    MapperDto mapper;

    public List<TrainingDto> getAllTrainings() {

        return trainingRepository.findAll().stream().map(mapper::convertToTrainingDto).collect(Collectors.toList());
    }

    public void saveTraning(TrainingDto trainingDto) throws IOException {
        if (!trainingDto.getMultipartFile().isEmpty()) {

            Training training = mapper.convertToTrainingEntity(trainingDto);
            File file = new File("./video_trainings/"
                    + trainingDto.getTrainingTitle().replaceAll("\\s+", "_") + "/"
                    + Objects.requireNonNull(trainingDto.getMultipartFile().getOriginalFilename()).replaceAll("\\s+", ""));
            file.getParentFile().mkdirs();
            file.createNewFile();
            trainingDto.getMultipartFile().transferTo(file.getAbsoluteFile());

            training.setPathToTraining(file.getPath());

            trainingRepository.save(training);
            log.info("Training saved to: {}", file.getPath());
        } else {
            log.error("Training can not be saved without uploading a file");
        }


    }

    public void deleteTraining(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training was not found", ErrorType.TRAINING_DOES_NOT_EXIST));
        try {
            if (training.getPathToTraining() != null) {
                File file = new File(String.valueOf(Path.of(training.getPathToTraining())));


                if (file.exists()) {
                    log.info("Deleting file: {}", file.getAbsoluteFile());
                    file.delete();
                    trainingRepository.delete(training);

                    log.info("Training deleted successfully");

                } else {
                    log.info("File is missing. Deleting also the database entry");
                    trainingRepository.delete(training);
                }
            } else {
                log.error("Path to training was missing from db. Investigate how the issue is reproducing");
                trainingRepository.delete(training);
            }


        } catch (Exception ex) {
            log.info(ex.toString());
        }


    }

    public TrainingDto getTrainingById(Long id) {

        TrainingDto trainingDto = trainingRepository.findById(id).map(mapper::convertToTrainingDto)
                .orElseThrow(() -> new ResourceNotFoundException("Training was not found", ErrorType.TRAINING_DOES_NOT_EXIST));

        return trainingDto;
    }

    public void updateTraining(TrainingDto trainingDto) {
        Training trainingBeforeUpdate = trainingRepository.getReferenceById(trainingDto.getId());

        if (trainingBeforeUpdate.getTrainingTitle().equals(trainingDto.getTrainingTitle())
                && trainingDto.getMultipartFile().isEmpty()) {
            Training training = mapper.convertToTrainingEntity(trainingDto);
            training.setPathToTraining(trainingDto.getPathToTraining());
            trainingRepository.save(training);
        } else if (trainingDto.getMultipartFile().isEmpty()) {

            try {

                File previousFile = new File(String.valueOf(Path.of(trainingBeforeUpdate.getPathToTraining())));
                String[] fileName = trainingBeforeUpdate.getPathToTraining().split("\\\\");
                File newLocation = new File("./video_trainings/"
                        + trainingDto.getTrainingTitle().replaceAll("\\s+","_"));
                File newFiles = new File("./video_trainings/"
                        + trainingDto.getTrainingTitle().replaceAll("\\s+","_") + "/" + fileName[3]);
                newLocation.mkdirs();

                log.info(previousFile.getCanonicalPath());

                FileSystemUtils.copyRecursively(previousFile, newFiles);

                previousFile.delete();

                Training updatedTraining = mapper.convertToTrainingEntity(trainingDto);
                updatedTraining.setPathToTraining(newFiles.getPath());
                trainingRepository.save(updatedTraining);

            } catch (Exception ex) {
                log.error(ex.toString());
            }
        } else if (!trainingDto.getMultipartFile().isEmpty()) {

            try {
                deleteTraining(trainingBeforeUpdate.getId());
                saveTraning(trainingDto);
            } catch (Exception ex) {
                log.error(ex.toString());
            }
        }


    }

    public ResponseEntity<StreamingResponseBody> loadPartialMediaFile(Long id, String rangeHeader) {

        try {
            StreamingResponseBody responseStream;
            Training training = trainingRepository.getReferenceById(id);
            String filePathString = training.getPathToTraining();
            Path filePath = Paths.get(filePathString);
            long fileSize = Files.size(filePath);
            byte[] buffer = new byte[1024];

            final HttpHeaders responseHeaders = new HttpHeaders();

            if (rangeHeader == null) {
                responseHeaders.add("Content-Type", "video/mp4");
                responseHeaders.add("Content-Length", String.valueOf(fileSize));
                responseStream = os -> {
                    RandomAccessFile file = new RandomAccessFile(filePathString, "r");
                    try (file) {
                        long pos = 0;
                        file.seek(pos);
                        while (pos < fileSize - 1) {
                            file.read(buffer);
                            os.write(buffer);
                            pos += buffer.length;
                        }
                        os.flush();
                    } catch (Exception e) {
                    }
                };

                return new ResponseEntity<StreamingResponseBody>
                        (responseStream, responseHeaders, HttpStatus.OK);
            }

            String[] ranges = rangeHeader.split("-");
            Long rangeStart = Long.parseLong(ranges[0].substring(6));
            Long rangeEnd;
            if (ranges.length > 1)
            {
                rangeEnd = Long.parseLong(ranges[1]);
            }
            else
            {
                rangeEnd = fileSize - 1;
            }

            if (fileSize < rangeEnd)
            {
                rangeEnd = fileSize - 1;
            }

            String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
            responseHeaders.add("Content-Type", "video/mp4");
            responseHeaders.add("Content-Length", contentLength);
            responseHeaders.add("Accept-Ranges", "bytes");
            responseHeaders.add("Content-Range", "bytes" + " " +
                    rangeStart + "-" + rangeEnd + "/" + fileSize);
            final Long _rangeEnd = rangeEnd;
            responseStream = os -> {
                RandomAccessFile file = new RandomAccessFile(filePathString, "r");
                try (file)
                {
                    long pos = rangeStart;
                    file.seek(pos);
                    while (pos < _rangeEnd)
                    {
                        file.read(buffer);
                        os.write(buffer);
                        pos += buffer.length;
                    }
                    os.flush();
                }
                catch (Exception e) {}
            };

            return new ResponseEntity<StreamingResponseBody>
                    (responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);


        } catch (Exception ex) {
            log.error(ex.toString());
        }


        return null;
    }
}
