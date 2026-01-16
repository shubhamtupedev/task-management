package io.github.shubhamtupe.service;

import io.github.shubhamtupe.dto.ApiResponseDto;
import io.github.shubhamtupe.dto.TaskRequestDto;
import io.github.shubhamtupe.dto.TaskResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskManagerService {

    ResponseEntity<ApiResponseDto<TaskResponseDto>> createTask(TaskRequestDto taskRequestDto);

    ResponseEntity<ApiResponseDto<TaskResponseDto>> getTask(Long id);

    ResponseEntity<ApiResponseDto<List<TaskResponseDto>>> getTasks();

    ResponseEntity<ApiResponseDto<TaskResponseDto>> updateTaskStatus(Long id);

    ResponseEntity<ApiResponseDto<String>> deleteTask(Long id);

}
