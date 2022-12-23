package com.elearning.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class TrainingDto {

    private Long id;
    private String trainingTitle;
    private String description;
    private String pathToTraining;
    private String pathToImage;
    private MultipartFile multipartFile;
    private MultipartFile imageFile;
}
