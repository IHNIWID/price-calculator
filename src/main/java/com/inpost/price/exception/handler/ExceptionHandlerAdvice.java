package com.inpost.price.exception.handler;

import com.inpost.price.api.model.ApiErrorResponse;
import com.inpost.price.exception.InvalidUUIDException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.webjars.NotFoundException;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {


    @ExceptionHandler(InvalidUUIDException.class)
    protected ResponseEntity<ApiErrorResponse> handleInvalidUUIDException(InvalidUUIDException ex) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ex.getMessage()));
    }

    // last resort exception
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("Not handled exception occurred: " + Arrays.toString(ex.getStackTrace()) + ",\n message -> " + ex.getMessage());
        return ResponseEntity.internalServerError().body(new ApiErrorResponse("Fatal error."));
    }
}
