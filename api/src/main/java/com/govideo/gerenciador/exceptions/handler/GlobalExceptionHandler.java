package com.govideo.gerenciador.exceptions.handler;

import com.govideo.gerenciador.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormResponse> handle(MethodArgumentNotValidException exception) {
        List<ErroDeFormResponse> errosDTO = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormResponse erro = new ErroDeFormResponse(e.getField(), mensagem);
            errosDTO.add(erro);
        });
        return errosDTO;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> allExceptionHandler(Exception e, HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> recursoNaoEncontrado(ResourceNotFoundException e,
                                                                        HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(EquipamentoNaoDisponivelException.class)
    public final ResponseEntity<ExceptionResponse> equipamentoNaoDisponivel(EquipamentoNaoDisponivelException e,
                                                                            HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(ConflitoDeEmailException.class)
    public final ResponseEntity<ExceptionResponse> conflitoDeEmail(ConflitoDeEmailException e,
                                                                   HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public final ResponseEntity<ExceptionResponse> operacaoNaoPermitida(OperationNotAllowedException e,
                                                                        HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(CredenciaisIncorretasException.class)
    public final ResponseEntity<ExceptionResponse> credenciaisIncorretas(CredenciaisIncorretasException e,
                                                                         HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> emailNaoEncontrado(UsernameNotFoundException e,
                                                                      HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
