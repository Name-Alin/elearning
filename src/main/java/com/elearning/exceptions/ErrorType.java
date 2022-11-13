package com.elearning.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    USER_DOES_NOT_EXIST("User does not exist"),
    QUIZ_DOES_NOT_EXIST("Quiz does not exist");

    private String description;
}