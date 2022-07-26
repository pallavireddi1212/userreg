package com.predica.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyRegisteredException.class)
	public final ResponseEntity<PredicaCustomExceptionResponse> handleUserAlreadyRegisteredException(
			PredicaException ex, WebRequest webRequest) {
		PredicaCustomExceptionResponse predicaCustomExceptionResponse = new PredicaCustomExceptionResponse(
				ex.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<PredicaCustomExceptionResponse>(predicaCustomExceptionResponse, HttpStatus.CONFLICT);
	}
}
