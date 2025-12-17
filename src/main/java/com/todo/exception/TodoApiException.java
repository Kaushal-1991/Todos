package com.todo.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoApiException extends RuntimeException{
     private HttpStatus httpStatus;
     private String message;
}
