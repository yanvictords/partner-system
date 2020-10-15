package br.com.partner.controllers.impl;

import br.com.partner.exceptions.BadGatewayException;
import br.com.partner.exceptions.FeignClientException;
import br.com.partner.exceptions.UnprocessableEntityException;
import br.com.partner.presenters.errors.Error;
import br.com.partner.presenters.errors.ErrorDetail;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class HandlerController {

    private final Logger logger;

    public HandlerController(final Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<Error> handlerHttpStatusException(final FeignClientException e) throws IOException {
        final var errorPresenter = e.getErrorPresenter();
        errorPresenter.updatePath();
        logger.warn(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus())
                .body(errorPresenter);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<Error> handlerBadGatewayExceptionExceptionHandler(final BadGatewayException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .body(getError(e, UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Error> handlerUnprocessableEntityExceptionHandler(final UnprocessableEntityException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .body(getError(e, UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handlerIllegalArgumentExceptionHandler(final IllegalArgumentException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(BAD_REQUEST)
                .body(getError(e, BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Error> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        final var result = ex.getBindingResult();
        final var errors = result.getFieldErrors().stream()
                .map(it -> {
                    final var error = new ErrorDetail();
                    error.setMessage(it.getDefaultMessage());
                    logger.warn(it.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toSet());

        final var errorPresenter = getError(ex, UNPROCESSABLE_ENTITY);
        errorPresenter.setErrors(errors);
        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .body(errorPresenter);
    }

    private Error getError(final Throwable t, final HttpStatus status) {
        final var errors = getErrorDetails(t);
        return Error.builder()
                .errors(errors)
                .status(status)
                .build();
    }

    private Set<ErrorDetail> getErrorDetails(final Throwable e) {
        final var details = new ErrorDetail();
        details.setMessage(e.getMessage());
        return Sets.newHashSet(details);
    }
}
