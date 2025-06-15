package com.govideo.gerenciador.exceptions;

import org.springframework.http.HttpStatus;

public class OperationNotAllowedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    HttpStatus status = HttpStatus.FORBIDDEN;

    public OperationNotAllowedException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }

}
