package com.anurag.image_uploader.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File read failed")
public class FileReadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileReadException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileReadException(String message) {
		super(message);
	}

}
