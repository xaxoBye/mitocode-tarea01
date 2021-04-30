package com.mitocode.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensaje = ex.getBindingResult().getAllErrors().stream().map(err -> {
			return err.getDefaultMessage().toString().concat(", ");
		}).collect(Collectors.joining());

		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), mensaje, request.getDescription(false));
		
		return new ResponseEntity<Object>(er, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ModelNotFoundException.class)
	public ResponseEntity<ExceptionResponse> manejarModeloNotFoundException(ModelNotFoundException ex, WebRequest request){
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.NOT_FOUND);
	}
	

}
