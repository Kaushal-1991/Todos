package com.todo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.todo.dto.ErrorDetailsDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TodoApiException.class)
	public ResponseEntity<ErrorDetailsDto> hanldeTodoApiException(TodoApiException exception,WebRequest request){
		ErrorDetailsDto errorDetails = new ErrorDetailsDto(LocalDateTime.now(),exception.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
	}
}
