package io.github.shubhamtupe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    private boolean success;
    private String message;
    private String errorCode;
    private String path;
    private HashMap<String, String> errors;
    private LocalDateTime timestamp;

    public ErrorResponseDto(boolean success, String message, String errorCode, String path) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponseDto(boolean success, String message, String errorCode, String path, HashMap<String, String> errors) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

}
