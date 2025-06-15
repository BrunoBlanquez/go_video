package com.govideo.gerenciador.exceptions;

import org.springframework.http.HttpStatus;


public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }

}
