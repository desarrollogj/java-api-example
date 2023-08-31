package com.desarrollogj.exampleapi.commons.helper.web;

import com.desarrollogj.exampleapi.commons.helper.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerErrorAdvice {
    @ExceptionHandler(value = {ValidationException.class,
            ServiceException.class,
            NotFoundException.class,
            BadRequestException.class})
    public final ResponseEntity<ErrorResponse> handleCustomExceptions(ApiException ex) {
        return error(ex.getHttpStatus(), ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler({WebExchangeBindException.class})
    public final ResponseEntity<ErrorResponse> handleWebExchangeBindExceptions(
            WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        var errorsStr = errors.keySet().stream()
                .map(k -> k + ": " + errors.get(k))
                .collect(Collectors.joining(", ", "", ""));
        return error(HttpStatus.BAD_REQUEST, errorsStr);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        var validationList = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        var validationMessage = validationList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        return error(HttpStatus.BAD_REQUEST, validationMessage);
    }

    @ExceptionHandler({ResponseStatusException.class})
    public final ResponseEntity<ErrorResponse> handleResponseStatusExceptions(
            ResponseStatusException ex) {
        return error(ex.getStatusCode(), ex.getReason());
    }

    @ExceptionHandler({RuntimeException.class})
    public final ResponseEntity<ErrorResponse> handleAllExceptions(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    protected ResponseEntity<ErrorResponse> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message));
    }

    protected ResponseEntity<ErrorResponse> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), e.getMessage()));
    }

    protected ResponseEntity<ErrorResponse> error(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status.value(), status.getReasonPhrase(), code, message));
    }

    protected ResponseEntity<ErrorResponse> error(HttpStatusCode status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message));
    }
}
