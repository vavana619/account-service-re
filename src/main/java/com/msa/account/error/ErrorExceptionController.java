package com.msa.account.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.msa.account.exception.AccountNotFoundException;
import com.msa.account.exception.DuplicatedEmailException;
import com.msa.account.exception.WrongPasswordException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorExceptionController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
		final BindingResult bindingResult = e.getBindingResult();
		final List<FieldError> errors = bindingResult.getFieldErrors();
		
		return buildFieldErrors(
				ErrorCode.INPUT_VALUE_INVALID,
				errors.parallelStream()
					.map(error -> ErrorResponse.FieldError.builder()
							.reason(error.getDefaultMessage())
							.field(error.getField())
							.value((String)error.getRejectedValue())
							.build())
					.collect(Collectors.toList())
				);
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
		final ErrorCode accountNotFound = ErrorCode.ACCOUNT_NOT_FOUND;
		log.error(accountNotFound.getMessage(), e.getEmail());
		return buildError(accountNotFound);
	}
	
	@ExceptionHandler(DuplicatedEmailException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ErrorResponse handleConstraintViolationException(DuplicatedEmailException e) {
		final ErrorCode errorCode = ErrorCode.EMAIL_DUPLICATION;
		log.error(errorCode.getMessage(), e.getEmail());
		return buildError(errorCode);
	}
	
	@ExceptionHandler(WrongPasswordException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	protected ErrorResponse handleWrongPasswordException(WrongPasswordException e) {
		final ErrorCode errorCode = ErrorCode.WRONG_PASSWORD;
		log.error(errorCode.getMessage(), e.getEmail());
		return buildError(errorCode);
	}
	
	private ErrorResponse buildError(ErrorCode errorCode) {
		return ErrorResponse.builder()
				.code(errorCode.getCode())
				.status(errorCode.getStatus())
				.message(errorCode.getMessage())
				.build();
	}
	
	private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
		return ErrorResponse.builder()
				.code(errorCode.getCode())
				.status(errorCode.getStatus())
				.message(errorCode.getMessage())
				.errors(errors)
				.build();
	}

}
