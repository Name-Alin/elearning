package com.elearning.dto.mapper;

import com.elearning.dto.*;
import com.elearning.model.authentication.Role;
import com.elearning.model.authentication.User;
import com.elearning.model.evaluation.Answer;
import com.elearning.model.evaluation.EvaluationDetails;
import com.elearning.model.evaluation.Question;
import com.elearning.model.evaluation.Quiz;
import com.elearning.model.training.Training;
import org.springframework.stereotype.Component;

@Component
public class MapperDto {

    public UserDto convertToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(user.getRoles())
                .isSupervisedBy(user.getIsSupervisedBy())
                .build();
    }
    public User convertToUserEntity(UserDto userDto){
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .id(userDto.getId())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .enabled(userDto.isEnabled())
                .roles(userDto.getRoles())
                .isSupervisedBy(userDto.getIsSupervisedBy())
                .build();
    }

    public Role convertToRoleEntity(RoleDto roleDto){
        if (roleDto == null)
            return null;

       return Role.builder().name(roleDto.getName()).build();
    }

    public RoleDto convertToRoleDto(Role role){
        if (role == null)
            return null;

        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public AnswerDto convertToAnswerDto(Answer answer){
        if (answer == null)
            return null;
        return AnswerDto.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .question(answer.getQuestion())
                .correct(answer.isCorrect())
                .build();
    }

    public Answer convertToAnswerEntity(AnswerDto answerDto){
        if (answerDto == null)
            return null;
        return Answer.builder()
                .id(answerDto.getId())
                .content(answerDto.getContent())
                .correct(answerDto.isCorrect())
                .question(answerDto.getQuestion())
                .build();
    }

    public QuestionDto convertToQuestionDto(Question question){
        if (question == null)
            return null;
        return QuestionDto.builder()
                .id(question.getId())
                .content(question.getContent())
                .answers(question.getAnswers())
                .build();
    }

    public Question convertToQuestionEntity(QuestionDto questionDto){
        if (questionDto == null)
            return null;
        return Question.builder()
                .id(questionDto.getId())
                .content(questionDto.getContent())
                .answers(questionDto.getAnswers())
                .build();
    }

    public QuizDto convertToQuizDto (Quiz quiz){

        if (quiz == null)
            return null;
        return QuizDto.builder()
                .id(quiz.getId())
                .questions(quiz.getQuestions())
                .name(quiz.getName())
                .description(quiz.getDescription())
                .expectedPercent(quiz.getExpectedPercent())
                .createdDate(quiz.getCreatedDate())
                .build();
    }

    public Quiz convertToQuizEntity(QuizDto quizDto){
        if (quizDto == null)
            return null;
        return Quiz.builder()
                .id(quizDto.getId())
                .questions(quizDto.getQuestions())
                .name(quizDto.getName())
                .description(quizDto.getDescription())
                .expectedPercent(quizDto.getExpectedPercent())
                .createdDate(quizDto.getCreatedDate())
                .build();
    }

    public TrainingDto convertToTrainingDto (Training training){
        if (training == null)
            return null;
        return TrainingDto.builder()
                .id(training.getId())
                .trainingTitle(training.getTrainingTitle())
                .description(training.getDescription())
                .pathToTraining(training.getPathToTraining())
                .pathToImage(training.getPathToImage())
                .build();
    }

    public Training convertToTrainingEntity(TrainingDto trainingDto){
        if (trainingDto == null)
            return null;
        return Training.builder()
                .id(trainingDto.getId())
                .trainingTitle(trainingDto.getTrainingTitle())
                .description(trainingDto.getDescription())
                .build();
    }

    public EvaluationDetailsDto convertToEvaluationDetailsDto(EvaluationDetails evaluationDetails){
        if (evaluationDetails == null)
            return null;
        return EvaluationDetailsDto.builder()
                .graduated(evaluationDetails.isGraduated())
                .quizPercentCorrect(evaluationDetails.getQuizPercentCorrect())
                .quizDuration(evaluationDetails.getQuizDuration())
                .username(evaluationDetails.getUser().getUsername())
                .trainingId(evaluationDetails.getTrainingId())
                .quizId(evaluationDetails.getQuizId())
                .build();
    }

    public EvaluationDetails convertToEvaluationDetailsEntity(EvaluationDetailsDto evaluationDetailsDto){
        if (evaluationDetailsDto == null)
            return null;
        return EvaluationDetails.builder()
                .graduated(evaluationDetailsDto.isGraduated())
                .quizPercentCorrect(evaluationDetailsDto.getQuizPercentCorrect())
                .trainingId(evaluationDetailsDto.getTrainingId())
                .quizId(evaluationDetailsDto.getQuizId())
                .startTime(evaluationDetailsDto.getStartTime())
                .endTime(evaluationDetailsDto.getEndTime())
                .build();
    }

}
