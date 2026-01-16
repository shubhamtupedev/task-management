package io.github.shubhamtupe.exception.handler;

import io.github.shubhamtupe.dto.ErrorResponseDto;
import io.github.shubhamtupe.exception.ResourceNotFoundException;
import io.github.shubhamtupe.exception.ValidationException;
import io.github.shubhamtupe.utility.AppErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(ValidationException validationException, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                false,
                validationException.getMessage(),
                validationException.getErrorCode(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        HashMap<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(
                        error -> errorMap.put(error.getField(), error.getDefaultMessage())
                );

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                false,
                "Validation Failed",
                AppErrorCodes.VALIDATION_FAILED,
                request.getRequestURI(),
                errorMap
        );

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                false,
                ex.getMessage(),
                AppErrorCodes.RESOURCE_NOT_FOUND,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }
}
