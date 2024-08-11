package com.anurag.image_uploader.exceptionhandlers;

import java.io.UncheckedIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

	private Logger logger;

	GlobalExceptionHandlingControllerAdvice() {
		this.logger = LoggerFactory.getLogger(getClass());
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "File download failed")
	@ExceptionHandler(value = {UncheckedIOException.class})
	public String handleUncheckedIOException(UncheckedIOException e) {
		logger.error("Error downloading file", e.getMessage());
		return e.getMessage();
	}

}
